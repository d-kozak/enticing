package cz.vutbr.fit.knot.enticing.webserver.repository

import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class SearchSettingsRepositoryTest(
        @Autowired val entityManager: TestEntityManager,
        @Autowired val searchSettingsRepository: SearchSettingsRepository
) {

    @Test
    fun `find default should return null`() {
        assertThat(searchSettingsRepository.findByDefaultIsTrue())
                .isNull()
    }

    @Test
    fun `find default should return prev default`() {
        val settingsOne = SearchSettings(0, "foo", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"), default = true)
        entityManager.persistFlushFind(settingsOne)
        assertThat(searchSettingsRepository.findByDefaultIsTrue())
                .isEqualTo(settingsOne)
    }
}