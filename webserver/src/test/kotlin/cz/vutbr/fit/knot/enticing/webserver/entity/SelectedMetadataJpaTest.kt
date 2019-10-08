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
internal class SelectedMetadataJpaTest(
        @Autowired val entityManager: TestEntityManager
) {

    @Test
    fun `test persists`() {
        val attributeList = SelectedEntityMetadata("name", "age")
        assertThat(entityManager.persistFlushFind(attributeList))
                .extracting { it.id }
                .isNotEqualTo(0)

        val metadata = SelectedMetadata(indexes = listOf("token", "lemma", "parword"), entities = mapOf(
                "person" to attributeList
        ))


        assertThat(entityManager.persistFlushFind(metadata))
                .extracting { it.id }
                .isNotEqualTo(0)
    }
}

