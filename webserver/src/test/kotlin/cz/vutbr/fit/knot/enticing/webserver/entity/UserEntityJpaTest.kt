package cz.vutbr.fit.knot.enticing.webserver.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class UserEntityJpaTest(
        @Autowired val entityManager: TestEntityManager
) {

    @Test
    fun `Test persist`() {
        val user = UserEntity()
        assertThat(entityManager.persistFlushFind(user))
                .extracting { it.id }
                .isNotEqualTo(0)
    }
}