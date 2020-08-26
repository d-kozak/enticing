package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.api.EnticingComponentApi
import cz.vutbr.fit.knot.enticing.dto.ExtendedComponentInfo
import cz.vutbr.fit.knot.enticing.dto.toComponentAddress
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ComponentEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.CorpusRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.LogRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.PerfRepository
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ComponentService(
        private val commandService: CommandService,
        private val componentRepository: ComponentRepository,
        private val corpusRepository: CorpusRepository,
        private val logRepository: LogRepository,
        private val perfRepository: PerfRepository,
        private val componentApi: EnticingComponentApi
) {

    fun getComponentsOfCorpus(corpusId: Long, pageable: Pageable): Page<ExtendedComponentInfo> {
        val corpus = corpusRepository.findByIdOrNull(corpusId)
                ?: throw IllegalArgumentException("No corpus with id $corpusId")
        return componentRepository.findByCorpusesContains(corpus, pageable).map { it.toComponentInfo() }
    }

    fun getComponentsOnServer(serverId: Long, pageable: Pageable): Page<ExtendedComponentInfo> = componentRepository.findByServerId(serverId, pageable).map { it.toComponentInfo() }

    fun getComponents(componentType: ComponentType?, pageable: Pageable): Page<ExtendedComponentInfo> {
        val entities = when {
            componentType != null -> componentRepository.findByType(componentType, pageable)
            else -> componentRepository.findAll(pageable)
        }
        return entities.map { it.toComponentInfo() }
    }

    fun getComponent(componentId: Long) = componentRepository.findByIdOrNull(componentId)?.toComponentInfo()

    fun removeComponent(componentId: Long) {
        val component = componentRepository.findByIdOrNull(componentId)
                ?: throw IllegalArgumentException("No component with id $componentId")
        logRepository.deleteByComponent(component)
        perfRepository.deleteByComponent(component)
        componentRepository.delete(component)
    }

    fun pingComponent(componentId: Long): StaticServerInfo? {
        val component = requireNotNull(componentRepository.findByIdOrNull(componentId)) { "component with id $componentId not found" }
        return componentApi.ping(component.fullAddress.toComponentAddress())
    }

    fun update(componentId: Long, block: ComponentEntity.() -> Unit) {
        val component = componentRepository.findByIdOrNull(componentId)
                ?: throw IllegalArgumentException("No component with id $componentId found")
        component.block()
    }
}