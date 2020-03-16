package cz.vutbr.fit.knot.enticing.index.integration

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.dto.TextMetadata
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

class IntegrationTests {

    private val templateQuery = SearchQuery(
            "",
            20,
            null,
            TextMetadata.Predefined("all"),
            cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET,
            TextFormat.TEXT_UNIT_LIST
    )

    companion object {

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            //startIndexing(builderConfig, SimpleStdoutLoggerFactory)
        }

    }

    private val collectionManager = initMg4jCollectionManager(collectionManagerConfiguration, SimpleStdoutLoggerFactory)


    @Test
    @DisplayName("water")
    fun one() {
        val result = collectionManager.query(templateQuery.copy(query = "water"))
        result.checkContent {
            highlights("water")
        }
    }

    @Test
    @DisplayName("ground")
    fun two() {
        val result = collectionManager.query(templateQuery.copy(query = "ground"))
        result.checkContent {
            highlights("ground")
        }
    }

}


data class Requirements(
        val highlights: MutableList<String> = mutableListOf()
) {

    fun highlights(vararg words: String) = this.highlights.addAll(words)

    private val errors = mutableListOf<String>()

    fun reportError(msg: String) = errors.add(msg)

    fun checkerFor(result: IndexServer.SearchResult, block: (DocumentChecker) -> Unit) {
        val checker = DocumentChecker(result, this)
        try {
            block(checker)
        } finally {
            checker.validate()
        }
    }

    fun validate() {
        if (errors.isNotEmpty()) {
            errors.forEach { System.err.println(it) }
            throw java.lang.AssertionError(errors)
        }
    }
}

class DocumentChecker(val result: IndexServer.SearchResult, val requirements: Requirements) {

    private var hasErrors = false

    private val highlights = requirements.highlights.toMutableSet()

    fun reportError(msg: String) {
        requirements.reportError("${result.documentTitle}: $msg")
        hasErrors = true
    }

    fun checkWord(word: TextUnit.Word) {

    }

    fun checkQueryMatch(match: TextUnit.QueryMatch) {
        val queue = ArrayDeque(match.content)

        for (unit in queue) {
            when (unit) {
                is TextUnit.Word -> {
                    if (!highlights.remove(unit.token)) reportError("Word '${unit.token}' was highlighted, but it shouldn't")
                }
                is TextUnit.Entity -> unit.words.reversed().forEach { queue.addFirst(it) }
                else -> error("Other types than word and entity should not be encountered here")
            }
        }
    }

    fun checkEntity(unit: TextUnit.Entity) {

    }

    fun validate() {
        highlights.forEach { reportError("Word $it should be highlighted, but was not") }
        if (hasErrors) {
            val content = result.textUnitList.toRawText()
            reportError("\n\nText of the document is:\n$content\n")
        }
    }
}

val IndexServer.SearchResult.textUnitList: List<TextUnit>
    get() = ((this.payload as ResultFormat.Snippet) as ResultFormat.Snippet.TextUnitList).content.content

fun List<TextUnit>.toRawText(): String = buildString {
    for ((i, unit) in this@toRawText.withIndex()) {
        when (unit) {
            is TextUnit.Word -> append(unit.token)
            is TextUnit.Entity -> append(unit.words.toRawText())
            is TextUnit.QueryMatch -> append("<b>${unit.content.toRawText()}</b>")
        }
        if (i != this@toRawText.size - 1) append(' ')
    }
}


fun withRequirements(init: Requirements.() -> Unit, block: (Requirements) -> Unit) {
    val requirements = Requirements().apply(block)
    try {
        block(requirements)
    } finally {
        requirements.validate()
    }
}

private fun IndexServer.CollectionResultList.checkContent(init: Requirements.() -> Unit) {
    check(this.searchResults.isNotEmpty()) { "cannot iterate over empty results" }
    withRequirements(init) { requirements ->
        for (result in this.searchResults) {
            requirements.checkerFor(result) { checker ->
                val deque = ArrayDeque(result.textUnitList)
                for (unit in deque) {
                    when (unit) {
                        is TextUnit.Word -> checker.checkWord(unit)
                        is TextUnit.Entity -> {
                            unit.words.reversed().forEach { deque.addFirst(it) }
                            checker.checkEntity(unit)
                        }
                        is TextUnit.QueryMatch -> {
                            unit.content.reversed().forEach { deque.addFirst(it) }
                            checker.checkQueryMatch(unit)
                        }
                    }
                }
            }
        }

    }
}
