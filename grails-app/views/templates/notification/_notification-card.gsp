<%@ page import="com.mini.asaas.utils.DateFormatUtils" %>
<%
    Map status = [
            PAYMENT_CREATED: 'success',
            PAYMENT_RECEIVED: 'success',
            PAYMENT_OVERDUE: 'in progress',
            PAYMENT_DELETED: 'error'
    ]

    Map icons = [
            PAYMENT_CREATED: 'money',
            PAYMENT_RECEIVED: 'check-circle',
            PAYMENT_OVERDUE: 'clock',
            PAYMENT_DELETED: 'x'
    ]
%>

<atlas-notification-center-card
        href="${createLink(controller: 'payment', action: 'show', id: notification.paymentId)}"
        header="${notification.subject}"
        icon="${icons."${notification.type}"}"
        date="${DateFormatUtils.format(notification.dateCreated)}"
        status="${status."${notification.type}"}"
>
    ${notification.body}
</atlas-notification-center-card>