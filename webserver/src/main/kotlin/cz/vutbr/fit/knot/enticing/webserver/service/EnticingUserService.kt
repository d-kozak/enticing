package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.entity.EnticingUser
import cz.vutbr.fit.knot.enticing.webserver.repository.EnticingUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class EnticingUserService(private val userRepository: EnticingUserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails = userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("User with login $username not found")

    fun saveUser(newUser: EnticingUser) {
        userRepository.save(newUser)
    }

    fun deleteUser(user: EnticingUser) {
        userRepository.delete(user)
    }

    fun updateUser(user: EnticingUser) {
        userRepository.save(user)
    }
}