package com.mini.asaas.email

import com.mini.asaas.Payment.Payment
import com.mini.asaas.utils.StringUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugins.mail.MailService
import org.springframework.context.MessageSource

@GrailsCompileStatic
@Transactional
class EmailService {

    MessageSource messageSource

    MailService mailService

    public void emailPaymentCreated(Payment payment) {

        String value = StringUtils.fromBigDecimal(payment.value)
        String payerName = payment.payer.name

        String subject = messageSource.getMessage(
                'email.payment.created.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )

        String actionMessage = messageSource.getMessage(
                'email.payment.created.action',
                null,
                Locale.getDefault()
        )

        String body = messageSource.getMessage(
                'email.payment.created.body',
                [value, payerName] as Object[],
                Locale.getDefault()
        )

        def model = [
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

        String subject = messageSource.getMessage(
                'email.payment.receive.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )

        String actionMessage = messageSource.getMessage(
                'email.payment.receive.action',
                null,
                Locale.getDefault()
        )

        String body = messageSource.getMessage(
                'email.payment.receive.body',
                [value, payerName] as Object[],
                Locale.getDefault()
        )

        def model = [
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

        String subject = messageSource.getMessage(
                'email.payment.deleted.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )

        String actionMessage = messageSource.getMessage(
                'email.payment.deleted.action',
                null,
                Locale.getDefault()
        )

        String body = messageSource.getMessage(
                'email.payment.deleted.body',
                [value, payerName] as Object[],
                Locale.getDefault()
        )

        def model = [
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

        String subject = messageSource.getMessage(
                'email.payment.overdue.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )

        String actionMessage = messageSource.getMessage(
                'email.payment.overdue.action',
                null,
                Locale.getDefault()
        )

        String body = messageSource.getMessage(
                'email.payment.overdue.body',
                [value, payerName] as Object[],
                Locale.getDefault()
        )

        def model = [
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
            throw new Exception(exception)
        }
    }

}
