package cz.vutbr.fit.knot.enticing.index.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IndexServerApplication

fun main(args: Array<String>) {
    runApplication<IndexServerApplication>(*args)
}
