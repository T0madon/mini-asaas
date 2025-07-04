package com.mini.asaas.user

import com.mini.asaas.customer.Customer
import com.mini.asaas.base.BasePerson
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User extends BasePerson implements Serializable {

    private static final long serialVersionUID = 1

    String username

    String password

    boolean enabled = true

    boolean accountExpired

    boolean accountLocked

    boolean passwordExpired

    Customer customer

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        customer nullable: false
    }

    static mapping = {
        password column: '`password`'
    }
}
