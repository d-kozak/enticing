package cz.vutbr.fit.knot.enticing.management.managementservice

import cz.vutbr.fit.knot.enticing.management.runManagementCli
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ManagementServiceApplication

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "at least 2 arguments expected" }
    when (val type = args[0]) {
        "cli" -> runManagementCli(args.copyOfRange(1, args.size))
        "server" -> {
            require(args.size >= 4) { "at least three arguments expected - component type, build id, config file and service id, got '${args.contentToString()}'" }
            if (!args[1].startsWith("--build.id=")) {
                args[1] = "--build.id=" + args[1]
            }
            if (!args[2].startsWith("--config.file=")) {
                args[2] = "--config.file=" + args[2]
            }
            if (!args[3].startsWith("--service.id=")) {
                args[3] = "--service.id=" + args[3]
            }
            runApplication<ManagementServiceApplication>(*args)
        }
        else -> throw IllegalArgumentException("Unknown management type '$type'")
    }

}
