package cz.vutbr.fit.knot.enticing.webserver.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class SearchSettingsTest {


    @Test
    fun `Equals test`() {
        val settingsOne = SearchSettings(1, "foo")
        val sameName = SearchSettings(2, "foo")
        val differentName = SearchSettings(1, "bar")
        assertThat(settingsOne).isEqualTo(sameName)
        assertThat(settingsOne).isNotEqualTo(differentName)
    }

    @Test
    fun `Hashcode test`() {
        val settingsOne = SearchSettings(1, "foo")
        val sameName = SearchSettings(2, "foo")
        val differentName = SearchSettings(1, "bar")
        assertThat(settingsOne.hashCode()).isEqualTo(sameName.hashCode())
        assertThat(settingsOne.hashCode()).isNotEqualTo(differentName.hashCode())
    }


    @Test
    fun `toString test`() {
        val settings = SearchSettings(1, "nameLong")
        assertThat(settings.toString())
                .contains("id", "name", "default", "1", "nameLong")
    }
}