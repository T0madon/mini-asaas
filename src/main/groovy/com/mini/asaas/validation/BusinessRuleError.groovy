package com.mini.asaas.validation

import com.mini.asaas.utils.MessageSourceUtils

class BusinessRuleError {

    String code

    List args

    public BusinessRuleError(String code) {
        this.code = code
    }

    public BusinessRuleError(String code, List args) {
        this.code = code
        this.args = args
    }

    public String getMessage() {
        return MessageSourceUtils.getMessage(this.code, this.args as Object[])
    }

}
