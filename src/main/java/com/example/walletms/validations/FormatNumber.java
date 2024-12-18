package com.example.walletms.validations;

import org.springframework.stereotype.Component;

@Component
public class FormatNumber {
    public String formatPhoneNumber(String phoneNumber) {
        String cleanedPhone = phoneNumber.replaceAll("\\s+", "");
        if (!cleanedPhone.startsWith("+994")) {
            cleanedPhone = "+994" + cleanedPhone;
        }
        return cleanedPhone;
    }
}
