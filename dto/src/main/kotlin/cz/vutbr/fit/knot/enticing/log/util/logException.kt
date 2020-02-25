package cz.vutbr.fit.knot.enticing.log.util

import cz.vutbr.fit.knot.enticing.log.LogService

fun LogService.error(ex: Exception) {
    this.error(ex.toString())
}