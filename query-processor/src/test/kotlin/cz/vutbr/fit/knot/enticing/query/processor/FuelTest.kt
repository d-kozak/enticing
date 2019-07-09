package cz.vutbr.fit.knot.enticing.query.processor


import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.github.kittinunf.fuel.httpGet
import cz.vutbr.fit.knot.enticing.query.processor.fuel.awaitDto
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


    private data class Todo(val userId: Int, val id: Int, val title: String, val completed: Boolean)

    @Test
    fun `get todo object`() {

        val todo = runBlocking {
            "https://jsonplaceholder.typicode.com/todos/1".httpGet()
                    .awaitDto<Todo>()
        }

        println("got todo $todo")
    }
}