package com.mini.asaas.user

import com.mini.asaas.repository.Repository
import grails.compiler.GrailsCompileStatic
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class UserRepository implements Repository<User, UserRepository> {

    @Override
    BuildableCriteria getBuildableCriteria() {
        return User.createCriteria()
    }

    @Override
    List<String> listAllowedFilters() {
        return [
            "username"
        ]
    }

    @Override
    void buildCriteria() {
        addCriteria {
            if (search.containsKey("username")) {
                eq("username", search.username)
            }
        }
    }
    
    @Override
    Boolean getDomainHasSoftDelete() {
        return false
    }
}