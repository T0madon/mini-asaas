package com.mini.asaas.email

import com.mini.asaas.Payment.Payment
import com.mini.asaas.utils.MessageSourceUtils
import com.mini.asaas.utils.StringUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugins.mail.MailService

@GrailsCompileStatic
@Transactional
class EmailService {

    MailService mailService

    public void emailPaymentCreated(Payment payment) {

        String value = StringUtils.fromBigDecimal(payment.value)
        String payerName = payment.payer.name

        String subject = MessageSourceUtils.getMessage(
                'email.payment.created.subject',
                [payment.id] as Object[]
        )

        String actionMessage = MessageSourceUtils.getMessage(
                'email.payment.created.action',
                null
        )

        String body = MessageSourceUtils.getMessage(
                'email.payment.created.body',
                [value, payerName] as Object[]
        )

        Map<String, String> model = [
                actionMessage: actionMessage,
                body: body
        ]

        send(
                to: payment.payer.email,
                subject: subject,
                html: [view: "/email/paymentEmail", model: model]
        )
    }

    public void emailPaymentReceive(Payment payment) {

        String value = StringUtils.fromBigDecimal(payment.value)
        String payerName = payment.payer.name

        String subject = MessageSourceUtils.getMessage(
                'email.payment.receive.subject',
                [payment.id] as Object[]
        )

        String actionMessage = MessageSourceUtils.getMessage(
                'email.payment.receive.action',
                null
        )

        String body = MessageSourceUtils.getMessage(
                'email.payment.receive.body',
                [value, payerName] as Object[]
        )

        Map<String, String> model = [
                actionMessage: actionMessage,
                body: body
        ]

        send(
                to: payment.payer.email,
                subject: subject,
                html: [view: "/email/paymentEmail", model: model]
        )
    }

    public void emailPaymentDeleted(Payment payment) {

        String value = StringUtils.fromBigDecimal(payment.value)
        String payerName = payment.payer.name

        String subject = MessageSourceUtils.getMessage(
                'email.payment.deleted.subject',
                [payment.id] as Object[]
        )

        String actionMessage = MessageSourceUtils.getMessage(
                'email.payment.deleted.action',
                null
        )

        String body = MessageSourceUtils.getMessage(
                'email.payment.deleted.body',
                [value, payerName] as Object[]
        )

        Map<String, String> model = [
                actionMessage: actionMessage,
                body: body
        ]

        send(
                to: payment.payer.email,
                subject: subject,
                html: [view: "/email/paymentEmail", model: model]
        )
    }

    public void emailPaymentOverdue(Payment payment) {

        String value = StringUtils.fromBigDecimal(payment.value)
        String payerName = payment.payer.name

        String subject = MessageSourceUtils.getMessage(
                'email.payment.overdue.subject',
                [payment.id] as Object[]
        )

        String actionMessage = MessageSourceUtils.getMessage(
                'email.payment.overdue.action',
                null
        )

        String body = MessageSourceUtils.getMessage(
                'email.payment.overdue.body',
                [value, payerName] as Object[]
        )

        Map<String, String> model = [
                actionMessage: actionMessage,
                body: body
        ]

        send(
                to: payment.payer.email,
                subject: subject,
                html: [view: "/email/paymentEmail", model: model]
        )
    }

    private void send(Map args) {

        try {
            mailService.sendMail {
                to(args.to as String)
                subject(args.subject as String)
                html(args.html as Map)
            }
        } catch (Exception exception) {
            throw new RuntimeException("Houve um erro ao enviar o email: " + exception)
        }
    }

}
