package com.mini.asaas.base

import grails.gorm.dirty.checking.DirtyCheck

@DirtyCheck
abstract class BaseEntity {

    Date dateCreated

    Date lastUpdated

    Boolean deleted = false

    static mapping = {
        tablePerHierarchy false
    }
}
