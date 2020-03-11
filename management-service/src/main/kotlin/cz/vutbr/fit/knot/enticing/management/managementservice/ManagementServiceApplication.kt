package cz.vutbr.fit.knot.enticing.management.managementservice

import cz.vutbr.fit.knot.enticing.management.runManagementCli
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ManagementServiceApplication

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "two arguments expected - config file and server address" }
    when (val type = args[0]) {
        "cli" -> runManagementCli(args.copyOfRange(1, args.size))
        "server" -> {
            require(args.size >= 3) { "at least three arguments expected - component type config file service id, got '${args.contentToString()}'" }
            if (!args[1].startsWith("--config.file=")) {
                args[1] = "--config.file=" + args[1]
            }
            if (!args[2].startsWith("--service.id=")) {
                args[2] = "--service.id=" + args[2]
            }
            runApplication<ManagementServiceApplication>(*args)
        }
        else -> throw IllegalArgumentException("Unknown management type '$type'")
    }

}
