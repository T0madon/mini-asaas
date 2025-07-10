package com.mini.asaas.payer

import com.mini.asaas.repository.Repository
import com.mini.asaas.utils.Utils
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

            if (search.containsKey("id[ne]")) {
                ne("id", Utils.toLong(search."id[ne]"))
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
                "customerId",
                "id[ne]"
        ]
    }
}
