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

    fun addOrUpdate(corpus: Corpus): Corpus {
        val includedComponents = componentRepository.findByIdIn(corpus.components)
        if (includedComponents.size != corpus.components.size) {
            val missing = corpus.components.toSet() - includedComponents.map { it.id }
            throw IllegalArgumentException("No components with ids $missing")
        }
        val excludedComponents = componentRepository.findByIdNotIn(corpus.components)
        val corpusEntity = corpusRepository.save(CorpusEntity(corpus.id, corpus.name, includedComponents.toMutableSet()))
        for (component in includedComponents)
            component.corpuses.add(corpusEntity)
        for (component in excludedComponents)
            component.corpuses.remove(corpusEntity)
        return corpusEntity.toCorpus()
    }

    fun getOne(id: Long) = corpusRepository.findByIdOrNull(id)?.toCorpus()

    fun addComponentToCorpus(componentId: Long, corpusId: Long) {
        val corpus = corpusRepository.findByIdOrNull(corpusId)
                ?: throw IllegalArgumentException("No corpus with id $corpusId found")
        val component = componentRepository.findByIdOrNull(componentId)
                ?: throw IllegalArgumentException("No component with id $componentId found")
        corpus.components.add(component)
        component.corpuses.add(corpus)
    }

    fun removeComponentFromCorpus(componentId: Long, corpusId: Long): Boolean {
        val corpus = corpusRepository.findByIdOrNull(corpusId)
                ?: throw IllegalArgumentException("No corpus with id $corpusId found")
        val component = componentRepository.findByIdOrNull(componentId)
                ?: throw IllegalArgumentException("No component with id $componentId found")
        return corpus.components.remove(component) && component.corpuses.remove(corpus)
    }

    fun deleteById(id: Long) = corpusRepository.deleteById(id)
}
