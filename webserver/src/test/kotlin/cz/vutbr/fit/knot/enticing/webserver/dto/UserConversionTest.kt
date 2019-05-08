package cz.vutbr.fit.knot.enticing.webserver.dto

import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UserConversionTest {


    @Test
    fun `From entity to dto`() {
        val userEntity = UserEntity(2, "123", "abc", true, mutableSetOf("USER"), 21, cz.vutbr.fit.knot.enticing.webserver.entity.UserSettings(33))
        val user = userEntity.toUser()
        assertNoInformationLost(user, userEntity)
    }

    @Test
    fun `From dto to entity`() {
        val user = User(1, "111", false, setOf("FOO"), 10, UserSettings(21))
        val entity = user.toEntity()
        assertNoInformationLost(user, entity)
    }

    private fun assertNoInformationLost(user: User, userEntity: UserEntity) {
        assertThat(user.login).isEqualTo(userEntity.login)
        assertThat(user.id).isEqualTo(userEntity.id)
        assertThat(user.active).isEqualTo(userEntity.active)
        assertThat(user.roles).isEqualTo(userEntity.roles)
        assertThat(user.selectedSettings).isEqualTo(userEntity.selectedSettings)
        assertThat(user.userSettings.resultsPerPage).isEqualTo(userEntity.userSettings.resultsPerPage)
    }
}