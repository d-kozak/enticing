package cz.vutbr.fit.knot.enticing.webserver.repository

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
        @Autowired val userRepository: UserRepository
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
}