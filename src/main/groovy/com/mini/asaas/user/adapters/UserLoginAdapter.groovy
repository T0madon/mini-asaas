package com.mini.asaas.user.adapters

import com.mini.asaas.utils.Utils
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class UserLoginAdapter { 

    String email

    String password

    public UserLoginAdapter(Map originalParams) {
        if (!originalParams) return
        
        Map<String, String> params = Utils.normalizeParams(originalParams)
        
        this.email = params.email
        this.password = params.password
    }
}