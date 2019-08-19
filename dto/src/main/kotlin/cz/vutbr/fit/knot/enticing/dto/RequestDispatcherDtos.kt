package cz.vutbr.fit.knot.enticing.dto

interface Mg4jQuery {
    val defaultIndex: String
    val responseFormat: ResponseFormat
}

interface Query<T:Query<T>>{
    val snippetCount:Int
    fun updateSnippetCount(newSnippetCount:Int):T
}

interface QueryResult<OffsetType>{
    val searchResults: List<IndexServer.SearchResult>
    val offset: OffsetType?

    fun createRequest(address:String):RequestData<OffsetType>
}