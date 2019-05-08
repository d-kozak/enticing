package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.dto.*
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
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

    /**
     * Updates everything except from the password
     */
    fun updateUser(user: User) {
        requireCanEditUser(user)
        val originalEntity = userRepository.findById(user.id).orElseThrow { IllegalArgumentException("No user with id ${user.id}") }
        val updatedEntity = user.toEntity()
        updatedEntity.encryptedPassword = originalEntity.encryptedPassword
        userRepository.save(updatedEntity)
    }

    fun deleteUser(user: User) {
        requireCanEditUser(user)
        userRepository.delete(user.toEntity())
    }

    fun changePassword(userCredentials: ChangePasswordCredentials) {
        val userEntity = userRepository.findByLogin(userCredentials.login)
                ?: throw IllegalArgumentException("Unknown login ${userCredentials.login}")
        requireCanEditUser(userEntity.toUser())
        if (!encoder.matches(userCredentials.oldPassword, userEntity.encryptedPassword) && !currentUser!!.isAdmin) {
            throw IllegalArgumentException("Invalid password")
        }
        userEntity.encryptedPassword = encoder.encode(userCredentials.newPassword)
        userRepository.save(userEntity)
    }

    private fun requireCanEditUser(user: User) {
        if (!canEditUser(user)) {
            throw InsufficientRoleException("User $currentUser cannot edit user $user")
        }
    }

    private fun canEditUser(targetUser: User): Boolean = currentUser != null && (currentUser!!.isAdmin || currentUser!!.id == targetUser.id)

    val currentUser: User?
        get() = (SecurityContextHolder.getContext().authentication?.principal as UserEntity?)?.toUser()

}