package com.example.walletms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WalletMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletMsApplication.class, args);
    }

}
