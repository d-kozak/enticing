package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserWithPassword
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class EnticingUserService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails = userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("UserSpecification with login $username not found")

    fun saveUser(newUser: UserWithPassword) {
        userRepository.save(newUser.toEntity())
    }

    fun deleteUser(user: User) {
        userRepository.delete(user.toEntity())
    }

    fun updateUser(user: User) {
        userRepository.save(user.toEntity())
    }
}