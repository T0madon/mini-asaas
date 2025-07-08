package com.mini.asaas.notification

import com.mini.asaas.base.BaseEntity
import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.NotificationType

class Notification extends BaseEntity {

    String subject

    String body

    NotificationType type

    Customer customer
}
