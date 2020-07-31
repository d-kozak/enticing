package cz.vutbr.fit.knot.enticing.server.probe

import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.mx.ServerProbe

/**
 * Just print current server status
 * Used to get server info for servers on which no component is running
 */
fun main() = println(ServerProbe().scan().toJson())



