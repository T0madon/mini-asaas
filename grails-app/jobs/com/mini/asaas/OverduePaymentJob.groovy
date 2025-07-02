package com.mini.asaas

import com.mini.asaas.payment.PaymentService

class OverduePaymentJob {

    PaymentService paymentService

    static triggers = {
        simple repeatInterval: 1000l
        cron name: 'ProcessOverduePaymentJobTrigger', cronExpression: "0 0 0 * * ?"
    }

    def execute() {
        try {
            paymentService.setPaymentOverdue()
        } catch (Exception exception) {
            log.error(exception)
        }
    }
}
