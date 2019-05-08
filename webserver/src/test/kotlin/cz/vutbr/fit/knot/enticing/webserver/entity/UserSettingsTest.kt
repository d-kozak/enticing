package cz.vutbr.fit.knot.enticing.webserver.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserSettingsTest {

    @Test
    fun `toString contains results per page`() {
        val userSettings = UserSettings(20)
        assertThat(userSettings.toString())
                .contains("resultsPerPage", "20")
    }

    @Test
    fun `equals throws exception`() {
        val userSettings = UserSettings(10)
        assertThrows<UnsupportedOperationException> {
            userSettings.equals(42)
        }
    }

    @Test
    fun `hashcode throws exception`() {
        val userSettings = UserSettings(10)
        assertThrows<UnsupportedOperationException> {
            userSettings.hashCode()
        }
    }

}