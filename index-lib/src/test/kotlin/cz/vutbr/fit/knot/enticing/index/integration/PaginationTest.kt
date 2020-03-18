package cz.vutbr.fit.knot.enticing.index.integration

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CollectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.mg4jFiles
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RootNode
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.matchDocument
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
import cz.vutbr.fit.knot.enticing.index.startIndexing
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

/**
 * Verifies the pagination functionality
 *
 * In the data/pagination/pagination.mg4j file there should be 1000 copies of identical document containing 2 mythologies (of len 2), 3 dates and 1 location
 */
class PaginationTest {


    companion object {

        private val builderConfig = IndexBuilderConfig(
                "CC",
                "col1",
                File("../data/pagination/mg4j"),
                File("../data/pagination/indexed"),
                metadata
        )


        private val collectionManagerConfiguration = CollectionManagerConfiguration(
                "CC",
                "col1",
                File("../data/pagination/mg4j"),
                File("../data/pagination/indexed"),
                metadata
        )

        private val templateQuery = SearchQuery(
                "nertag:(date mythology)",
                20,
                null,
                TextMetadata.Predefined("all"),
                ResultFormat.SNIPPET,
                TextFormat.TEXT_UNIT_LIST
        )

        private val paginationDocumentCollection = Mg4jCompositeDocumentCollection(metadata, collectionManagerConfiguration.mg4jDir.mg4jFiles, SimpleStdoutLoggerFactory)

        private val eqlCompiler = EqlCompiler(SimpleStdoutLoggerFactory)

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            startIndexing(builderConfig, SimpleStdoutLoggerFactory)
        }

    }


    private val collectionManager = initMg4jCollectionManager(collectionManagerConfiguration, SimpleStdoutLoggerFactory)

    @Test
    fun `there are 1000 identic documents with wanted content in the pagination mg4j file`() {
        assertThat(paginationDocumentCollection.size()).isEqualTo(1000)

        val nertagIndex = metadata.indexOf("nertag")
        val nerlenIndex = metadata.indexOf("nerlength")

        for (i in 0 until paginationDocumentCollection.size()) {
            val document = paginationDocumentCollection.document(i)
            assertThat(document.title).isEqualTo("1947–48 Civil War in Mandatory Palestine")
            assertThat(document.size()).isEqualTo(26)
            val nertag = document.content[nertagIndex]
            val nerlen = document.content[nerlenIndex]

            val mythology = nertag.filterIndexed { i, word -> word == "mythology" && nerlen[i] != "-1" }
            val date = nertag.filterIndexed { i, word -> word == "date" && nerlen[i] != "-1" }
            val location = nertag.filterIndexed { i, word -> word == "location" && nerlen[i] != "-1" }
            assertThat(mythology.size).isEqualTo(2)
            assertThat(date.size).isEqualTo(3)
            assertThat(location.size).isEqualTo(1)
        }
    }


    @Test
    fun `mythology and date`() {
        // 2 mythologies * 3 dates * 1000 documents == 12000 results
        checkResult("nertag:(mythology date)", 6000)
    }

    @Test
    fun `only location`() {
        // 1 location * 1000 documents == 1000 results
        checkResult("nertag:(location)", 1000)
    }

    @Test
    fun `location and date`() {
        // 1 location * 3 dates * 1000 documents == 3000 results
        checkResult("nertag:(location date)", 3000)
    }

    @Test
    fun `location or date`() {
        // (1 location + 3 dates) * 1000 documents == 4000 results
        checkResult("nertag:(location | date)", 4000)
    }

    private fun checkResult(query: String, expectedResultCount: Int) {
        val eqlResults = getAllPossibleResults(query)
        assertThat(eqlResults.size)
                .isEqualTo(expectedResultCount)
        val allResults = mutableListOf<IndexServer.SearchResult>()
        var offset: Offset? = Offset(0, 0)
        while (offset != null) {
            val res = collectionManager.query(templateQuery.copy(query = query, snippetCount = 17), offset)
            allResults.addAll(res.searchResults)
            offset = res.offset
        }
        assertThat(allResults.size)
                .isEqualTo(expectedResultCount)
    }

    private fun getAllPossibleResults(query: String): List<DocumentMatch> {
        val ast = eqlCompiler.parseOrFail(query, metadata) as RootNode
        return (0 until paginationDocumentCollection.size())
                .map { paginationDocumentCollection.document(it) }
                .map { matchDocument(ast, it, "token", 0, metadata, Interval.valueOf(0, it.size() - 1)) }
                .flatMap { it.intervals }

    }

    @Nested
    inner class CheckingTheMatchingAlg {


        @Test
        fun `mythology and date`() {
            // 2 mythologies * 3 dates  == 6 results
            val ast = eqlCompiler.parseOrFail("nertag:(mythology date)", metadata) as RootNode
            val document = paginationDocumentCollection.document(0)
            val result = matchDocument(ast, document, "token", 0, metadata, Interval.valueOf(0, document.size() - 1))
            assertThat(result.intervals).hasSize(6)
        }

        @Test
        fun `location and date`() {
            // 1 location * 3 dates  == 3 results
            val ast = eqlCompiler.parseOrFail("nertag:(location date)", metadata) as RootNode
            val document = paginationDocumentCollection.document(0)
            val result = matchDocument(ast, document, "token", 0, metadata, Interval.valueOf(0, document.size() - 1))
            assertThat(result.intervals).hasSize(3)
        }
    }
}