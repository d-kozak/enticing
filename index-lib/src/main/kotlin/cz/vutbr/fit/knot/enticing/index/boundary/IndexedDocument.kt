package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.annotation.Speed

typealias Word = List<String>

/**
 * Retrieved document from the indexed data
 */
interface IndexedDocument : Iterable<Word> {
    val id: Int
    val uuid: String
    val title: String
    val uri: String
    val size: Int
    @Speed("If necessary, char[] or MutableStrings or some else more low level abstraction can be used here")
    val content: List<String>

    override fun iterator() = object : Iterator<Word> {

        private val wordReader = WordReader(content, ' ')

        private var next: List<String>? = null

        override fun hasNext(): Boolean {
            next = wordReader.nextWord()
            return next != null
        }

        override fun next(): Word {
            requireNotNull(next)
            return next!!
        }
    }
}


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

class TokenReader(private val input: String, private val separator: Char) {
    var pos = 0

    fun nextToken(): String? {
        if (pos >= input.length) return null
        val start = pos
        while (pos < input.length && input[pos] != separator) pos++
        return input.substring(start, pos++) // another increment to skip the space
    }
}