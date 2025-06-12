package com.mini.asaas.user

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class UserRole implements Serializable {

    private static final long serialVersionUID = 1

    User user
    Role role

    @Override
    boolean equals(other) {
        if (other instanceof UserRole) {
            other.userId == user?.id && other.roleId == role?.id
        }
    }

    @Override
    int hashCode() {
        int hashCode = HashCodeHelper.initHash()
        if (user) {
            hashCode = HashCodeHelper.updateHash(hashCode, user.id)
        }
        if (role) {
            hashCode = HashCodeHelper.updateHash(hashCode, role.id)
        }
        hashCode
    }

    public static UserRole get(long userId, long roleId) {
        criteriaFor(userId, roleId).get()
    }

    public static boolean exists(long userId, long roleId) {
        criteriaFor(userId, roleId).count()
    }

    private static DetachedCriteria criteriaFor(long userId, long roleId) {
        UserRole.where {
            user == User.load(userId) &&
            role == Role.load(roleId)
        }
    }

    public static UserRole create(User user, Role role, boolean flush = false) {
        def instance = new UserRole(user: user, role: role)
        instance.save(flush: flush)
        return instance
    }

    public static boolean remove(User user1, Role role1) {
        if (user1 != null && role1 != null) {
            return UserRole.where { user == user1 && role == role1 }.deleteAll()
        }
        return false
    }

    public static int removeAll(User user1) {
        return user1 == null ? 0 : UserRole.where { user == user1 }.deleteAll() as int
    }

    public static int removeAll(Role role1) {
        return role1 == null ? 0 : UserRole.where { role == role1 }.deleteAll() as int
    }

    static constraints = {
        user nullable: false
        role nullable: false, validator: { Role role2, UserRole userRoler ->
            if (userRoler.user?.id) {
                if (UserRole.exists(userRoler.user.id, role2.id)) {
                    return ['userRole.exists']
                }
            }
        }
    }

    static mapping = {
        id composite: ['user', 'role']
        version false
    }
}
