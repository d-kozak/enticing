package cz.vutbr.fit.knot.enticing.dto

interface RequestData {
    val address: String
    val offset: Map<String,Offset>
}