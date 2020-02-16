package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor

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
        var repository: String = ""
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