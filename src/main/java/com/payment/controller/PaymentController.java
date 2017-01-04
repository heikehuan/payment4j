package com.payment.controller;/**
 * Created by ptmind on 2017/1/5.
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:huanhuan_com@yeah.net">zhanhh</a>
 * @since 2017/1/5 - 2:26
 */
@RestController
public class PaymentController {

    @GetMapping("test")
    public String test() {
        return "hello world";
    }
}
