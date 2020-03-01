package cz.vutbr.fit.knot.enticing.dto

interface GeneralFormatInfo {
    val defaultIndex: String
    val textFormat: TextFormat
    val metadata: TextMetadata
}

interface Query<T:Query<T>>{
    val query: String
    val snippetCount: Int
    fun updateSnippetCount(newSnippetCount:Int):T
}

interface QueryResult<OffsetType>{
    val searchResults: List<IndexServer.SearchResult>
    val offset: OffsetType?

    fun createRequest(address:String):RequestData<OffsetType>
}