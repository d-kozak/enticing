package cz.vutbr.fit.knot.enticing.webserver.entity

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import cz.vutbr.fit.knot.enticing.webserver.entity.ipaddress.ipRegex
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RegexTest {

    @Test
    fun urlRegexText() {
        val pattern = urlRegexStr.toPattern()
        val matches = { input: String -> pattern.matcher(input).find() }
        assertThat(matches("google.com")).isTrue()
        assertThat(matches("www.google.com")).isTrue()
        assertThat(matches("http://www.google.com")).isTrue()
        assertThat(matches("https://www.google.com")).isTrue()
        assertThat(matches("https://www.google.com:77")).isTrue()
        assertThat(matches("https://www.google.com:77/foo/var")).isTrue()
        assertThat(matches("web.foogle.com")).isTrue()
        assertThat(matches("35.25.20.10")).isTrue()
        assertThat(matches("35.25.20.10:20")).isTrue()

        assertThat(matches("asadkfjdsa")).isFalse()
        assertThat(matches("google.")).isFalse()

    }

    @Test
    fun ipRegexText() {
        val pattern = ipRegex.toPattern()
        val matches = { input: String -> pattern.matcher(input).find() }

        assertThat(matches("35.25.20.10")).isTrue()
        assertThat(matches("35.25.20.10")).isTrue()
        assertThat(matches("35.25.20.10:20")).isTrue()
        assertThat(matches("localhost")).isTrue()
        assertThat(matches("localhost:12000")).isTrue()

        assertThat(matches("asadkfjdsa")).isFalse()
        assertThat(matches("10")).isFalse()
        assertThat(matches("10.")).isFalse()

    }
}