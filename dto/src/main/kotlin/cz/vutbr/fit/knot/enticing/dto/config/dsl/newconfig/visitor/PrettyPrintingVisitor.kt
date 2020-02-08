package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.AttributeConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.IndexConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration

class PrettyPrintingVisitor : EnticingConfigurationVisitor {

    /**
     * stringbuild into which the output is agregated
     */
    val stringBuilder = StringBuilder()

    /**
     * current index level
     */
    private var indent = 0

    /**
     * how much indent should be put into each nested block
     */
    private val indentDiff = 2

    override fun visitEnticingConfiguration(configuration: EnticingConfiguration) {
        withIndent("enticingConfiguration") {
            configuration.managementServiceConfiguration.accept(this)
            configuration.webserverConfiguration.accept(this)
            if (configuration.corpuses.isNotEmpty()) {
                withIndent("corpuses") {
                    for (corpus in configuration.corpuses)
                        corpus.accept(this)
                }

            }

            if (configuration.indexServers.isNotEmpty()) {
                withIndent("indexServers") {
                    for (indexServer in configuration.indexServers)
                        indexServer.accept(this)
                }
            }
        }
    }

    override fun visitCorpusConfiguration(configuration: CorpusConfiguration) {
        withIndent("corpusConfiguration") {
            appendProperty("name", configuration.name)
            appendProperty("mg4jDir", configuration.mg4jDir)
            appendProperty("indexedDir", configuration.indexedDir)
            configuration.metadataConfiguration.accept(this)
            withIndent("indexServers") {
                for (indexServer in configuration.indexServers)
                    indexServer.accept(this)
            }
        }
    }

    override fun visitIndexServerConfiguration(configuration: IndexServerConfiguration) {
        withIndent("indexServer") {
            appendProperty("address", configuration.address)
            appendProperty("mg4jDir", configuration.mg4jDir)
            appendProperty("indexedDir", configuration.indexedDir)
            configuration.metadata?.accept(this)
        }
    }

    override fun visitMetadataConfiguration(configuration: MetadataConfiguration) {
        withIndent("metadata") {
            appendProperty("entityIndex", configuration.entityIndex)
            appendProperty("lengthIndex", configuration.lengthIndex)
            if (configuration.indexes.isNotEmpty())
                withIndent("indexes") {
                    for (index in configuration.indexes.values)
                        index.accept(this)
                }
            if (configuration.entities.isNotEmpty())
                withIndent("entities") {
                    for (entity in configuration.entities.values)
                        entity.accept(this)
                }
        }
    }

    override fun visitIndexConfiguration(configuration: IndexConfiguration) {
        withIndent("index") {
            appendProperty("name", configuration.name)
            appendProperty("description", configuration.description)
            appendProperty("columnIndex", configuration.columnIndex)
        }
    }

    override fun visitEntityConfiguration(configuration: EntityConfiguration) {
        withIndent("entity") {
            appendProperty("name", configuration.name)
            appendProperty("description", configuration.description)
            if (configuration.attributes.isNotEmpty())
                withIndent("attributes") {
                    for (attribute in configuration.attributes.values)
                        attribute.accept(this)
                }
        }
    }

    override fun visitAttributeConfiguration(configuration: AttributeConfiguration) {
        withIndent("attribute") {
            appendProperty("name", configuration.name)
            appendProperty("description", configuration.description)
            appendProperty("correspondingIndex", configuration.index)
        }
    }

    override fun visitManagementConfiguration(configuration: ManagementServiceConfiguration) {
        appendLine(configuration.toString())
    }

    override fun visitWebserverConfiguration(configuration: WebserverConfiguration) {
        appendLine(configuration.toString())
    }

    /**
     * executed the block of code with increased indent
     */
    private fun withIndent(blockName: String, block: () -> Unit) {
        appendLine("$blockName {")
        indent()
        block()
        dedent()
        appendLine("}")
    }

    /**
     * prints a named property
     */
    private fun appendProperty(name: String, value: Any?) {
        putIdent()
        stringBuilder.append(name)
        stringBuilder.append(" = ")
        stringBuilder.appendln(value)
    }

    /**
     * increased indent
     */
    private fun indent() {
        indent += indentDiff
    }

    /**
     * decreased indent
     */
    private fun dedent() {
        indent -= indentDiff
    }

    /**
     * prints indented line
     */
    private fun appendLine(line: String) {
        putIdent()
        stringBuilder.appendln(line)
    }

    /**
     * adds indent to the buffer
     */
    private fun putIdent() {
        repeat(indent) {
            stringBuilder.append(' ')
        }
    }
}

/**
 * pretty prints current configuration to stdout
 */
fun EnticingConfiguration.prettyPrint() {
    val visitor = PrettyPrintingVisitor()
    this.accept(visitor)
    println(visitor.stringBuilder.toString())
}