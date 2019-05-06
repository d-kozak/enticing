package cz.vutbr.fit.knot.enticing.webserver.entity

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EnticingEntityJacksonTest {

    private val mapper = ObjectMapper()

    @Test
    fun serialization() {
        val serialized = mapper.writeValueAsString(EnticingUser("Pepa"))
        assertThat(serialized)
                .contains("id", "login", "isActive", "roles", "selectedSettings")
                .doesNotContain("encryptedPassword")
    }
}