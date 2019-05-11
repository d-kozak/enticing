package cz.vutbr.fit.knot.enticing.webserver.repository

import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@DataJpaTest
class UserRepositoryTest(
        @Autowired val entityManager: TestEntityManager,
        @Autowired val userRepository: UserRepository,
        @Autowired val searchSettingsRepository: SearchSettingsRepository
) {

    @Test
    fun `findByLogin no one should be found`() {
        assertThat(userRepository.findByLogin("dkozak"))
                .isNull()
    }

    @Test
    fun `findByLogin one user should be found`() {
        entityManager.persistFlushFind(UserEntity(login = "dkozak", encryptedPassword = "aaa"))
        assertThat(userRepository.findByLogin("dkozak"))
                .isNotNull
                .extracting { it!!.login }
                .isEqualTo("dkozak")
    }

    @Test
    fun `Verify that save updates entity when the same id is used`() {
        val user = entityManager.persistFlushFind(UserEntity(login = "abc", encryptedPassword = "vbb"))
        assertThat(userRepository.findAll().size).isEqualTo(1)

        val userInNewRequest = UserEntity(login = "bcd", encryptedPassword = "aaa").apply { id = user.id }

        userRepository.save(userInNewRequest)
        assertThat(userRepository.findAll().size).isEqualTo(1)
        assertThat(userRepository.findById(user.id))
                .map { it.login }
                .isEqualTo(Optional.of("bcd"))

    }


    @Test
    fun `detach settings from user`() {
        val settingsToDelete = searchSettingsRepository.save(SearchSettings(0, "to delete", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1")))
        val settingsThatStay = SearchSettings(0, "please dont leave me", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"))

        searchSettingsRepository.save(settingsThatStay)

        userRepository.save(UserEntity(login = "user1", encryptedPassword = "aaa", selectedSettings = settingsThatStay))
        userRepository.save(UserEntity(login = "user2", encryptedPassword = "aaa", selectedSettings = settingsToDelete))
        userRepository.save(UserEntity(login = "user3", encryptedPassword = "aaa", selectedSettings = settingsThatStay))

        userRepository.detachSettingsFromAllUsers(settingsToDelete)
        searchSettingsRepository.delete(settingsToDelete)

        val invalid = userRepository.findAll()
                .filter { it.selectedSettings?.id == settingsToDelete.id }
        if (invalid.isNotEmpty()) {
            throw AssertionError("Following settings has stale selected settings $invalid")
        }
    }
}