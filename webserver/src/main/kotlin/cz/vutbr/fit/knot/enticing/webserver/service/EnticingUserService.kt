package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.dto.User
import cz.vutbr.fit.knot.enticing.webserver.dto.UserCredentials
import cz.vutbr.fit.knot.enticing.webserver.dto.toEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class EnticingUserService(private val userRepository: UserRepository, private val encoder: PasswordEncoder) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails = userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("UserSpecification with login $username not found")

    fun saveUser(newUser: UserCredentials) {
        val (login, password) = newUser
        userRepository.save(UserEntity(login = login, encryptedPassword = encoder.encode(password)))
    }

    fun deleteUser(user: User) {
        userRepository.delete(user.toEntity())
    }

    fun updateUser(user: User) {
        userRepository.save(user.toEntity())
    }
}