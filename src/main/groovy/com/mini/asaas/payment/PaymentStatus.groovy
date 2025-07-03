package com.mini.asaas.payment

enum PaymentStatus {

    PENDING,
    RECEIVED,
    OVERDUE,
    CANCELED

    public Boolean canBeDeleted() {
        return [OVERDUE, PENDING].contains(this)
    }

    public Boolean canBeReceived() {
        return [PENDING].contains(this)
    }
}