package com.mini.asaas.payer

import com.mini.asaas.repository.Repository
import grails.compiler.GrailsCompileStatic
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class PayerRepository implements Repository<Payer, PayerRepository> {

    @Override
    void buildCriteria() {
        addCriteria {
            if (search.containsKey("customerId")) {
                eq("customer.id", Long.valueOf(search.customerId.toString()))
            }

            if (search.containsKey("cpfCnpj")) {
                eq("cpfCnpj", search.cpfCnpj)
            }

            if (search.containsKey("email")) {
                eq("email", search.email)
            }

        }

    }

    @Override
    BuildableCriteria getBuildableCriteria() {
        return Payer.createCriteria()
    }

    @Override
    List<String> listAllowedFilters() {
        return [
                "cpfCnpj",
                "email",
                "customerId"
        ]
    }
}
