package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import org.assertj.core.api.Assertions.assertThat
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import java.io.File

class SearchConfigTest {

    @Test
    fun parseConfig() {
        @Language("JSON") val input = """{
  "col1": ["../data/mg4j","../data/indexed"],
  "col2": ["../data/mg4j/cc1.mg4j","../data/indexed"]
}"""
        val config = IndexClientConfig()
        config.update(input)
        assertThat(config.collections).hasSize(2)
        assertThat(config.collections[0].name).isEqualTo("col1")
        assertThat(config.collections[0].mg4jFiles.toSet()).isEqualTo(setOf(File("../data/mg4j/cc1.mg4j"), File("../data/mg4j/cc2.mg4j"), File("../data/mg4j/cc3.mg4j"), File("../data/mg4j/small.mg4j")))
        assertThat(config.collections[0].indexDirectory).isEqualTo(File("../data/indexed"))

        assertThat(config.collections[1].name).isEqualTo("col2")
        assertThat(config.collections[1].mg4jFiles).isEqualTo(listOf(File("../data/mg4j/cc1.mg4j")))
        assertThat(config.collections[1].indexDirectory).isEqualTo(File("../data/indexed"))
    }

}