package cz.vutbr.fit.knot.enticing.webserver.entity

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.persistence.PersistenceException
import javax.validation.ConstraintViolationException

@ExtendWith(SpringExtension::class)
@DataJpaTest
class UserSettingsJpaTest(
        @Autowired val entityManager: TestEntityManager
) {


    @Test
    fun `Should be ok for positive results per page`() {
        val userSettings = UserSettings(25)
        val user = UserEntity(login = "foo", encryptedPassword = "foo", userSettings = userSettings)
        entityManager.persistFlushFind(user)
    }


    @Test
    fun `Should fail for results per page more than 50`() {
        val userSettings = UserSettings(51)
        val user = UserEntity(login = "foo", encryptedPassword = "foo", userSettings = userSettings)
        assertThrows<ConstraintViolationException> {
            entityManager.persistFlushFind(user)
        }
    }

    @Test
    fun `Should fail for negative results per page`() {
        val userSettings = UserSettings(-10)
        val user = UserEntity(login = "foo", encryptedPassword = "foo", userSettings = userSettings)
        assertThrows<ConstraintViolationException> {
            entityManager.persistFlushFind(user)
        }
    }

    @Test
    fun `Should fail for not unique name`() {
        val searchSettings = SearchSettings(0, "foo", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"))
        entityManager.persistFlushFind(searchSettings)
        assertThrows<PersistenceException> { entityManager.persistFlushFind(searchSettings) }
    }
}