package cz.vutbr.fit.knot.enticing.dto

/**
 * Represents the address of the a component, consisting of an IP/URL and a port
 */
data class ComponentAddress(val url: String, val port: Int) {
    companion object {
        fun parse(input: String): ComponentAddress {
            val parts = input.split(":")
            return when (parts.size) {
                1 -> ComponentAddress(parts[0], 8080)
                2 -> ComponentAddress(parts[0], requireNotNull(parts[1].toIntOrNull()) { "Invalid port number in $input, ${parts[1]}" })
                else -> error("Invalid input $input, could not be split into server and port")
            }
        }
    }

    override fun toString(): String = "$url:$port"
}

/**
 * Parse a string into a ComponentAddress object
 */
fun String.toComponentAddress() = ComponentAddress.parse(this)