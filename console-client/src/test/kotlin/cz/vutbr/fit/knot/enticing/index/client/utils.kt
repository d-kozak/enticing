package cz.vutbr.fit.knot.enticing.index.client

fun String.asArgs() = this.split("""\s+""".toRegex()).toTypedArray()