package com.payment.bean;/**
 * Created by ptmind on 2017/1/5.
 */

/**
 * @author <a href="mailto:huanhuan_com@yeah.net">zhanhh</a>
 * @since 2017/1/5 - 23:36
 */
public class PaymentBean {
    //pay type : like paypal, stripe , alipay
    private String payType;


    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
