package cz.vutbr.fit.knot.enticing.management

fun main(args: Array<String>) {
    runManagementCli(args)
}

fun runManagementCli(args: Array<String>) {
    val args = parseCliArgs(args)
    println(args)
}