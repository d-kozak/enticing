package cz.vutbr.fit.knot.enticing.webserver.exception

class ValueNotUniqueException(val field: String, message: String) : RuntimeException(message)