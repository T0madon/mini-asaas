package com.mini.asaas.payment

import com.mini.asaas.BaseController
import com.mini.asaas.Payment.Payment
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import grails.plugin.springsecurity.annotation.Secured

class PaymentController extends BaseController{

    PaymentService paymentService

    def index() { }

    @Secured("permitAll")
    def show() { }

    @Secured("permitAll")
    def save() {
        try {
            PaymentSaveAdapter adapter = new PaymentSaveAdapter(params)
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()
            Payment payment = paymentService.save(customerId, adapter)
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
            PaymentUpdateAdapter adapter = new PaymentUpdateAdapter(params)
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()

            Payment payment = paymentService.update(customerId, adapter)
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
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()
        try {
            paymentService.delete(customerId, id)
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

    @Secured("permitAll")
    def restore() {
        Long id = params.id as Long
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()

        println("\nid: " + id)

        try {
            paymentService.restore(customerId, id)
            createFlash("Pagamento restaurado com sucesso!", AlertType.SUCCESS, true)
            render(status: 201, contentType: 'application/json')
        } catch (BusinessException exception) {
            createFlash("Houve um erro: " + exception.getMessage(), AlertType.ERROR, false)
            render(status: 400, contentType: 'application/json', text: '{"erro": "Requisição Inválida"}')
        } catch (Exception exception) {
            log.error(exception)
            createFlash("Houve um erro: " + exception.getMessage(), AlertType.ERROR, false)
            render(status: 400, contentType: 'application/json', text: '{"erro": "Requisição Inválida"}')
        }
    }
}
