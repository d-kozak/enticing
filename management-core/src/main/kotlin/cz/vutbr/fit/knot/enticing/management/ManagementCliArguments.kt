package cz.vutbr.fit.knot.enticing.management

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.util.Validator
import cz.vutbr.fit.knot.enticing.dto.config.dsl.util.ValidatorImpl
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript


fun parseCliArgs(args: Array<String>): ManagementCliArguments = mainBody { ArgParser(args).parseInto(::ManagementCliArguments) }

class ManagementCliArguments(parser: ArgParser) : Validator by ValidatorImpl() {
    /**
     * Path to the configuration scripts
     */
    val scriptPath by parser.positional("configuration script")

    /**
     * List of corpuses to work with
     */
    val corpuses by parser.adding("-c", "--corpus", help = "Corpus name")

    /**
     * build the project remotely
     */
    val build by parser.flagging("-b", "--build", help = "build the project remotely")

    /**
     * Distribute mg4j files to the servers
     */
    val distribute by parser.flagging("-d", "--distrib", help = "Distribute mg4j files over servers")

    /**
     * Start the indexing process
     */
    val startIndexing by parser.flagging("-p", "--preprocess", help = "Index mg4j files using index-builder")

    /**
     * Start or kill the webserver
     */
    val webserver by parser.flagging("-w", "--webserver", help = "Start or kill webserver")

    /**
     * Start or kill the index servers
     */
    val indexServers by parser.flagging("-i", "--index", help = "Start or kill indexservers")

    /**
     * Kill selected components (implicitly all)
     */
    val kill by parser.flagging("-k", "--kill", help = "Kill selected components(implicitly all)")


    /**
     * Start selected components
     */
    val startComponents: Boolean
        get() = !kill

    /**
     * Print mg4j files on the servers
     */
    val printFiles by parser.flagging("--print", help = "Print how mg4j files are currently distributed")

    /**
     * Clean mg4j files on the servers
     */
    val removeFiles by parser.flagging("--remove", help = "Remove mg4j directories on the servers")


    lateinit var configuration: EnticingConfiguration

    fun validateOrFail(): ManagementCliArguments {
        configuration = executeScript<EnticingConfiguration>(scriptPath).validateOrFail()

        if (corpuses.isEmpty()) {
            for (corpus in configuration.corpuses.values) corpuses.add(corpus.name)
        }

        check(listOf(printFiles, removeFiles, distribute, startIndexing, webserver, indexServers, kill).any()) { "At least one option should be set" }
        if (errors.isNotEmpty())
            throw IllegalArgumentException(errors.joinToString("\n"))
        return this
    }

    override fun toString(): String = buildString {
        append("ManagementCliArguments(scriptPath='")
        append(scriptPath)
        append("',corpuses=")
        append(corpuses)
        append(',')

        if (build) append("build,")
        if (distribute) append("distribute,")
        if (startIndexing) append("startIndexing,")
        if (webserver) append("webserver,")
        if (indexServers) append("indexServers,")
        if (kill) append("kill,")
        if (printFiles) append("printFiles,")
        if (removeFiles) append("removeFiles,")

        // remove last ','
        setLength(length - 1)
        append(')')
    }

}