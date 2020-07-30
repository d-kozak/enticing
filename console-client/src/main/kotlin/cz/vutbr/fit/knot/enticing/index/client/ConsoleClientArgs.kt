package cz.vutbr.fit.knot.enticing.index.client

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import cz.vutbr.fit.knot.enticing.dto.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.TextFormat

/**
 * Parse the cli args and create ConsoleClientArgs object from them
 */
fun parseCliArgs(args: Array<String>): ConsoleClientArgs = ArgParser(args)
        .parseInto(::ConsoleClientArgs)
        .validateOrFail()

/**
 * Configuration of the console client
 */
class ConsoleClientArgs(parser: ArgParser) {

    val query by parser.storing("-q", "--query", help = "Query to execute")
            .default<String?>(null)

    val queryFile by parser.storing("-f", "--file", help = "File with input queries")
            .default<String?>(null)

    /**
     * Where the results should be stored
     */
    val resultFile by parser.storing("-o", "--output", help = "Where the results should be stored, defaults to stdout")
            .default<String?>(null)

    val benchmark by parser.flagging("-b", "--benchmark", help = "Run as benchmark")

    val indexServer by parser.storing("-i", "--index-server", help = "Index server to query")
            .default<String?>(null)

    val webserver by parser.storing("-w", "--webserver", help = "Webserver to query")
            .default<String?>(null)

    val searchSettingsId by parser.storing("--id", help = "id of the search settings to use, if using webserver") { toInt() }.default(2)

    val queryDispatcher by parser.storing("-d", "--query-dispatcher", help = "Dispatch queries using local query dispatcher") { split(",") }
            .default<List<String>?>(null)

    val resultFormat by parser.storing("--result-format", help = "Wanted result format") {
        when (this) {
            "snippet" -> ResultFormat.SNIPPET
            "idlist" -> ResultFormat.IDENTIFIER_LIST
            else -> throw IllegalArgumentException("Unknown result format '$this'")
        }
    }.default(ResultFormat.SNIPPET)

    val textFormat by parser.storing("--text-format", help = "Wanted text format") {
        when (this) {
            "plain" -> TextFormat.PLAIN_TEXT
            "html" -> TextFormat.HTML
            else -> throw IllegalArgumentException("Unknown text format '$this'")
        }
    }.default(TextFormat.PLAIN_TEXT)

    val snippetCount by parser.storing("-c", "--snippet-count", help = "How many results are wanted") { toInt() }
            .default(20)
            .addValidator {
                if (this.value > 10000) throw IllegalArgumentException("At most 10000 results allowed, or specify 'all' for everything")
            }

    val allResults by parser.flagging("-a", "--all", help = "Get all results for a given query")

    fun validateOrFail(): ConsoleClientArgs {
        val enabledOptions = listOf(indexServer, webserver, queryDispatcher).count { it != null }
        require(enabledOptions == 1) { "Exactly one of webserver, index server or query dispatcher has to be specified" }
        require((query != null) xor (queryFile != null)) { "One of query or query file options has to be specified" }
        return this
    }
}