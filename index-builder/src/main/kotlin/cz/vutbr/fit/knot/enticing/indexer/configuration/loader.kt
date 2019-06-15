package cz.vutbr.fit.knot.enticing.indexer.configuration

import cz.vutbr.fit.knot.enticing.index.config.dsl.IndexerConfig
import de.swirtz.ktsrunner.objectloader.KtsObjectLoader
import java.io.File

fun loadConfiguration(path: String): IndexerConfig {
    val script = File(path).readText()
    return KtsObjectLoader().load(script)
}