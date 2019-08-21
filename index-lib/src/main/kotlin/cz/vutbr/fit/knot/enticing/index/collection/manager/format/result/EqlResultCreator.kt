package cz.vutbr.fit.knot.enticing.index.collection.manager.format.result

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.filterBy
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.ResultCreator
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next.*

@Incomplete("not finished yet")
class EqlResultCreator(private val corpusConfiguration: CorpusConfiguration) : ResultCreator {
    override fun multipleResults(query: SearchQuery, document: IndexedDocument, resultOffset: Int): Pair<List<IndexServer.SearchResult>, Boolean> {
        val filteredConfig = corpusConfiguration.filterBy(query.metadata, query.defaultIndex)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Incomplete("start using ast when EQL is ready")
    override fun singleResult(query: GeneralFormatInfo, document: IndexedDocument, astNode: AstNode?, interval: Interval?): ResultFormat.Snippet {
        val filteredConfig = corpusConfiguration.filterBy(query.metadata, query.defaultIndex)

        val listener = when (query.textFormat) {
            TextFormat.PLAIN_TEXT -> return generatePlainText(document, filteredConfig, query.defaultIndex, astNode, interval)
            TextFormat.HTML -> HtmlGeneratingListener(filteredConfig, query.defaultIndex)
            TextFormat.STRING_WITH_METADATA -> StringWithAnnotationsGeneratingListener(filteredConfig, query.defaultIndex)
            TextFormat.TEXT_UNIT_LIST -> TextUnitListGeneratingListener(filteredConfig, query.defaultIndex)
        }
        val iterator = StructuredDocumentIterator(filteredConfig)
        iterator.iterateDocument(document, emptyMap(), emptySet(), listener, interval)
        return listener.build()
    }
}