package cz.vutbr.fit.knot.enticing.index.regres

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UlloTest : AbstractDocumentMatchingRegressionTest("ulloGottaNewMotor") {
    @Test
    fun query() {
        val query = "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
        val (intervals) = submitQuery(query)
        assertThat(intervals.size).isEqualTo(2)
        assertThat(testedDocument.loadText(intervals[0]))
                .isEqualTo("Sayle ( the presenter ) talks through the influence of the car on the post war working classes and also features villain John McVicar")
        assertThat(testedDocument.loadText(intervals[1]))
                .isEqualTo("Sayle ( the presenter ) talks through the influence of the car on the post war working classes and also features villain John McVicar ( who")

    }

}