package cz.vutbr.fit.knot.enticing.dto

interface Mg4jQuery {
    val defaultIndex: String
    val responseFormat: ResponseFormat
}

interface Query<T:Query<T>>{
    val snippetCount:Int
    fun updateSnippetCount(newSnippetCount:Int):T
}

interface QueryResult{
    val matched: List<IndexServer.Snippet>
    val offset: Map<String, Offset>
}