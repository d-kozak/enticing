package cz.vutbr.fit.knot.enticing.dto.config

import de.swirtz.ktsrunner.objectloader.KtsObjectLoader
import java.io.File

inline fun <reified Result> executeScript(path: String): Result {
    val script = File(path).readText()
    return KtsObjectLoader().load(script)
}