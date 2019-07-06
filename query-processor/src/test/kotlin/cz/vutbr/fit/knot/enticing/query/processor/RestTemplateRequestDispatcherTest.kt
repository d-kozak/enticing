package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.query.ServerInfo
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate


internal class RestTemplateRequestDispatcherTest {

    @Test
    fun success() {
        val mockTemplate = mockk<RestTemplate>()
        val serverInfo = ServerInfo("google.com")
        every {
            mockTemplate.exchange<SearchResult>("http://" + serverInfo.address + "/api/v1/query", HttpMethod.POST, any())
        } returns ResponseEntity(googleFirstResult, HttpStatus.OK)


        val requestDispatcher = RestTemplateRequestDispatcher(mockTemplate)
        val result = requestDispatcher(templateQuery, serverInfo)
        if (result.isFailure) {
            result.rethrowException()
        }
        assertThat(result.value)
                .isEqualTo(googleFirstResult)

        verify(exactly = 1) { mockTemplate.exchange<SearchResult>("http://" + serverInfo.address + "/api/v1/query", HttpMethod.POST, any()) }
    }

    @Test
    fun fail() {
        val mockTemplate = mockk<RestTemplate>()
        val serverInfo = ServerInfo("google.com")
        every {
            mockTemplate.exchange<SearchResult>("http://" + serverInfo.address + "/api/v1/query", HttpMethod.POST, any())
        } throws FailOnPurposeException("fail!")


        val requestDispatcher = RestTemplateRequestDispatcher(mockTemplate)
        val result = requestDispatcher(templateQuery, serverInfo)

        assertThat(result.isFailure)
                .isTrue()
        assertThrows<FailOnPurposeException> { result.rethrowException() }

        verify(exactly = 1) { mockTemplate.exchange<SearchResult>("http://" + serverInfo.address + "/api/v1/query", HttpMethod.POST, any()) }
    }


    @Nested
    inner class RestTemplateTest {

        @Test
        fun failing() {
            val restTemplate = RestTemplate()
            try {
                val response = restTemplate.getForEntity("http://localhost/api/v1/query", String::class.java)
                println("success")
            } catch (ex: Exception) {
                println("failed")
            }
        }
    }
}