package com.mini.asaas.notification

import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.NotificationType
import com.mini.asaas.utils.MessageSourceUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@GrailsCompileStatic
@Transactional
class NotificationService {

    public Notification create(Object[] subject, Object[] body, Customer customer, NotificationType type) {
        Notification notification = buildNotification(subject, body, customer, type)

        notification.save(failOnError: true)
        return notification
    }

    public List<Notification> list(Long customerId, Integer max, Integer offset) {
        return NotificationRepository.query([customerId: customerId]).readOnly().list([max: max, offset: offset])
    }

    private Notification buildNotification(Object[] subject, Object[] body, Customer customer, NotificationType type) {
        Notification notification = new Notification()

        String types = type.toString().split("_")
        String domain = types[0].toLowerCase()
        String status = types[1].toLowerCase()

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.' + domain + "." + status + '.subject',
                subject,
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.' + domain + "." + status + '.body',
                body,
        )

        notification.subject = subjectNotification
        notification.body = bodyNotification
        notification.type = type
        notification.customer = customer

        return notification
    }
}
