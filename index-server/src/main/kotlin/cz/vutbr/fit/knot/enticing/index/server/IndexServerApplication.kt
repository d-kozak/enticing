package cz.vutbr.fit.knot.enticing.index.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IndexServerApplication

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "at least one argument expected, the config file" }
    if (!args[0].startsWith("--config.file=")) {
        args[0] = "--config.file=" + args[0]
    }
    if (args.size >= 2) {
        if (!args[1].startsWith("--collections.config")) {
            args[1] = "--collections.config=" + args[1]
        }
    }
    runApplication<IndexServerApplication>(*args)
}
