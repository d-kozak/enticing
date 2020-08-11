package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.Corpus
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.toCorpus
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CorpusEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.CorpusRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class CorpusService(
        private val corpusRepository: CorpusRepository,
        private val componentRepository: ComponentRepository
) {


    fun getAll(pageable: Pageable) = corpusRepository.findAll(pageable).map { it.toCorpus() }

    fun addNew(corpus: Corpus): Corpus {
        val components = corpus.components.map {
            componentRepository.findByIdOrNull(it) ?: throw IllegalArgumentException("No component with id $it found")
        }.toMutableSet()
        return corpusRepository.save(CorpusEntity(corpus.id, corpus.name, components)).toCorpus()
    }

    fun getOne(id: Long) = corpusRepository.findByIdOrNull(id)?.toCorpus()

    fun addComponentToCorpus(componentId: Long, corpusId: Long) {
        val corpus = corpusRepository.findByIdOrNull(corpusId)
                ?: throw IllegalArgumentException("No corpus with id $corpusId found")
        val component = componentRepository.findByIdOrNull(componentId)
                ?: throw IllegalArgumentException("No component with id $componentId found")
        corpus.components.add(component)
    }

    fun removeComponentFromCorpus(componentId: Long, corpusId: Long): Boolean {
        val corpus = corpusRepository.findByIdOrNull(corpusId)
                ?: throw IllegalArgumentException("No corpus with id $corpusId found")
        val component = componentRepository.findByIdOrNull(componentId)
                ?: throw IllegalArgumentException("No component with id $componentId found")
        return corpus.components.remove(component)
    }

    fun deleteById(id: Long) = corpusRepository.deleteById(id)
}
