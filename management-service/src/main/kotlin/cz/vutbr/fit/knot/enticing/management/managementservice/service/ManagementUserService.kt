package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.*
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.UserEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.exception.InsufficientRoleException
import cz.vutbr.fit.knot.enticing.management.managementservice.exception.InvalidPasswordException
import cz.vutbr.fit.knot.enticing.management.managementservice.exception.ValueNotUniqueException
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional
@Service
class ManagementUserService(
        private val userRepository: UserRepository,
        private val encoder: PasswordEncoder,
        loggerFactory: LoggerFactory
) : UserDetailsService {

    private val logger = loggerFactory.logger { }

    override fun loadUserByUsername(username: String): UserDetails = userRepository.findByIdOrNull(username)
            ?: throw UsernameNotFoundException("User with login $username")

    fun singUp(newUser: UserCredentials) {
        val (login, password) = newUser
        if (userRepository.existsByLogin(login))
            throw ValueNotUniqueException("login", "Login $login is already taken")

        userRepository.save(UserEntity(login = login, encryptedPassword = encoder.encode(password)))
    }

    fun createNewUser(newUser: CreateUserRequest): User {
        val (login, password, roles) = newUser
        if (userRepository.existsByLogin(login))
            throw ValueNotUniqueException("login", "Login $login is already taken")
        return userRepository.save(UserEntity(login = login, encryptedPassword = encoder.encode(password), roles = roles.toMutableSet())).toUser()
    }


    /**
     * Updates everything except from the password. Password is updated separately
     */
    fun updateUser(user: User) {
        requireCanEditUser(user)
        val originalEntity = userRepository.findById(user.login).orElseThrow { IllegalArgumentException("No user with login ${user.login}") }
        val updatedEntity = user.toEntity()
        val currentUser = getCurrentUser()!!
        if (!currentUser.isAdmin) {
            // if not admin, not not change roles
            updatedEntity.roles.clear()
            updatedEntity.roles.addAll(originalEntity.roles)
        }
        updatedEntity.encryptedPassword = originalEntity.encryptedPassword
        userRepository.save(updatedEntity)
    }

    fun deleteUser(login: String) {
        val userEntity = userRepository.findById(login).orElseThrow { java.lang.IllegalArgumentException("No user with login $login") }
        requireCanEditUser(userEntity.toUser())
        userRepository.delete(userEntity)
    }

    fun changePassword(userCredentials: ChangePasswordCredentials) {
        val currentUser = requireLoggedInUser()
        val userEntity = userRepository.findByIdOrNull(userCredentials.login)
                ?: throw IllegalArgumentException("Unknown login ${userCredentials.login}")
        requireCanEditUser(userEntity.toUser())
        if (!encoder.matches(userCredentials.oldPassword, userEntity.encryptedPassword) && !currentUser.isAdmin) {
            throw InvalidPasswordException("Invalid password")
        }
        userEntity.encryptedPassword = encoder.encode(userCredentials.newPassword)
        userRepository.save(userEntity)
    }

    fun getUsers(pageable: Pageable): Page<User> = userRepository.findAll(pageable).map(UserEntity::toUser)

    private fun requireCanEditUser(user: User) {
        if (!canEditUser(user)) {
            throw InsufficientRoleException("User ${getCurrentUser()} cannot edit user $user")
        }
    }

    private fun canEditUser(targetUser: User): Boolean = requireLoggedInUser().let { currentUser -> currentUser.isAdmin || currentUser.login == targetUser.login }

    private fun requireLoggedInUser() = getCurrentUser()
            ?: throw IllegalStateException("This operation require user to be logged in")

    fun getCurrentUser(): User? {
        val authentication = SecurityContextHolder.getContext().authentication ?: return null
        val login = authentication.name
        val roles = authentication.authorities.map { it.authority.split("_")[1] }.toSet()
        return User(login, true, roles)
    }
}