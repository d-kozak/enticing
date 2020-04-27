package cz.vutbr.fit.knot.enticing.server.probe

import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.mx.ServerProbe


fun main() = println(ServerProbe().scan().toJson())



