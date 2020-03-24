package cz.vutbr.fit.knot.enticing.testing.performance

import com.github.kittinunf.fuel.httpGet
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.utils.asJsonObject
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
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

    var settingsId: Int = 2

    var sessionId: String? = null

    val queryEndpoint: String
        get() = "http://$address/api/v1/query?settings=$settingsId"


    fun login(username: String, password: String) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA
        val map = LinkedMultiValueMap<String, String>()
        map.add("username", username)
        map.add("password", password)
        performRequest("http://$address/api/v1/login", HttpMethod.POST, headers, contentMap = map)
    }

    fun userInfo(): String {
        return performRequest("http://$address/api/v1/user", HttpMethod.GET, HttpHeaders())
    }

    fun sendQuery(query: SearchQuery): WebServer.ResultList {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return performRequest(queryEndpoint, HttpMethod.POST, headers, content = query.toJson()).toDto()
    }

    fun importSearchSettings(searchSettings: String) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val res = performRequest("http://$address/api/v1/search-settings/import/", HttpMethod.POST, headers, searchSettings)
        settingsId = res.asJsonObject()["id"].intValue()
    }

    /**
     * memoizes JSESSION
     */
    private fun performRequest(address: String, method: HttpMethod, headers: HttpHeaders, content: String? = null, contentMap: LinkedMultiValueMap<String, String>? = null): String {
        if (sessionId != null)
            headers.add("Cookie", "JSESSIONID=$sessionId")

        val entity = when {
            content != null -> HttpEntity(content, headers)
            contentMap != null -> HttpEntity(contentMap, headers)
            else -> HttpEntity(headers)
        }

        val res = RestTemplate().exchange(address, method, entity, String::class.java)

        val outHeaders = res.headers
        val newJSession = outHeaders.get(HttpHeaders.SET_COOKIE)?.find { it.startsWith("JSESSIONID=") }
        if (newJSession != null)
            sessionId = newJSession.substring(newJSession.indexOf('=') + 1).trim()
        return res.body ?: ""
    }

    fun getMore(): WebServer.ResultList {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return performRequest("http://$address/api/v1/query/get_more", HttpMethod.GET, headers).toDto()
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