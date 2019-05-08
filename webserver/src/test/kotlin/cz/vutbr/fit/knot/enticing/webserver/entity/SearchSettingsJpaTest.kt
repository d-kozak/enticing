package cz.vutbr.fit.knot.enticing.webserver.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.validation.ConstraintViolationException

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class SearchSettingsJpaTest(
        @Autowired val entityManager: TestEntityManager
) {

    @Test
    fun `test insert`() {
        val settingsOne = SearchSettings(0, "foo")
        assertThat(entityManager.persistFlushFind(settingsOne))
                .extracting { it.id }
                .isNotEqualTo(0)
    }

    @Test
    fun `name cant be empty`() {
        val settingsOne = SearchSettings(0, "")
        assertThrows<ConstraintViolationException> {
            entityManager.persistFlushFind(settingsOne)
        }
    }
}