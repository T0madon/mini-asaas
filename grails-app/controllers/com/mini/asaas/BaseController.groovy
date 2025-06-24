package com.mini.asaas

import com.mini.asaas.enums.AlertType
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BaseController {

    protected void createFlash(String message, AlertType alertType, Boolean success) {
        flash.message = message
        flash.status = alertType
        flash.success = success
    }
}
