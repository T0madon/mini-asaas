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

    protected Integer getLimitPerPage() {
        Integer defaultLimitPerPage = (!params.itemsPerPage) ? 10 : 50

        return getDefaultLimitPerPage(defaultLimitPerPage)
    }

    protected Integer getOffset() {

        if (params.containsKey("page")) {
            Integer currentPage = Integer.valueOf(params.page as Integer ?: 1)
            return (currentPage - 1) * getLimitPerPage()
        }

        if (params.offset == 'undefined') params.offset = null
        return Integer.valueOf(params.offset as Integer ?: 0)
    }

    private Integer getDefaultLimitPerPage(Integer limitPerPage) {
        if (params.containsKey("itemsPerPage")) {
            String itemsPerPage = params.itemsPerPage?.toString()
            if (!itemsPerPage?.isNumber()) params.itemsPerPage = null

            Integer parsed = params.itemsPerPage ? Integer.valueOf(params.itemsPerPage.toString()): limitPerPage

            return Math.min(parsed, limitPerPage)
        }

        String max = params.max?.toString()
        if (!max?.isNumber()) params.max = null

        Integer parsedMax = params.max ? Integer.valueOf(params.max.toString()): limitPerPage

        return Math.min(parsedMax, limitPerPage)
    }
}
