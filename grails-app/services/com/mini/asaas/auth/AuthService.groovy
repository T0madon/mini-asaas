package com.mini.asaas.auth

import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.user.User
import com.mini.asaas.user.UserRepository
import com.mini.asaas.user.adapters.UserLoginAdapter
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.crypto.password.PasswordEncoder

@GrailsCompileStatic
@Transactional
class AuthService {

    SpringSecurityService springSecurityService

    public User login(UserLoginAdapter adapter) {
        User user = validateBeforeLogin(adapter)
        springSecurityService.reauthenticate(user.username, adapter.password)
        
        return user
    }

    private User validateBeforeLogin(UserLoginAdapter adapter) {
        User validatedUser = UserRepository.query([username: adapter.email]).get()

        if (!validatedUser) {
            throw new BusinessException("E-mail ou senha inválidos.")
        }

        PasswordEncoder passwordEncoder = springSecurityService.getPasswordEncoder() as PasswordEncoder
        
        if (!passwordEncoder.matches(adapter.password, validatedUser.password)) {
            throw new BusinessException("E-mail ou senha inválidos.")
        }
        
        return validatedUser
    }
}