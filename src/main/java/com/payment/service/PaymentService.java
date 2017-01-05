package com.payment.service;/**
 * Created by ptmind on 2017/1/5.
 */

import com.payment.bean.RefundBean;

/**
 * @author <a href="mailto:huanhuan_com@yeah.net">zhanhh</a>
 * @since 2017/1/5 - 23:33
 */
public interface PaymentService {
    void refund(RefundBean refundBean);
}
