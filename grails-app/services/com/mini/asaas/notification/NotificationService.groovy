package com.mini.asaas.notification

import com.mini.asaas.Payment.Payment
import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.NotificationType
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.payment.PaymentRepository
import com.mini.asaas.payment.PaymentStatus
import com.mini.asaas.utils.MessageSourceUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import org.springframework.context.MessageSource

@GrailsCompileStatic
@Transactional
class NotificationService {

    MessageSource messageSource

    public Notification create(Object[] subject, Object[] body, Customer customer, NotificationType type, String status, Long paymentId) {
        NotificationAdapter adapter = new NotificationAdapter(subject, body, customer, type, status, paymentId)
        Notification notification = buildNotification(adapter)
//        Notification notification = buildNotification(subject, body, customer, type, status, paymentId)

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

    private Notification buildNotification(NotificationAdapter adapter) {
        Notification notification = new Notification()

        String subjectNotification = MessageSourceUtils.getMessage(
                'notify.payment.' + adapter.status + '.subject',
                adapter.subject as Object[]
        )

        String bodyNotification = MessageSourceUtils.getMessage(
                'notify.payment.' + adapter.status + '.body',
                adapter.body as Object[]
        )

        notification.subject = subjectNotification
        notification.body = bodyNotification
        notification.type = adapter.type
        notification.customer = adapter.customer
        notification.paymentId = adapter.paymentId

        return notification
    }
}
