package com.payment.bean;/**
 * Created by ptmind on 2017/1/6.
 */

import java.math.BigDecimal;

/**
 * @author <a href="mailto:huanhuan_com@yeah.net">zhanhh</a>
 * @since 2017/1/6 - 0:02
 */
public class RefundBean {

    private String billId;

    private BigDecimal refundAmount;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
}
