package com.mini.asaas.notification

import com.mini.asaas.Payment.Payment
import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.NotificationType
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.payment.PaymentRepository
import com.mini.asaas.payment.PaymentStatus
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import org.springframework.context.MessageSource

@GrailsCompileStatic
@Transactional
class NotificationService {

    MessageSource messageSource

    public Notification create(Object[] subject, Object[] body, Customer customer, NotificationType type, Long paymentId) {
        Notification notification = buildNotification(subject, body, customer, type, paymentId)

        Notification lastUpdateNotification = NotificationRepository.query([
                paymentId: paymentId
        ]).get()

        if (lastUpdateNotification) lastUpdateNotification.deleted = true

        notification.save(failOnError: true)
        return notification
    }

    public void deleteAll(Long customerId) {
        List<Notification> activeNotfications = list(customerId)

        if (!activeNotfications) return

        activeNotfications.each { notification ->
            notification.deleted = true
            notification.save(failOnError: true)
        }
    }

    public List<Notification> list(Long customerId) {
        return NotificationRepository.query([customerId: customerId]).list()
    }

    public List<Notification> list(Long customerId, Integer max, Integer offset) {
        return NotificationRepository.query([customerId: customerId]).readOnly().list([max: max, offset: offset])
    }

    private Notification buildNotification(Object[] subject, Object[] body, Customer customer, NotificationType type, Long paymentId) {
        Notification notification = new Notification()

        String[] types = type.toString().split("_")
        String domain = types[0].toLowerCase()
        String status = types[1].toLowerCase()

        String subjectNotification = messageSource.getMessage(
                'notify.' + domain + '.' + status + '.subject',
                subject,
                Locale.getDefault()
        )

        String bodyNotification = messageSource.getMessage(
                'notify.' + domain + '.' + status + '.body',
                body,
                Locale.getDefault()
        )

        notification.subject = subjectNotification
        notification.body = bodyNotification
        notification.type = type
        notification.customer = customer
        notification.paymentId = paymentId

        return notification
    }
}
