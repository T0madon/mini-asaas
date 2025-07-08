package com.mini.asaas

import com.mini.asaas.payment.PaymentStatus
import com.mini.asaas.utils.MessageSourceUtils

class PaymentTagLib {
    static namespace = "paymentTagLib"

    private static final Map<PaymentStatus, String> THEMES = [
            (PaymentStatus.CANCELED) : "secondary",
            (PaymentStatus.OVERDUE)  : "danger",
            (PaymentStatus.PENDING)  : "warning",
            (PaymentStatus.RECEIVED) : "success"
    ]

    def atlasBadge = { attrs, body ->
        PaymentStatus status = attrs.status as PaymentStatus
        if (status) {
            String theme = THEMES.get(status, "default")
            String label = MessageSourceUtils.getEnumLabel(status)
            out << """<atlas-badge text="${label}" theme="${theme}"></atlas-badge>"""
        }
    }
}

