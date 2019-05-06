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
internal class EnticingUserJpaTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Test
    fun `Test persist`() {
        val user = EnticingUser("dkozak")
        assertThat(entityManager.persistFlushFind(user))
                .extracting { it.id }
                .isNotEqualTo(0)
    }
}