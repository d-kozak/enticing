package cz.vutbr.fit.knot.enticing.dto.config

import java.io.File
import javax.script.ScriptEngineManager

/**
 * Executes kotlin script on the defined path and returns it's result cast to a specific type
 */
inline fun <reified Result> executeScript(path: String): Result {
    val script = File(path).readText()
    val engine = ScriptEngineManager().getEngineByExtension("kts")
            ?: throw IllegalStateException("Could not locate kts engine")
    return engine.eval(script) as Result
}