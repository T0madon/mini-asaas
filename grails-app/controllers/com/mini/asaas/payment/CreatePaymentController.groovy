package com.mini.asaas.payment

import com.mini.asaas.BaseController
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.payer.Payer
import com.mini.asaas.payer.PayerService
import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class CreatePaymentController extends BaseController{

    PayerService payerService

    def index() {
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()

        List<Payer> payerList = payerService.list(customerId)
        return [payerList: payerList]
    }
}
