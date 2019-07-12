package cz.vutbr.fit.knot.enticing.dto.utils

import cz.vutbr.fit.knot.enticing.dto.query.ResponseFormat
import cz.vutbr.fit.knot.enticing.dto.query.ResponseType
import cz.vutbr.fit.knot.enticing.dto.query.TextMetadata

/**
 * Default values for dto attributes.
 *
 * Since some of them are shared between multiple dtos, they were refactored here to allow for easier change of their values
 */
object Defaults {
    val defaultIndex = "token"
    val responseFormat = ResponseFormat.ANNOTATED_TEXT
    val responseType: ResponseType = ResponseType.FULL
    val metadata = TextMetadata.Predefined("all")
}