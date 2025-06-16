package com.mini.asaas.base

import com.mini.asaas.validation.BusinessValidation

abstract class BaseValidator {

    final BusinessValidation validation

    public BaseValidator() {
        this.validation = new BusinessValidation()
    }

}
