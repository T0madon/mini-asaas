package com.mini.asaas.notification

import com.mini.asaas.Payment.Payment
import com.mini.asaas.enums.NotificationType
import com.mini.asaas.utils.MessageSourceUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@GrailsCompileStatic
@Transactional
class NotificationService {

    public Notification createNotificationPaymentCreated(Payment payment, NotificationType type) {
        NotificationAdapter adapter = new NotificationAdapter(payment, type)

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.payment.created.subject',
                adapter.subject as Object[]
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.payment.created.body',
                adapter.body as Object[]
        )

        Notification notification = persistNotification(adapter, subjectNotification, bodyNotification)
        return notification
    }

    public Notification createNotificationPaymentDeleted(Payment payment, NotificationType type) {
        NotificationAdapter adapter = new NotificationAdapter(payment, type)

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.payment.deleted.subject',
                adapter.subject as Object[]
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.payment.deleted.body',
                adapter.body as Object[]
        )

        Notification notification = persistNotification(adapter, subjectNotification, bodyNotification)
        return notification
    }

    public Notification createNotificationPaymentReceived(Payment payment, NotificationType type) {
        NotificationAdapter adapter = new NotificationAdapter(payment, type)

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.payment.received.subject',
                adapter.subject as Object[]
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.payment.received.body',
                adapter.body as Object[]
        )

        Notification notification = persistNotification(adapter, subjectNotification, bodyNotification)
        return notification
    }

    public Notification createNotificationPaymentOverdue(Payment payment, NotificationType type) {
        NotificationAdapter adapter = new NotificationAdapter(payment, type)

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.payment.overdue.subject',
                adapter.subject as Object[]
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.payment.overdue.body',
                adapter.body as Object[]
        )

        Notification notification = persistNotification(adapter, subjectNotification, bodyNotification)
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

    private Notification persistNotification(NotificationAdapter adapter, String subjectNotification, String bodyNotification) {
        Notification notification = buildNotification(adapter, subjectNotification, bodyNotification)

        Notification lastUpdateNotification = NotificationRepository.query([
                paymentId: notification.paymentId
        ]).get()

        if (lastUpdateNotification) lastUpdateNotification.deleted = true

        notification.save(failOnError: true)

        return notification
    }

    private Notification buildNotification(NotificationAdapter adapter, String subjectNotification, String bodyNotification) {
        Notification notification = new Notification()

        notification.subject = subjectNotification
        notification.body = bodyNotification
        notification.type = adapter.type
        notification.customer = adapter.customer
        notification.paymentId = adapter.paymentId

        return notification
    }
}
