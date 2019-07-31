package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegexStr
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

interface RequestData<OffsetType> {
    val address: String
    val offset: OffsetType?
}

data class IndexServerRequestData(
        @field:NotEmpty
        @field:Pattern(regexp = urlRegexStr)
        override val address:String,
        @field:Valid
        override val offset:Map<CollectionName, Offset>? = null
) : RequestData<Map<CollectionName, Offset>>


data class CollectionRequestData(
        @field:NotEmpty
        override val address:String,
        @field:Valid
        override val offset:Offset
): RequestData<Offset>