package com.mini.asaas.validation

class BusinessValidation {

    List<BusinessRuleError> errors = []

    public Boolean isValid() {
        return this.errors.isEmpty()
    }

    public void addError(String code) {
        this.errors.add(new BusinessRuleError(code))
    }

    public void addError(String code, List args) {
        this.errors.add(new BusinessRuleError(code, args))
    }

    public String getFirstErrorCode() {
        return this.isValid() ? null : this.errors.first().getCode()
    }
}
