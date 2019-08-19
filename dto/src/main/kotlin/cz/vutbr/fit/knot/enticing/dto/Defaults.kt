package cz.vutbr.fit.knot.enticing.dto

/**
 * Default values for dto attributes.
 *
 * Since some of them are shared between multiple dtos, they were refactored here to allow for easier change of their values
 */
object Defaults {
    val snippetCount = 20
    val defaultIndex = "token"
    val textFormat = TextFormat.TEXT_UNIT_LIST
    val resultFormat = ResultFormat.SNIPPET
    val metadata = TextMetadata.Predefined("all")
}