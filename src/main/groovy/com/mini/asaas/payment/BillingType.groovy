package com.mini.asaas.payment

import com.mini.asaas.utils.MessageSourceUtils

enum BillingType {

    BANKSLIP,
    DEBIT_CARD,
    CREDIT_CARD,
    PIX

    public String getLabel() {
        return MessageSourceUtils.getEnumLabel(this)
    }

    public static BillingType convert(type) {
        try {
            return valueOf(type.toUpperCase())
        } catch (Exception ignored) {
            return null
        }
    }
}