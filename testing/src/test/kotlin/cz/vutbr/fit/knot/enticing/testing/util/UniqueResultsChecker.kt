package cz.vutbr.fit.knot.enticing.testing.util

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.uniqueId
import org.junit.jupiter.api.fail

fun assertUniqueResults(results: List<IndexServer.SearchResult>) {
    val duplicities = results.groupBy { it.uniqueId }
            .values
            .filter { it.size > 1 }
    fail {
        duplicities.joinToString("\n", prefix = "Following documents contain duplicities") {
            it.joinToString("\n", prefix = "<<Group start>>", postfix = "<<Group end>>")
        }
    }
}