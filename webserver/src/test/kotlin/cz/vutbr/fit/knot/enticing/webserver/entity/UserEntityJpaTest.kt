package cz.vutbr.fit.knot.enticing.webserver.entity

import org.assertj.core.api.Assertions.assertThat
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
internal class UserEntityJpaTest(
        @Autowired val entityManager: TestEntityManager
) {

    @Test
    fun `Test persist`() {
        val user = UserEntity(login = "ferda", encryptedPassword = "sadfsda")
        assertThat(entityManager.persistFlushFind(user))
                .extracting { it.id }
                .isNotEqualTo(0)
    }

    @Test
    fun `Should fail for empty login`() {
        assertThrows<ConstraintViolationException> {
            val user = UserEntity(encryptedPassword = "dsafsd")
            entityManager.persistFlushFind(user)
        }
    }

    @Test
    fun `Should fail for empty password`() {
        assertThrows<ConstraintViolationException> {
            val user = UserEntity(login = "ABC")
            entityManager.persistFlushFind(user)
        }
    }

    @Test
    fun `Should fail for not unique login`() {
        entityManager.persistFlushFind(UserEntity(login = "ferda", encryptedPassword = "dafsda"))
        assertThrows<PersistenceException> { entityManager.persistFlushFind(UserEntity(login = "ferda", encryptedPassword = "aaa")) }
    }
}