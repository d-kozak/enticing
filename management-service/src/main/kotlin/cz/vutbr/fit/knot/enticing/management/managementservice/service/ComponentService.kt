package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandRequest
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.CommandType
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ComponentEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.LogRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.PerfRepository
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
        private val logRepository: LogRepository,
        private val perfRepository: PerfRepository
) {

    fun deleteComponent(component: ComponentEntity): ComponentInfo {
        logRepository.deleteByComponent(component)
        perfRepository.deleteByComponent(component)
        componentRepository.delete(component)
        val commandType = when (component.type) {
            ComponentType.WEBSERVER -> CommandType.KILL_WEBSERVER
            ComponentType.INDEX_SERVER -> CommandType.KILL_INDEX_SERVER
            else -> error("cant kill this type of component $component")
        }
        commandService.enqueue(CommandRequest(commandType, component.fullAddress))
        return component.toComponentInfo()
    }

    fun getComponentsOnServer(serverId: Long, pageable: Pageable): Page<ComponentInfo> = componentRepository.findByServerId(serverId, pageable).map { it.toComponentInfo() }

    fun getComponents(componentType: ComponentType?, pageable: Pageable): Page<ComponentInfo> {
        val entities = when {
            componentType != null -> componentRepository.findByType(componentType, pageable)
            else -> componentRepository.findAll(pageable)
        }
        return entities.map { it.toComponentInfo() }
    }

    fun getComponent(componentId: Long) = componentRepository.findByIdOrNull(componentId)?.toComponentInfo()

    fun deleteComponent(componentId: Long): ComponentInfo {
        val component = componentRepository.findByIdOrNull(componentId)
                ?: throw IllegalArgumentException("No component with id $componentId")
        return deleteComponent(component)
    }
}