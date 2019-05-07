package cz.vutbr.fit.knot.enticing.webserver.dto

import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UserConversionTest {


    @Test
    fun `From entity to dto`() {
        val userEntity = UserEntity(2, "123", "abc", true, mutableSetOf("USER"), 21)
        val user = userEntity.toUser()
        assertNoInformationLost(user, userEntity)
    }

    @Test
    fun `From dto to entity`() {
        val user = User(1, "111", false, setOf("FOO"), 10)
        val entity = user.toEntity()
        assertNoInformationLost(user, entity)
    }

    @Test
    fun `From dto with password to entity`() {
        val user = UserWithPassword(1, "111", "abc", false, setOf("FOO"), selectedSettings = 10)
        val entity = user.toEntity()
        assertNoInformationLost(user, entity)
        assertThat(user.password)
                .isEqualTo(entity.encryptedPassword)
    }

    private fun assertNoInformationLost(user: User, userEntity: UserEntity) {
        assertThat(user.login).isEqualTo(userEntity.login)
        assertThat(user.id).isEqualTo(userEntity.id)
        assertThat(user.active).isEqualTo(userEntity.active)
        assertThat(user.roles).isEqualTo(userEntity.roles)
        assertThat(user.selectedSettings).isEqualTo(userEntity.selectedSettings)
    }
}