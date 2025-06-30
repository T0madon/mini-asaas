package com.mini.asaas.payment

import com.mini.asaas.utils.MessageSourceUtils

enum PaymentStatus {

    PENDING,
    RECEIVED,
    OVERDUE,
    CANCELED

    public String getLabel() {
        return MessageSourceUtils.getEnumLabel(this)
    }

    public Boolean canBeDeleted() {
        return [OVERDUE, PENDING].contains(this)
    }

    public Boolean canBeReceived() {
        return [PENDING].contains(this)
    }
}