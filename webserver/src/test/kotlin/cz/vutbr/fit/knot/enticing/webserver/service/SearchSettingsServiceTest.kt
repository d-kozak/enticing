package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.exception.ValueNotUniqueException
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*

internal class SearchSettingsServiceTest {

    val searchSettingsRepository = mockk<SearchSettingsRepository>()

    val userRepository = mockk<UserRepository>()

    val searchSettingsService = SearchSettingsService(searchSettingsRepository, userRepository)

    @Nested
    inner class GetAll {
        @Test
        fun `When not logged in only non private setting should be visible`() {
            val searchSettings = listOf(SearchSettings(1, "foo"), SearchSettings(2, "bar"), SearchSettings(3, "baz"))
            every { searchSettingsRepository.findByPrivateIsFalse() } returns searchSettings

            val result = searchSettingsService.getAll(null)
            assertThat(result).isEqualTo(searchSettings)
            verify { searchSettingsRepository.findByPrivateIsFalse() }

            clearMocks(searchSettingsRepository, userRepository)

        }

        @Test
        fun `For normal user non private setting should be visible`() {
            val searchSettings = listOf(SearchSettings(1, "foo"), SearchSettings(2, "bar"), SearchSettings(3, "baz"))
            every { searchSettingsRepository.findByPrivateIsFalse() } returns searchSettings

            val result = searchSettingsService.getAll(UsernamePasswordAuthenticationToken("", ""))
            assertThat(result).isEqualTo(searchSettings)
            verify { searchSettingsRepository.findByPrivateIsFalse() }

            clearMocks(searchSettingsRepository, userRepository)

        }

        @Test
        fun `For admin all settings should be visible`() {
            val searchSettings = listOf(SearchSettings(1, "foo"), SearchSettings(2, "bar"), SearchSettings(3, "baz"))
            every { searchSettingsRepository.findAll() } returns searchSettings

            val result = searchSettingsService.getAll(UsernamePasswordAuthenticationToken("", "", mutableListOf(SimpleGrantedAuthority("ROLE_ADMIN"))))
            assertThat(result).isEqualTo(searchSettings)
            verify { searchSettingsRepository.findAll() }

            clearMocks(searchSettingsRepository, userRepository)


        }

    }


    @Test
    fun `create should fail name not unique`() {
        val searchSettings = SearchSettings(1, "foo", annotationServer = "foo.baz", annotationDataServer = "baz.paz", servers = setOf("127.0.0.1"))
        every { searchSettingsRepository.existsByName("foo") } returns true

        assertThrows<ValueNotUniqueException> { searchSettingsService.create(searchSettings) }
        clearMocks(searchSettingsRepository, userRepository)
    }


    @Test
    fun `Change default no previous default`() {
        val searchSettings = SearchSettings(id = 42, name = "foo")

        every { searchSettingsRepository.save(searchSettings) } returns searchSettings
        every { searchSettingsRepository.findByDefaultIsTrue() } returns null
        every { searchSettingsRepository.findById(42) } returns Optional.of(searchSettings)

        searchSettingsService.setDefault(42)

        verify { searchSettingsRepository.save(searchSettings) }
        verify { searchSettingsRepository.findById(42) }
        assertThat(searchSettings.default).isTrue()
        clearMocks(searchSettingsRepository, userRepository)

    }

    @Test
    fun `Change default with previous`() {
        val prev = SearchSettings(id = 21, name = "foo", default = true)
        val new = SearchSettings(id = 42, name = "foo")

        every { searchSettingsRepository.save(new) } returns new
        every { searchSettingsRepository.findByDefaultIsTrue() } returns prev
        every { searchSettingsRepository.findById(42) } returns Optional.of(new)

        searchSettingsService.setDefault(42)

        verify { searchSettingsRepository.save(prev) }
        verify { searchSettingsRepository.save(new) }
        verify { searchSettingsRepository.findById(42) }
        assertThat(prev.default).isFalse()
        assertThat(new.default).isTrue()
        clearMocks(searchSettingsRepository, userRepository)
    }

}