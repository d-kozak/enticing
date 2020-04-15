package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ComponentEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerInfoEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.LogRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.PerfRepository
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ComponentService(
        private val componentRepository: ComponentRepository,
        private val logRepository: LogRepository,
        private val perfRepository: PerfRepository
) {


    fun addComponent(staticServerInfo: StaticServerInfo, serverEntity: ServerInfoEntity): ComponentInfo = componentRepository.save(
            ComponentEntity(0, serverEntity, (staticServerInfo.fullAddress.split(":")[1]).toInt(),
                    staticServerInfo.componentType, staticServerInfo.timestamp, mutableListOf(), mutableListOf()))
            .toComponentInfo()

    fun deleteComponent(component: ComponentEntity) {
        logRepository.deleteByComponent(component)
        perfRepository.deleteByComponent(component)
        componentRepository.delete(component)
    }

    fun getComponentsOnServer(serverId: Long, pageable: Pageable): Page<ComponentInfo> = componentRepository.findByServerId(serverId, pageable).map { it.toComponentInfo() }

    fun getComponents(pageable: Pageable): Page<ComponentInfo> = componentRepository.findAll(pageable).map { it.toComponentInfo() }
}