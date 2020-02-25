package cz.vutbr.fit.knot.enticing.testing.performance

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.query.processor.fuel.jsonBody
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

fun webserverLogin(address: String, username: String, password: String): WebserverApi {
    waitForWebserver(address)
    val api = WebserverApi(address)
    api.login(username, password)
    return api
}

class WebserverApi(
        var address: String
) {

    var settingsId: Int = -1

    var sessionId: String? = null

    val queryEndpoint = "http://$address/api/v1/query?settings=$settingsId"


    fun login(username: String, password: String) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA
        val map = LinkedMultiValueMap<String, String>()
        map.add("username", username)
        map.add("password", password)
        val req = HttpEntity(map, headers)

        val res = RestTemplate().exchange("http://$address/api/v1/login", HttpMethod.POST, req, String::class.java)
        val outHeaders = res.headers
        val jsessidCookie = outHeaders.getFirst(HttpHeaders.SET_COOKIE)!!
        sessionId = jsessidCookie.substring(jsessidCookie.indexOf('=') + 1, jsessidCookie.indexOf(';'))
    }

    fun userInfo(): String {
        val userHeaders = HttpHeaders()
        userHeaders.add("Cookie", "JSESSIONID=$sessionId")
        val user = RestTemplate().exchange("http://$address/api/v1/user", HttpMethod.GET, HttpEntity<String>(userHeaders), String::class.java)
        return user.body!!
    }

    fun sendQuery(query: String): WebServer.ResultList {
        val (_, _, result) = Fuel.post(queryEndpoint)
                .timeout(60_000)
                .jsonBody(SearchQuery(query))
                .responseString()
        return result.get()
                .toDto()
    }


}

private fun waitForWebserver(address: String) {
    for (i in 0 until 10) {
        println("waiting for the webserver...$i")
        try {
            val (_, _, res) = "http://$address".httpGet().responseString()
            println(res.get())
            println("webserver started")
            return
        } catch (ex: Exception) {
            println(ex.message)
            // pass through and try again
        }
        Thread.sleep(3_000)
    }
    throw IllegalStateException("webserver has not started, takes way too long")
}