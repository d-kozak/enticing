package cz.vutbr.fit.knot.enticing.query.processor.asynchron

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import java.lang.Thread.sleep
import kotlin.coroutines.CoroutineContext

sealed class DispatcherMessage
data class InputMessage(val value: Int) : DispatcherMessage()
data class ResultMessage(val result: Int) : DispatcherMessage()

fun log(msg: String) = println("[${Thread.currentThread()}] $msg")

fun CoroutineScope.executor(id: Int, resultChannel: SendChannel<ResultMessage>) = actor<Int> {
    for (msg in channel) {
        log("Executor $id, received message $msg")
        if (resultChannel.isClosedForSend) {
            log("Executor $id, exiting")
            return@actor
        }
        delay(3000)
        val result = msg * id
        log("Executor $id, sending result $result")
        resultChannel.send(ResultMessage(result))
    }
}

fun CoroutineScope.dispatcher(serverCount: Int, outputChannel: SendChannel<Int>) = actor<DispatcherMessage> {
    val executors = (0 until serverCount).map { executor(it, this.channel) }

    for (msg in channel) {
        when (msg) {
            is InputMessage -> {
                log("Dispatcher: received number ${msg.value} to proccess")
                executors.forEach { it.send(msg.value) }
            }
            is ResultMessage -> {
                log("Dispatcher: Received result ${msg.result}")
                outputChannel.send(msg.result)
            }
        }
    }
}


class WrapperService : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job()

    fun startIt(input: Int, callback: (Int) -> Unit) = async(coroutineContext) {
        val outputChannel = Channel<Int>()
        val dispatcher = dispatcher(10, outputChannel)
        dispatcher.send(InputMessage(input))
        for (msg in outputChannel) {
            callback(msg)
        }
    }

}

fun main() {
    val service = WrapperService()
    service.startIt(42) {
        log("main: received $it")
    }
    sleep(10000)
}