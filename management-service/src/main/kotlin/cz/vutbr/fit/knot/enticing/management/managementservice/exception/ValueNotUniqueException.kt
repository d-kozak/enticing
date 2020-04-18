package cz.vutbr.fit.knot.enticing.management.managementservice.exception

class ValueNotUniqueException(val field: String, message: String) : RuntimeException(message)