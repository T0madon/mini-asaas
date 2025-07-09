package com.mini.asaas.notification

import com.mini.asaas.BaseController
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.enums.AlertType
import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
class NotificationController extends BaseController {

    NotificationService notificationService

    @Secured("permitAll")
    def list() {
        try {
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()
            Integer limitPage = getDefaultLimitPerPage()
            Integer offsetPage = getOffset()

            List<Notification> notifications = notificationService.list(customerId, limitPage, offsetPage)

            render(template: '/templates/notification/center', model: [notifications: notifications])
        } catch (Exception exception) {
            createFlash("Ocorreu um erro ao mostrar as notificações!" + exception.getMessage(), AlertType.ERROR, false)
        }
    }
}
