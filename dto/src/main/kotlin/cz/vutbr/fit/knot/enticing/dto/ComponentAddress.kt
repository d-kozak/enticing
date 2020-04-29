package cz.vutbr.fit.knot.enticing.dto

data class ComponentAddress(val url: String, val port: Int) {
    companion object {
        fun parse(input: String): ComponentAddress {
            val parts = input.split(":")
            require(parts.size == 2) { "Invalid input $input, could not be split into server and port" }
            val url = parts[0]
            val port = requireNotNull(parts[1].toIntOrNull()) { "Invalid port number in $input, ${parts[1]}" }
            return ComponentAddress(url, port)
        }
    }

    override fun toString(): String = "$url:$port"
}

fun String.toComponentAddress() = ComponentAddress.parse(this)