package com.payment;

import com.payment.config.AbstractApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.UnknownHostException;

@SpringBootApplication(scanBasePackages = "com.payment")
public class Application extends AbstractApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(Application.class);
        abstractMain(app, args);
    }
}
