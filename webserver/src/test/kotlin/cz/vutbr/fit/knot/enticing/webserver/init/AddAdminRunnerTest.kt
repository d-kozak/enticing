package cz.vutbr.fit.knot.enticing.webserver.init

import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import cz.vutbr.fit.knot.enticing.webserver.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder


internal class AddAdminRunnerTest {


    @Test
    fun `Nothing happens when at least one admin already present`() {
        val userRepository = mockk<UserRepository>()
        val encoder = mockk<PasswordEncoder>()
        val runner = AddAdminRunner(userRepository, encoder)
        every { userRepository.findAllAdmins() } returns listOf(UserEntity(login = "ferda", encryptedPassword = "dafsda"))
        runner.run(null)
        verify(exactly = 1) { userRepository.findAllAdmins() }
    }

    @Test
    fun `New admin should be created when no admin found in the database`() {
        val userRepository = mockk<UserRepository>()
        val encoder = mockk<PasswordEncoder>()
        val runner = AddAdminRunner(userRepository, encoder)
        val slot = slot<UserEntity>()
        every { userRepository.findAllAdmins() } returns listOf()
        every { userRepository.existsByLogin(any()) } returns false
        every { userRepository.save(capture(slot)) } returns UserEntity(login = "foo")
        every { encoder.encode(any()) } returns "encoded"

        runner.run(null)

        verify(exactly = 1) { userRepository.existsByLogin(any()) }
        verify(exactly = 1) { userRepository.findAllAdmins() }
        verify(exactly = 1) { encoder.encode(any()) }
        verify(exactly = 1) { userRepository.save<UserEntity>(any()) }
        assertThat(slot.isCaptured).isTrue()
        assertThat(slot.captured)
                .extracting { it.encryptedPassword }
                .isEqualTo("encoded")
        assertThat(slot.captured.roles.contains("ADMIN")).isTrue()
    }
}