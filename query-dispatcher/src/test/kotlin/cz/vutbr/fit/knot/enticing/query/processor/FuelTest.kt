package cz.vutbr.fit.knot.enticing.query.processor


import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

/**
 * Tests showing how to use fuel library for async http calls
 */
class FuelTest {

    @Test
    fun `get doc`() = runBlocking {
        val result = "http://github.com/kittinunf/fuel".httpGet()
                .awaitStringResponse()

        println(result)
    }

}