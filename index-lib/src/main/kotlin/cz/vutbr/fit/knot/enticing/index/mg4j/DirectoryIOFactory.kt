package cz.vutbr.fit.knot.enticing.index.mg4j

import it.unimi.di.big.mg4j.io.IOFactory
import java.io.*
import java.nio.channels.ReadableByteChannel
import java.nio.channels.WritableByteChannel
import java.nio.file.Path

/**
 * Delegates all io operations into a specific path
 */
class DirectoryIOFactory(private val path: Path) : IOFactory {

    @Throws(IOException::class)
    override fun getOutputStream(name: String): OutputStream = FileOutputStream(path.resolve(name).toFile())

    @Throws(IOException::class)
    override fun getInputStream(name: String): InputStream = FileInputStream(path.resolve(name).toFile())

    override fun delete(name: String): Boolean = path.resolve(name).toFile().delete()

    override fun exists(name: String): Boolean = path.resolve(name).toFile().exists()

    @Throws(IOException::class)
    override fun createNewFile(name: String) {
        path.resolve(name).toFile().createNewFile()
    }

    @Throws(IOException::class)
    override fun getWritableByteChannel(name: String): WritableByteChannel =
            FileOutputStream(path.resolve(name).toFile()).channel

    @Throws(IOException::class)
    override fun getReadableByteChannel(name: String): ReadableByteChannel =
            FileInputStream(path.resolve(name).toFile()).channel

    @Throws(IOException::class)
    override fun length(name: String): Long = path.resolve(name).toFile().length()
}