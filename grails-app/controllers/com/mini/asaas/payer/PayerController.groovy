package com.mini.asaas.payer

import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.utils.StringUtils
import grails.plugin.springsecurity.annotation.Secured

class PayerController {

    PayerService payerService

    def index() {
    }

    @Secured("permitAll")
    def save() {
        try {
            PayerAdapter adapter = new PayerAdapter(params)
            payerService.save(adapter)
            render(status: 201, contentType: 'application/json')
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro durante o cadastro, tente novamente."
        }
    }

    @Secured("permitAll")
    def update() {
        try {
            PayerAdapter adapter = new PayerAdapter(params)
            Long id = params.id as Long
            println("Id " + id)
            println("Agora todos os campos do Adapter")
            adapter.properties.each {key, value ->
                println("Atributo: $key, Valor: $value")
            }
            println("FIM DOS ATRIBUTOS DO ADAPTER")
            payerService.update(adapter, id)
            println("Payer atualizado")
            flash.message = "Pagador atualizado com sucesso"
            flash.status = AlertType.SUCCESS.getValue()
            render(status: 201, contentType: 'application/json')
        } catch (BusinessException error) {
            flash.code = error.code
            flash.message = error.getMessage()
            flash.status = AlertType.ERROR.getValue()
        } catch (Exception error) {
            flash.message = "Ocorreu um erro durante o cadastro."
            flash.status = AlertType.ERROR.getValue()
        }
    }

    @Secured("permitAll")
    def delete() {
        try {
            Long id = params.id as Long
            if (!id) {
                def payerByEmail = Payer.findByEmail(params.email as String)
                if (payerByEmail) id = payerByEmail.id
            }

            if (!id) {
                def payerByCpfCnpj = Payer.findByCpfCnpj(StringUtils.removeNonNumeric(params.cpfCnpj as String))
                if (payerByCpfCnpj) id = payerByCpfCnpj.id
            }

            if (!id) return

            payerService.delete(id)

            render(status: 200, contentType: 'application/json')

        } catch (Exception exception) {
            flash.message = "Ocorreu um erro durante o delete, tente novamente."
        }
    }
}
