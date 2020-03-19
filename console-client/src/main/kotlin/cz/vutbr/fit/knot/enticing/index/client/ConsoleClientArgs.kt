package cz.vutbr.fit.knot.enticing.index.client

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.util.Validator
import cz.vutbr.fit.knot.enticing.dto.config.dsl.util.ValidatorImpl
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript

fun parseCliArgs(args: Array<String>): ConsoleClientArgs = ArgParser(args)
        .parseInto(::ConsoleClientArgs)
        .validateOrFail()

class ConsoleClientArgs(parser: ArgParser) : Validator by ValidatorImpl() {

    /**
     * Path to the configuration scripts
     */
    val scriptPath by parser.positional("Enticing configuration script")


    /**
     * Where the results should be stored
     */
    val resultFile by parser.storing("-f", "--result-file", help = "Where the results should be stored")
            .default("console-client.out")


    /**
     * Where the performance logs should be stored
     */
    val perfFile by parser.storing("-p", "--perf-file", help = "Where the performance logs should be stored")
            .default("console-client.perf")


    val useWebserver by parser.flagging("-w", "--webserver", help = "Send queries to the webserver")

    val searchSettingsId by parser.storing("--id", help = "id of the search settings to use, if using webserver") { toInt() }.default(2)

    val query by parser.storing("-q", "--query", help = "Query to execute").default("")

    val shell by parser.flagging("-s", "--shell", help = "Run interactive shell")

    /**
     * List of corpuses to work with
     *
     * default is all
     */
    val corpuses by parser.adding("-c", "--corpus", help = "Corpus name")

    lateinit var configuration: EnticingConfiguration

    fun validateOrFail(): ConsoleClientArgs {
        configuration = executeScript<EnticingConfiguration>(scriptPath).validateOrFail()

        if (corpuses.isEmpty()) {
            for (corpus in configuration.corpuses.values) corpuses.add(corpus.name)
        }

        return this
    }
}