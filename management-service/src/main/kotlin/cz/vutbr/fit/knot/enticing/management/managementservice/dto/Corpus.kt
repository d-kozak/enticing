package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.dto.Status
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CorpusEntity

fun CorpusEntity.toCorpus() = Corpus(id, name, status, components.map { it.id })

data class Corpus(
        val id: Long,
        val name: String,
        val status: Status = Status.DEAD,
        val components: List<Long> = listOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Corpus) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
