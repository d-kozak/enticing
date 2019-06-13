package cz.vutbr.fit.knot.enticing.indexer.mg4j

import it.unimi.di.big.mg4j.document.Document
import it.unimi.di.big.mg4j.document.DocumentCollection
import it.unimi.di.big.mg4j.document.DocumentFactory
import it.unimi.di.big.mg4j.document.DocumentIterator
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap
import java.io.File
import java.io.InputStream

class Mg4jSingleFileDocumentCollection(private val inputFile: File) : DocumentCollection {

    override fun metadata(index: Long): Reference2ObjectMap<Enum<*>, Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stream(index: Long): InputStream {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun iterator(): DocumentIterator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun document(index: Long): Document {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun size(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun factory(): DocumentFactory {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun filename(filename: CharSequence?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun copy(): DocumentCollection {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}