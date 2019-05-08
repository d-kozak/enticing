package cz.vutbr.fit.knot.enticing.webserver.entity

import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataJpaTest
@ExtendWith(SpringExtension::class)
class UserWithSearchSettingsJpaTest(
        @Autowired val entityManager: TestEntityManager,
        @Autowired val userRepository: UserRepository
) {

    @Test
    fun `persis both`() {
        val searchSettings = entityManager.persistFlushFind(SearchSettings(name = "settings1"))
        assertThat(searchSettings.id).isNotEqualTo(0)
        val user = entityManager.persistAndFlush(UserEntity(login = "John", encryptedPassword = "abc", selectedSettings = searchSettings))
        assertThat(user.id).isNotEqualTo(0)
        assertThat(userRepository.findById(user.id))
                .isPresent
                .get()
                .extracting { it.selectedSettings }
                .isEqualTo(searchSettings)
    }
}