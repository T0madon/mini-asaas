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

    public static convertStatus(type) {
        if (type == "Aguardando pagamento") return PENDING
        if (type == "Cancelada") return CANCELED
        if (type == "Recebida") return RECEIVED
        if (type == "AVencida") return OVERDUE
    }

    public static List<String> getAllNames() {
        return values().collect { it.name() }
    }
}