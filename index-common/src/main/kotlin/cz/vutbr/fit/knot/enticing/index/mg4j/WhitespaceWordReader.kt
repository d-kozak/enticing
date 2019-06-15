package cz.vutbr.fit.knot.enticing.index.mg4j

import it.unimi.dsi.io.FastBufferedReader

class WhitespaceWordReader : FastBufferedReader() {

    override fun isWordConstituent(c: Char): Boolean {
        return !Character.isWhitespace(c)
    }
}