package com.mini.asaas.user

import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.PersonType
import com.mini.asaas.user.User
import grails.plugin.springsecurity.annotation.Secured


class UserController {

    UserService userService
    def index() {
        redirect action: 'create'
    }
    @Secured(['permitAll'])
    def create() {
        println "exibir formulario"
        render view: 'signUp'
    }
    
    //parametros como string
    @Secured(['permitAll'])
    def save(String email, String password,
             String customerName, String, String customerCpfCnpj,
             String customerPostalCode, String customerState, String customerCity,
             String customerNeighborhood, String customerAddress, String customerAddressNumber,
             String customerComplement, String customerPersonTypeString) {
            println "salvar usuario"

        try {
            def personType = PersonType.fromString(customerPersonTypeString)

            User user = userService.registerUser(
                email, password,
                customerName, customerCpfCnpj,
                customerPostalCode, customerState, customerCity,
                customerNeighborhood, customerAddress, customerAddressNumber,
                customerComplement, personType
            )

            flash.message = "Usuário cadastrado com sucesso!"
            println "uUsuário cadastrado com sucesso"
            redirect(controller: 'login')

        } catch (Exception exception) {
            flash.message = "Erro no cadastro: ${exception.message}"
            redirect(action: 'create', params: params)

        }
    }
}