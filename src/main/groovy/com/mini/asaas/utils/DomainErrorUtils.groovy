package com.mini.asaas.utils

import com.mini.asaas.validation.BusinessRuleError
import grails.compiler.GrailsCompileStatic
import org.grails.datastore.gorm.GormEntity
import org.springframework.validation.ObjectError

@GrailsCompileStatic
class DomainErrorUtils {

    public static GormEntity addError(GormEntity entity, String message) {
        entity.errors.reject("", null, message)
        return entity
    }

    public static GormEntity addBusinessRuleError(GormEntity entity, BusinessRuleError error) {
        addError(entity, error.message)
        return entity
    }

    public static GormEntity addBusinessRuleErrors(GormEntity entity, List<BusinessRuleError> errors) {
        errors.forEach { addBusinessRuleError(entity, it) }
        return entity
    }

    public static String getFirstValidationMessage(GormEntity entity) {
        ObjectError error = entity.errors.allErrors?.first() ?: null
        if (error == null) return null
        return MessageSourceUtils.getMessage(error.codes?.first(), error.arguments, error.defaultMessage)
    }
}
