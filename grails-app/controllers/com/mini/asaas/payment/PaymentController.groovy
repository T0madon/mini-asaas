package com.mini.asaas.payment

import com.mini.asaas.BaseController
import com.mini.asaas.Payment.Payment
import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import grails.plugin.springsecurity.annotation.Secured

class PaymentController extends BaseController{

    PaymentService paymentService

    def index() { }

    @Secured("permitAll")
    def save() {
        try {
            PaymentAdapter adapter = new PaymentAdapter(params)
            Payment payment = paymentService.save(adapter)
            createFlash("Cadastro de pagamento realizado!", AlertType.SUCCESS, true)
            render(status: 201, contentType: 'application/json')
        } catch (BusinessException exception) {
            createFlash("Ocorreu um erro no cadastro do pagamento: " + exception.getMessage(), AlertType.ERROR, false)
        } catch (Exception exception) {
            createFlash("Ocorreu um erro no cadastro!" + exception.getMessage(), AlertType.ERROR, false)
        }
    }
}
