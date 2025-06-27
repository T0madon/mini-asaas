package com.mini.asaas.payment

enum PaymentStatus {

    PENDING,
    RECEIVED,
    OVERDUE,
    CANCELED

    public Boolean canBeReceived() {
        return [PENDING].contains(this)
    }
}