package cz.vutbr.fit.knot.enticing.webserver.dto

import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UserConversionTest {


    @Test
    fun `From entity to dto`() {
        val userEntity = UserEntity(2, "123", "abc", true, mutableSetOf("USER"), cz.vutbr.fit.knot.enticing.webserver.entity.UserSettings(33), SearchSettings(11))
        val user = userEntity.toUser()
        assertNoInformationLost(user, userEntity)
    }

    @Test
    fun `From dto to entity`() {
        val user = User(1, "111", false, setOf("FOO"), 10, UserSettings(21))
        val entity = user.toEntity(SearchSettings(id = 10))
        assertNoInformationLost(user, entity)
    }

    private fun assertNoInformationLost(user: User, userEntity: UserEntity) {
        assertThat(user.login).isEqualTo(userEntity.login)
        assertThat(user.id).isEqualTo(userEntity.id)
        assertThat(user.active).isEqualTo(userEntity.active)
        assertThat(user.roles).isEqualTo(userEntity.roles)
        assertThat(user.selectedSettings).isEqualTo(userEntity.selectedSettings?.id)
        assertThat(user.userSettings.resultsPerPage).isEqualTo(userEntity.userSettings.resultsPerPage)
    }

    @Test
    fun `Is admin test`() {
        val user = User(1, "111", roles = setOf())
        assertThat(user.isAdmin).isFalse()
        val admin = User(1, "111", roles = setOf("ADMIN"))
        assertThat(admin.isAdmin).isTrue()
    }
}