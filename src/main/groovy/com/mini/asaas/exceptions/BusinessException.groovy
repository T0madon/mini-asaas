package com.mini.asaas.exceptions

class BusinessException extends RuntimeException {

    public String code

    public BusinessException(String message) {
        super(message)
    }

    public BusinessException(String message, String code) {
        super(message)
        this.code = code
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause)
    }
}
