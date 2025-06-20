package com.mini.asaas.utils

import grails.util.Holders

class MessageSourceUtils {

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        Object bean = Holders.applicationContext.getBean("messageSource")
        return bean.getMessage(code, args, defaultMessage, Locale.getDefault())
    }

    public static String getMessage(String code) {
        getMessage(code, null, "")
    }

    public static String getMessage(String code, Object[] args) {
        getMessage(code, args, "")
    }

    public static String getEnumLabel(Enum enumValue) {
        String className = StringUtils.pascalToCamelCase(enumValue.class.simpleName)
        String code = "${className}.${enumValue.name()}.label"
        return getMessage(code)
    }
}
