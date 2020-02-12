package cz.vutbr.fit.knot.enticing.api

interface EnticingApi {
    fun httpPost(endPoint: String, dto: Any)
}