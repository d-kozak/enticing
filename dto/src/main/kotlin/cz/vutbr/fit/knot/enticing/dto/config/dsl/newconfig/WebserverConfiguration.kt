package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

/**
 * Configuration of the webserver
 */
data class WebserverConfiguration(
        override var address: String? = null
) : ComponentConfiguration