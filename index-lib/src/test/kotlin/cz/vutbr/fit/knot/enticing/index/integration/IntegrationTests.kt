package cz.vutbr.fit.knot.enticing.index.integration

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.result.toRawText
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
import cz.vutbr.fit.knot.enticing.index.startIndexing
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.math.max
import kotlin.math.min

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
            startIndexing(builderConfig, SimpleStdoutLoggerFactory)
        }

    }

    private val collectionManager = initMg4jCollectionManager(collectionManagerConfiguration, SimpleStdoutLoggerFactory)


    @Test
    fun `the same number of results is returned for various snippet size of all results are retrieved`() {
        for (query in listOf("water", "is has")) {
            val resultsCount = mutableSetOf<Int>()
            for (snippetCount in listOf(75, 100, 200, 300, 500, 600, 700, 800, 1000, 10_000)) {
                var offset: Offset? = Offset(0, 0)
                var cnt = 0
                while (offset != null) {
                    val result = collectionManager.query(SearchQuery(query, snippetCount), offset)
                    cnt += result.searchResults.size
                    offset = result.offset
                }
                resultsCount.add(cnt)
            }
            assertThat(resultsCount)
                    .hasSize(1)
        }
    }

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

    @Test
    @DisplayName("is a ctx:sent")
    fun `check sentence context`() {
        val result = collectionManager.query(templateQuery.copy(query = "is a ctx:sent"))
        result.checkContent {
            highlights("is", "a", sentenceLimit = true)
        }
    }

    @Nested
    inner class DocumentConstraints {
        @Test
        fun `by title`() {
            for (title in setOf("x: New Fairy Tales Issue 6", "Health News - ABC News Radio", "Accommodation  Upper Kangaroo Valley - Accommodation Gold Coast")) {
                val result = collectionManager.query(templateQuery.copy(query = "water document.title:'$title'"))
                result.checkContent {
                    highlights("water")
                    this.title = title
                }
            }
        }

        @Test
        fun `by url`() {
            for (url in setOf("http://annamckerrow.blogspot.com/2010/12/new-fairy-tales-issue-6.html", "http://abcnewsradioonline.com/health-news/tag/bvo", "http://accommodationgoldcoast.net/accommodation/upper-kangaroo-valley")) {
                val result = collectionManager.query(templateQuery.copy(query = "water document.url:'$url'"))
                result.checkContent {
                    highlights("water")
                    this.url = url
                }
            }
        }

        @Test
        fun `by uuid`() {
            val docUuids = setOf("1e07edd3-5042-5605-9d68-5b5eeafe1e40",
                    "328a2ed3-ef05-5b06-8efc-5b8940ed30da",
                    "328a2ed3-ef05-5b06-8efc-5b8940ed30da",
                    "328a2ed3-ef05-5b06-8efc-5b8940ed30da",
                    "c29969d4-2014-5c01-8448-06b1b054e25c",
                    "78ae9e01-0ff7-548e-a3c8-051c6caff76b",
                    "d1c42edb-ce79-5cdc-827d-0b392ccf8232",
                    "2e581b65-1e18-54e2-a31b-9bb0964df978",
                    "2e581b65-1e18-54e2-a31b-9bb0964df978",
                    "2e581b65-1e18-54e2-a31b-9bb0964df978",
                    "2e581b65-1e18-54e2-a31b-9bb0964df978",
                    "d2850166-8864-5fe6-8cee-c010685effad",
                    "d2850166-8864-5fe6-8cee-c010685effad")

            for (uuid in docUuids) {
                val result = collectionManager.query(templateQuery.copy(query = "water document.uuid:'$uuid'"))
                result.checkContent {
                    highlights("water")
                    this.uuid = uuid
                }
            }
        }
    }

    @Nested
    inner class RealExamples {

        @Test
        @DisplayName("(influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist)) ctx:sent doc.url:'http://en.wikipedia.org/wiki/1947–48_Civil_War_in_Mandatory_Palestine' && influencer.url != influencee.url")
        fun first() {
            val result = collectionManager.query(templateQuery.copy(query = "(influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist)) ctx:sent doc.url:'http://en.wikipedia.org/wiki/1947–48_Civil_War_in_Mandatory_Palestine' && influencer.url != influencee.url"))
            assertThat(result.searchResults).isNotEmpty()
            println(result)
        }
    }
}


data class Requirements(
        val highlights: MutableList<String> = mutableListOf(),
        var title: String? = null,
        var url: String? = null,
        var uuid: String? = null
) {

    var sentenceLimit: Boolean = true

    fun highlights(vararg words: String, sentenceLimit: Boolean = false) {
        this.highlights.addAll(words)
        this.sentenceLimit = sentenceLimit
    }

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

    private val sentenceLimit = requirements.sentenceLimit

    private val paragraphMarks = mutableSetOf<Int>()
    private val sentenceMarks = mutableSetOf<Int>()

    private var minHighlightIndex = Int.MAX_VALUE
    private var maxHighlightIndex = Int.MIN_VALUE

    init {
        if (requirements.title != null && requirements.title != result.documentTitle)
            reportError("Only documents with title '${requirements.title}' expected, this one had '${result.documentTitle}'")
        if (requirements.url != null && requirements.url != result.url)
            reportError("Only documents with url '${requirements.url}' expected, this one had '${result.url}'")

        if (requirements.uuid != null && requirements.uuid != result.uuid)
            reportError("Only documents with uuid ${requirements.uuid} expected, this one had '${result.uuid}'")
    }

    fun reportError(msg: String) {
        requirements.reportError("${result.documentTitle}: $msg")
        hasErrors = true
    }

    fun checkWord(i: Int, word: TextUnit.Word) {
        when (word.token) {
            IndexedDocument.PARAGRAPH_MARK -> paragraphMarks.add(i)
            IndexedDocument.SENTENCE_MARK -> sentenceMarks.add(i)
        }
    }

    fun checkQueryMatch(i: Int, match: TextUnit.QueryMatch) {
        val queue = ArrayDeque(match.content)

        minHighlightIndex = min(minHighlightIndex, i)
        maxHighlightIndex = max(maxHighlightIndex, i)

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

    fun checkEntity(i: Int, unit: TextUnit.Entity) {

    }

    fun validate() {
        highlights.forEach { reportError("Word $it should be highlighted, but was not") }

        if (sentenceLimit) {
            if (minHighlightIndex == Int.MAX_VALUE) reportError("no match encountered")
            if (maxHighlightIndex == Int.MIN_VALUE) reportError("no match encountered")

            if (!hasErrors) {
                val intersection = sentenceMarks.filter { it in minHighlightIndex..maxHighlightIndex }
                if (intersection.isNotEmpty())
                    reportError("Sentence marks were found on $intersection even though context was restricted to only one sentence")
            }
        }

        if (hasErrors) {
            val content = result.textUnitList.toRawText(tokenIndex)
            reportError("\n\nText of the document is:\n$content\n")
        }
    }
}

val IndexServer.SearchResult.textUnitList: List<TextUnit>
    get() = ((this.payload as ResultFormat.Snippet) as ResultFormat.Snippet.TextUnitList).content.content


fun withRequirements(init: Requirements.() -> Unit, block: (Requirements) -> Unit) {
    val requirements = Requirements().apply(init)
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
                for ((i, unit) in deque.withIndex()) {
                    when (unit) {
                        is TextUnit.Word -> checker.checkWord(i, unit)
                        is TextUnit.Entity -> {
                            unit.words.reversed().forEach { deque.addFirst(it) }
                            checker.checkEntity(i, unit)
                        }
                        is TextUnit.QueryMatch -> {
                            unit.content.reversed().forEach { deque.addFirst(it) }
                            checker.checkQueryMatch(i, unit)
                        }
                    }
                }
            }
        }

    }
}
