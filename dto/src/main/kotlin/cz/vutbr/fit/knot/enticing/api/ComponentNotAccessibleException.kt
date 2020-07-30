package cz.vutbr.fit.knot.enticing.api

/**
 * Thrown when an exception occurs while trying to contact another component
 */
class ComponentNotAccessibleException(message: String, cause: Exception? = null) : RuntimeException(message, cause)