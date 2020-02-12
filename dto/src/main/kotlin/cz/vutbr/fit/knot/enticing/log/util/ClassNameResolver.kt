package cz.vutbr.fit.knot.enticing.log.util

inline fun resolveName(noinline func: () -> Unit): String {
    val name = func.javaClass.name
    return when {
        name.contains("Kt$") -> name.substringBefore("Kt$")
        name.contains("$") -> name.substringBefore("$")
        else -> name
    }
}
