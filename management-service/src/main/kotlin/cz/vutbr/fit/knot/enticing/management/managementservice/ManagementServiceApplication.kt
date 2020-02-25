package cz.vutbr.fit.knot.enticing.management.managementservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ManagementServiceApplication

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "two arguments expected - config file and server address" }
    if (!args[0].startsWith("--config.file=")) {
        args[0] = "--config.file=" + args[0]
    }
    if (!args[1].startsWith("--service.id=")) {
        args[1] = "--service.id=" + args[1]
    }
    runApplication<ManagementServiceApplication>(*args)
}
