package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig


abstract class AbstractComponentConfiguration {
    /**
     * Server on which this component should be started
     * null means that it should be started on this machine
     */
    var address: String? = null

}