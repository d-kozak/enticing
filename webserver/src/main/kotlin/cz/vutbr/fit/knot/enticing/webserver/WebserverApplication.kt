package cz.vutbr.fit.knot.enticing.webserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableCaching
class WebserverApplication

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
    runApplication<WebserverApplication>(*args)
}
