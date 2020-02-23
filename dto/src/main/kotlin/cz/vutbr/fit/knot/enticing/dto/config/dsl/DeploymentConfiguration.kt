package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

/**
 * Configuration of the deployment pipeline
 */
data class DeploymentConfiguration(
        /**
         * server at which the deployment should happen
         */
        var server: String = "",
        /**
         * path the the repository
         */
        var repository: String = "",
        /**
         * location of the configuration script
         * (has to be accessible from all components (shared on nfs)
         */
        var configurationScript: String = ""
) : EnticingConfigurationUnit {

    val indexBuilderScript: String
        get() = "$repository/bin/index-builder"

    val indexServerScript: String
        get() = "$repository/bin/index-server"

    val webserverScript: String
        get() = "$repository/bin/webserver"

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitDeploymentConfiguration(this)
    }
}