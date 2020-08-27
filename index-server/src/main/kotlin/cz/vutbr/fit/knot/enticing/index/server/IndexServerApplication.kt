package cz.vutbr.fit.knot.enticing.index.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class IndexServerApplication

/**
 * Start the component
 */
fun main(args: Array<String>) {
    require(args.size == 3) { "thee arguments expected - build id, config file and server address" }

    if (!args[0].startsWith("--build.id=")) {
        args[0] = "--build.id=" + args[0]
    }

    if (!args[1].startsWith("--config.file=")) {
        args[1] = "--config.file=" + args[1]
    }

    if (!args[2].startsWith("--service.id=")) {
        args[2] = "--service.id=" + args[2]
    }
    runApplication<IndexServerApplication>(*args)
}
