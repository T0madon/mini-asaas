package com.mini.asaas.notification

import com.mini.asaas.utils.MessageSourceUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@GrailsCompileStatic
@Transactional
class NotificationService {

    public Notification createNotificationPaymentCreated(Map args) {
        NotificationAdapter adapter = new NotificationAdapter(args)

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.payment.created.subject',
                adapter.subject as Object[]
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.payment.created.body',
                adapter.body as Object[]
        )

        Notification notification = buildNotification(adapter, subjectNotification, bodyNotification)

        Notification lastUpdateNotification = NotificationRepository.query([
                paymentId: args.paymentId
        ]).get()

        if (lastUpdateNotification) lastUpdateNotification.deleted = true

        notification.save(failOnError: true)
        return notification
    }

    public Notification createNotificationPaymentDeleted(Map args) {
        NotificationAdapter adapter = new NotificationAdapter(args)

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.payment.deleted.subject',
                adapter.subject as Object[]
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.payment.deleted.body',
                adapter.body as Object[]
        )

        Notification notification = buildNotification(adapter, subjectNotification, bodyNotification)

        Notification lastUpdateNotification = NotificationRepository.query([
                paymentId: args.paymentId
        ]).get()

        if (lastUpdateNotification) lastUpdateNotification.deleted = true

        notification.save(failOnError: true)
        return notification
    }

    public Notification createNotificationPaymentReceived(Map args) {
        NotificationAdapter adapter = new NotificationAdapter(args)

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.payment.received.subject',
                adapter.subject as Object[]
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.payment.received.body',
                adapter.body as Object[]
        )

        Notification notification = buildNotification(adapter, subjectNotification, bodyNotification)

        Notification lastUpdateNotification = NotificationRepository.query([
                paymentId: args.paymentId
        ]).get()

        if (lastUpdateNotification) lastUpdateNotification.deleted = true

        notification.save(failOnError: true)
        return notification
    }

    public Notification createNotificationPaymentOverdue(Map args) {
        NotificationAdapter adapter = new NotificationAdapter(args)

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.payment.overdue.subject',
                adapter.subject as Object[]
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.payment.overdue.body',
                adapter.body as Object[]
        )

        Notification notification = buildNotification(adapter, subjectNotification, bodyNotification)

        Notification lastUpdateNotification = NotificationRepository.query([
                paymentId: args.paymentId
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
