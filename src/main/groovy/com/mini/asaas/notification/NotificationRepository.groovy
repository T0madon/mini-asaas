package com.mini.asaas.notification

import com.mini.asaas.repository.Repository
import grails.compiler.GrailsCompileStatic
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class NotificationRepository implements Repository<Notification, NotificationRepository> {

    @Override
    void buildCriteria() {
        addCriteria {
            if (search.containsKey("type")) {
                eq("type", search.type.toString())
            }

            if (search.containsKey("customerId")) {
                eq("customer.id", search.customerId)
            }

            if (search.containsKey("paymentId")) {
                eq("payment.id", search.paymentId)
            }
        }
    }

    @Override
    BuildableCriteria getBuildableCriteria() {
        return Notification.createCriteria()
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "type",
                "customerId",
                "paymentId"
        ]
    }
}
