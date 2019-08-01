package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.IndexServerRequestData
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate


internal class RestTemplateQueryExecutorTest {

    @Test
    fun success() {
        val mockTemplate = mockk<RestTemplate>()
        val serverInfo = IndexServerRequestData("google.com")
        every {
            mockTemplate.exchange<String>("http://" + serverInfo.address + "/api/v1/query", HttpMethod.POST, any())
        } returns ResponseEntity(googleFirstResult.toJson(), HttpStatus.OK)


        val requestDispatcher = RestTemplateQueryExecutor(mockTemplate)
        val result = runBlocking { requestDispatcher(templateQuery, serverInfo) }
        if (result.isFailure) {
            result.rethrowException()
        }
        assertThat(result.value)
                .isEqualTo(googleFirstResult)

        verify(exactly = 1) { mockTemplate.exchange<String>("http://" + serverInfo.address + "/api/v1/query", HttpMethod.POST, any()) }
    }

    @Test
    fun fail() {
        val mockTemplate = mockk<RestTemplate>()
        val serverInfo = IndexServerRequestData("google.com")
        every {
            mockTemplate.exchange<String>("http://" + serverInfo.address + "/api/v1/query", HttpMethod.POST, any())
        } throws FailOnPurposeException("fail!")


        val requestDispatcher = RestTemplateQueryExecutor(mockTemplate)
        val result = runBlocking { requestDispatcher(templateQuery, serverInfo) }

        assertThat(result.isFailure)
                .isTrue()
        assertThrows<FailOnPurposeException> { result.rethrowException() }

        verify(exactly = 1) { mockTemplate.exchange<String>("http://" + serverInfo.address + "/api/v1/query", HttpMethod.POST, any()) }
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