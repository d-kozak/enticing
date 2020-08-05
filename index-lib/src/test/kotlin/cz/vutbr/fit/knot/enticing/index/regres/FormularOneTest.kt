package cz.vutbr.fit.knot.enticing.index.regres

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FormularOneTest : AbstractDocumentMatchingRegressionTest("formulaOneSeason") {
    @Test
    fun `query`() {
        val query = "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
        val matchInfo = submitQuery(query)
        assertThat(matchInfo.intervals).hasSize(1)
        val singleMatch = matchInfo.intervals[0]
        assertThat(singleMatch.interval).isEqualTo(Interval.valueOf(4420, 4438))
    }
}