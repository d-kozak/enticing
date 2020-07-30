package cz.vutbr.fit.knot.enticing.utils

import com.github.kittinunf.fuel.core.FuelError

/**
 * Retry a block of code multiple times until success.
 * @throws last encountered exception if no execution succeeds
 */
inline fun <T> retry(count: Int, block: () -> T): T {
    var lastEx: FuelError? = null
    for (i in 0 until count) {
        try {
            return block()
        } catch (ex: FuelError) {
            lastEx = ex
        }
    }
    throw lastEx!!
}