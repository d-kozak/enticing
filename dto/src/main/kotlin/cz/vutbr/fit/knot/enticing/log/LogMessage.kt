package cz.vutbr.fit.knot.enticing.log

/**
 * Log message to be transmitted
 */
data class LogMessage(val kind: String, val text: String, val timestamp: Long = System.currentTimeMillis())