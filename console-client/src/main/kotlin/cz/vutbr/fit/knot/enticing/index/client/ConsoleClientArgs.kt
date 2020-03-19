package cz.vutbr.fit.knot.enticing.index.client

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import java.io.FileWriter

fun parseCliArgs(args: Array<String>): ConsoleClientArgs = ArgParser(args)
        .parseInto(::ConsoleClientArgs)
        .validateOrFail()

class ConsoleClientArgs(parser: ArgParser) {

    /**
     * Path to the configuration scripts
     */
    val scriptPath by parser.positional("Enticing configuration script")

    val queryFile by parser.storing("-f", "--query-file", help = "File with input queries").default("")

    /**
     * Where the results should be stored
     */
    val resultFile by parser.storing("-r", "--result-file", help = "Where the results should be stored")
            .default("console-client.out")


    /**
     * Where the performance logs should be stored
     */
    val perfFile by parser.storing("-p", "--perf-file", help = "Where the performance logs should be stored")
            .default("console-client.perf.csv")


    val useWebserver by parser.flagging("-w", "--webserver", help = "Send queries to the webserver")

    val searchSettingsId by parser.storing("--id", help = "id of the search settings to use, if using webserver") { toInt() }.default(2)

    val query by parser.storing("-q", "--query", help = "Query to execute").default("")

    val shell by parser.flagging("-s", "--shell", help = "Run interactive shell")

    val appendFiles by parser.flagging("-a", "--append", help = "Append to the output files instead of rewriting them")

    /**
     * Corpus to work with
     *
     * default is first
     */
    private val corpusName by parser.storing("-c", "--corpus", help = "Corpus name").default("")

    lateinit var configuration: EnticingConfiguration

    lateinit var corpusConfiguration: CorpusConfiguration

    fun validateOrFail(): ConsoleClientArgs {
        configuration = executeScript<EnticingConfiguration>(scriptPath).validateOrFail()

        if (corpusName.isNotEmpty()) {
            corpusConfiguration = configuration.corpuses[corpusName]
                    ?: throw IllegalArgumentException("Corpus with name '$corpusName' not found in ${configuration.corpuses}")
        } else {
            corpusConfiguration = configuration.corpuses.values.first()
        }

        if (!appendFiles) {
            // clear the files
            FileWriter(resultFile).close()
            FileWriter(perfFile).close()
        }


        return this
    }
}