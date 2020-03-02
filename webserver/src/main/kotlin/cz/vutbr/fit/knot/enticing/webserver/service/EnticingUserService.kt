package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.webserver.dto.*
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettingsId
import cz.vutbr.fit.knot.enticing.webserver.entity.SelectedMetadata
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.entity.defaultSelectedMetadata
import cz.vutbr.fit.knot.enticing.webserver.exception.InsufficientRoleException
import cz.vutbr.fit.knot.enticing.webserver.exception.InvalidPasswordException
import cz.vutbr.fit.knot.enticing.webserver.exception.ValueNotUniqueException
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.SelectedEntityMetadataRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.SelectedMetadataRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
@Incomplete("selected metadata from user and search settings is probably not deleted properly")
class EnticingUserService(private val userRepository: UserRepository, private val selectedMetadataRepository: SelectedMetadataRepository, private val selectedEntityMetadataRepository: SelectedEntityMetadataRepository, private val encoder: PasswordEncoder, private val searchSettingsRepository: SearchSettingsRepository, val userHolder: CurrentUserHolder, loggerFactory: LoggerFactory) : UserDetailsService {

    private val logger = loggerFactory.logger { }

    override fun loadUserByUsername(username: String): UserDetails = userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("UserSpecification with login $username not found")

    fun saveUser(newUser: UserCredentials) {
        val (login, password) = newUser
        if (userRepository.existsByLogin(login))
            throw ValueNotUniqueException("login", "Login $login is already taken")

        userRepository.save(UserEntity(login = login, encryptedPassword = encoder.encode(password)))
    }

    fun saveUser(newUser: CreateUserRequest): User {
        val (login, password, roles) = newUser
        if (userRepository.existsByLogin(login))
            throw ValueNotUniqueException("login", "Login $login is already taken")
        return userRepository.save(UserEntity(login = login, encryptedPassword = encoder.encode(password), roles = roles)).toUser()
    }

    /**
     * Updates everything except from the password and selected settings. These two are updated separately
     */
    fun updateUser(user: User) {
        requireCanEditUser(user)
        val originalEntity = userRepository.findById(user.id).orElseThrow { IllegalArgumentException("No user with id ${user.id}") }
        val updatedEntity = user.toEntity()
        updatedEntity.encryptedPassword = originalEntity.encryptedPassword
        updatedEntity.selectedSettings = originalEntity.selectedSettings
        userRepository.save(updatedEntity)
    }

    fun deleteUser(userId: Long) {
        val userEntity = userRepository.findById(userId).orElseThrow { java.lang.IllegalArgumentException("No user with id $userId") }
        requireCanEditUser(userEntity.toUser())
        userRepository.delete(userEntity)
    }

    fun changePassword(userCredentials: ChangePasswordCredentials) {
        val currentUser = userHolder.requireLoggedInUser()
        val userEntity = userRepository.findByLogin(userCredentials.login)
                ?: throw IllegalArgumentException("Unknown login ${userCredentials.login}")
        requireCanEditUser(userEntity.toUser())
        if (!encoder.matches(userCredentials.oldPassword, userEntity.encryptedPassword) && !currentUser.isAdmin) {
            throw InvalidPasswordException("Invalid password")
        }
        userEntity.encryptedPassword = encoder.encode(userCredentials.newPassword)
        userRepository.save(userEntity)
    }

    fun getAllUsers(): List<User> = userRepository.findAll().map(UserEntity::toUser)

    fun selectSettings(searchSettingsId: Long) {
        val currentUser = userHolder.requireLoggedInUser()
        val searchSettings = searchSettingsRepository.findById(searchSettingsId).orElseThrow { IllegalArgumentException("No settings with id $searchSettingsId found") }
        val currentUserEntity = userRepository.findById(currentUser.id).orElseThrow { IllegalArgumentException("User not located in db") }
        currentUserEntity.selectedSettings = searchSettings
        userRepository.save(currentUserEntity)
    }

    private fun requireCanEditUser(user: User) {
        if (!canEditUser(user)) {
            throw InsufficientRoleException("User ${userHolder.getCurrentUser()} cannot edit user $user")
        }
    }


    fun loadSelectedMetadata(searchSettingsId: SearchSettingsId): SelectedMetadata {
        val searchSettings = searchSettingsRepository.findById(searchSettingsId).orElseThrow { IllegalArgumentException("Cannot find searchSettings with id $searchSettingsId") }
        val user = userHolder.getCurrentUser()
        if (user != null) {
            val userEntity = userRepository.findByLogin(user.login)
                    ?: throw IllegalStateException("User $user is in the SecurityContextHolder, but does not have corresponding entity in the db")
            val selectedMetadata = userEntity.selectedMetadata[searchSettingsId]
            if (selectedMetadata != null) return selectedMetadata
        }
        return searchSettings.defaultMetadata ?: defaultSelectedMetadata
    }

    fun loadDefaultMetadata(searchSettingsId: SearchSettingsId): SelectedMetadata {
        val defaultMetadata = searchSettingsRepository.findById(searchSettingsId).orElseThrow { IllegalArgumentException("Cannot find searchSettings with id $searchSettingsId") }
                .defaultMetadata
        return if (defaultMetadata != null) {
            logger.info("loaded default metadata for $searchSettingsId: $defaultMetadata")
            return defaultMetadata
        } else defaultSelectedMetadata
    }

    private fun saveMetadata(metadata: SelectedMetadata): SelectedMetadata {
        metadata.entities = metadata.entities.mapValues { (_, entity) -> selectedEntityMetadataRepository.save(entity) }
        return selectedMetadataRepository.save(metadata)
    }

    fun saveSelectedMetadata(metadata: SelectedMetadata, searchSettingsId: SearchSettingsId) {
        val currentUser = userHolder.requireLoggedInUser()
        val userEntity = currentUser.let { userRepository.findByLogin(it.login) }
                ?: throw IllegalArgumentException("could not find necessary user information")
        userEntity.selectedMetadata[searchSettingsId] = saveMetadata(metadata)
    }

    private fun canEditUser(targetUser: User): Boolean = userHolder.requireLoggedInUser().let { currentUser -> currentUser.isAdmin || currentUser.id == targetUser.id }

    fun saveDefaultMetadata(id: SearchSettingsId, metadata: SelectedMetadata) {
        logger.info("saving default metadata for $id: $metadata")
        val user = userHolder.getCurrentUser()
        if (user == null || !user.isAdmin) throw throw InsufficientRoleException("User $user cannot set default settings")
        val searchSettings = searchSettingsRepository.findById(id).orElseThrow { IllegalArgumentException("Cannot find searchSettings with id $id") }
        searchSettings.defaultMetadata = saveMetadata(metadata)
    }
}

