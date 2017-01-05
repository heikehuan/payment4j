package com.payment.service.impl;

import com.google.gson.Gson;
import com.payment.bean.RefundBean;
import com.payment.service.PaymentService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.RefundRequest;
import com.paypal.api.payments.Sale;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Refund;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.payment.utils.PaymentConstant.PAY_TYPE_PAYPAL;
import static com.payment.utils.PaymentConstant.PAY_TYPE_STRIPE;

/**
 * @author <a href="mailto:huanhuan_com@yeah.net">zhanhh</a>
 * @since 2017/1/5 - 23:33
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    /**
     * refund the payment, including full refund or partial refund
     */
    public String refund(RefundBean refundBean) {
        try {
            PaymentBill bill = billService.findBillByBillId(refundBean.getBillId());
            if (bill != null
                    && StringUtils.isNotBlank(bill.getRecurringPaymentId())
                    && StringUtils.isNotBlank(bill.getTxnId())) {

                PaymentOrder order = orderService.findOrderById(bill.getOrderId() + "");
                //交易id
                String txnId = bill.getTxnId();
                //退款金额
                BigDecimal refundAmount = refundBean.getRefundAmount();
                //如果前端不传金额, 默认全额退款
                if (refundAmount == null) {
                    refundAmount = order.getOrderAmount();
                }
                //币种
                String currency = order.getCurrency();
                //退款交易id
                String refundTxnId = "";

                switch (bill.getPaymentType()) {
                    case PAY_TYPE_PAYPAL:
                        refundTxnId = refundPaypal(dataVersion, txnId, refundAmount, currency);
                        break;
                    case PAY_TYPE_STRIPE:
                        refundTxnId = refundStripe(area, txnId, refundAmount, currency);
                        break;
                }
                //退款数据入库
                return saveRefundData(bill, refundAmount, refundTxnId);
            }
        } catch (Exception e) {
            logger.error("refund payment error.", e);
        }
        return null;
    }

    /**
     * 保存退款数据
     */
    private Long saveRefundData(final PaymentBill bill, BigDecimal refundAmount, String refundTxnId) {
        Long refundBillId = UUIDTools.createUUID();

        PaymentBill newBill = bill;
        newBill.setBillId(refundBillId);
        newBill.setRefundAmount(refundAmount.multiply(new BigDecimal(-1)));
        newBill.setRefundStatus(0);
        newBill.setTxnId(refundTxnId);
        newBill.setPaymentStatus(null);
        billService.createBillWithBillId(newBill);
        return refundBillId;
    }

    /**
     * stripe退款
     * <p>
     * 退款金额必须是正整数，若是美元，以美分为单位，例如退款15美元，则amount值为1500
     */
    private String refundStripe(String txnId, BigDecimal refundAmount, String currency)
            throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Stripe.apiKey = stripeApiKey_en;

        //获取美分金额 = 美元*100
        if (currency.equalsIgnoreCase("USD")) {
            refundAmount = refundAmount.multiply(BigDecimal.valueOf(100));
        }
        //去掉小数点
        Integer refundCent = refundAmount.intValue();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("charge", txnId);
        params.put("amount", refundCent);

        com.stripe.model.Refund refund = Refund.create(params);
        logger.warn(new Gson().toJson(refund));
        return refund.getId();
    }

    /**
     * paypal退款
     * <p>
     * paypal的金额允许美元和日元带小数点
     */
    private String refundPaypal(String dataVersion, String txnId, BigDecimal refundAmount, String currency) throws PayPalRESTException {
        //paypal退款金额可以为整数或小数, 如果是小数，必须保留2位小数, 这里为了不报错，全部保留2位小数
        refundAmount = refundAmount.setScale(2, BigDecimal.ROUND_UP);
        //获取paypal的apiContext, 用于跟paypal通信
        APIContext apiContext = new APIContext(clientID, clientSecret, mode);

        Sale sale = new Sale();
        sale.setId(txnId);

        Amount amount = new Amount();
        amount.setCurrency(currency); //币种必须跟购买时的币种一样,且不能为空
        amount.setTotal(refundAmount.toString());

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setAmount(amount);
        com.paypal.api.payments.Refund returnRefund = sale.refund(apiContext, refundRequest);
        logger.warn(new Gson().toJson(returnRefund));
        return returnRefund.getId();
    }

}
