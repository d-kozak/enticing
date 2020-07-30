package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

/**
 * Base interface for specifying offsets into IndexServers and Collections when querying
 */
interface RequestData<OffsetType> {
    val address: String
    val offset: OffsetType?
}

/**
 * Offset specification for one IndexServer
 */
data class IndexServerRequestData(
        @field:NotEmpty
        @field:Pattern(regexp = urlRegexStr)
        override val address:String,
        @field:Valid
        override val offset:Map<CollectionName, Offset>? = null
) : RequestData<Map<CollectionName, Offset>>


/**
 * Offset specification for a single collection
 */
data class CollectionRequestData(
        @field:NotEmpty
        override val address:String,
        @field:Valid
        override val offset:Offset
): RequestData<Offset>