package com.mini.asaas.user.adapters

import com.mini.asaas.utils.Utils
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class UserSaveAdapter { 

    String email

    String password

    public SaveUserAdapter(Map originalParams) {
        Map<String, String> params = Utils.normalizeParams(originalParams)
        if (!params) return
        this.email = params.email
        this.password = params.password
    }
}

