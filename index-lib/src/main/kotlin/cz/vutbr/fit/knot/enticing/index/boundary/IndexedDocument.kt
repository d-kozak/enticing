package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.annotation.Speed
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

/**
 * Each word consists of multiple indexes
 */
typealias Word = List<String>

/**
 * Retrieved document from the indexed data
 */
interface IndexedDocument : Iterable<Word> {

    @Cleanup("this is a redefinition, should be merged")
    companion object {
        const val PARAGRAPH_MARK = "§"
        const val SENTENCE_MARK = "¶"
    }

    val id: DocumentId
    val uuid: String
    val title: String
    val uri: String
    val size: Int

    @Speed("If necessary, char[] or MutableStrings or some else more low level abstraction can be used here")
    val content: List<Word>

    override fun iterator() = object : Iterator<Word> {
        var i = 0

        override fun hasNext(): Boolean = i < content[0].size

        override fun next(): Word {
            val current = i++
            return content.map { it[current] }
        }
    }

    val interval: Interval
        get() = Interval.valueOf(0, size - 1)
}


/**
 * Reads the document word after word opposed to the interval storing ( which is index by index)
 */
class WordReader(input: List<String>, private val separator: Char) {
    private val tokenReaders = input.map { TokenReader(it, separator) }

    fun nextWord(): List<String>? {
        val tokens = tokenReaders.map { it.nextToken() }
        var foundNull = false
        var foundNotNull = false
        for (token in tokens) {
            if (token == null) foundNull = true
            if (token != null) foundNotNull = true
        }
        if (foundNull && foundNotNull) throw IllegalStateException("Some token readers finished before others, all should finish at once")
        return if (foundNull) null else tokens as List<String>

    }

}

/**
 * Reads a single index token by token separated by separator char
 */
class TokenReader(private val input: String, private val separator: Char) : Iterable<String> {
    var pos = 0

    fun nextToken(): String? {
        if (pos >= input.length) return null
        val start = pos
        while (pos < input.length && input[pos] != separator) pos++
        return input.substring(start, pos++) // another increment to skip the space
    }

    override fun iterator(): Iterator<String> = object : Iterator<String> {

        private val reader = TokenReader(input, separator)

        private var next: String? = null

        override fun hasNext(): Boolean {
            next = reader.nextToken()
            return next != null
        }

        override fun next(): String {
            requireNotNull(next)
            return next!!
        }
    }
}