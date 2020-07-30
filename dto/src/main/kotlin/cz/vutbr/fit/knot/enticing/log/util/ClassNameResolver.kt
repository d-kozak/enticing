package cz.vutbr.fit.knot.enticing.log.util

/**
 * Resolves the name of a class based on the function that is being passed in.
 * It is a little hacky, but thanks to this it is possible to infer the name without having to pass it into the logger
 */
inline fun resolveName(noinline func: () -> Unit): String {
    val name = func.javaClass.name
    return when {
        name.contains("Kt$") -> name.substringBefore("Kt$")
        name.contains("$") -> name.substringBefore("$")
        else -> name
    }
}
