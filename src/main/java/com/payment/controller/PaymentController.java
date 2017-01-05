package com.payment.controller;/**
 * Created by ptmind on 2017/1/5.
 */

import com.payment.bean.RefundBean;
import com.payment.service.PaymentService;
import com.payment.utils.GlobalView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:huanhuan_com@yeah.net">zhanhh</a>
 * @since 2017/1/5 - 2:26
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("test")
    public String test() {
        return "hello world";
    }

    @PostMapping(value = "refund", produces = MediaType.APPLICATION_JSON_VALUE)
    public GlobalView refund(@RequestBody RefundBean refundBean) {
        GlobalView view = new GlobalView();
        try {
            paymentService.refund(refundBean);
            view.successBack("refund success");
            return view;
        } catch (Exception e) {
            view.errorBack("refund error.", e);
        }
        return view;
    }


}
