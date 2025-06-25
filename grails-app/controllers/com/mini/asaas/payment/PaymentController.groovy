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

    @Secured("permitAll")
    def update() {
        try {
            PaymentAdapter adapter = new PaymentAdapter(params)
            Long id = params.id as Long

            Payment payment = paymentService.update(adapter, id)
            createFlash("Pagamento atualizado com sucesso!", AlertType.SUCCESS, true)
            render(status: 201, contentType: 'application/json')
        }catch (BusinessException exception) {
            createFlash("Ocorreu um erro ao atualizar pagamento: " + exception.getMessage(), AlertType.ERROR, false)
        } catch (Exception exception) {
            createFlash("Ocorreu um erro ao atualizar pagamento: " + exception.getMessage(), AlertType.ERROR, false)
        }
    }

    @Secured("permitAll")
    def delete() {
        Long id = params.id as Long

        try {
            paymentService.delete(id)
            createFlash("Pagamento deletado com sucesso!", AlertType.SUCCESS, true)
            render(status: 201, contentType: 'application/json')
        } catch (BusinessException exception) {
            createFlash("Ocorreu um erro ao deletar: " + exception.getMessage(), AlertType.ERROR, false)
            render(status: 400, contentType: 'application/json', text: '{"erro": "Requisição Inválida"}')
        } catch (Exception exception) {
            createFlash("Ocorreu um erro ao deletar: " + exception.getMessage(), AlertType.ERROR, false)
            render(status: 400, contentType: 'application/json', text: '{"erro": "Requisição Inválida"}')
        }
    }
}
