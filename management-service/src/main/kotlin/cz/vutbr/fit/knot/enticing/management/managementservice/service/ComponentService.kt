package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.ComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ComponentEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ServerInfoEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toComponentInfo
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ComponentService(
        private val componentRepository: ComponentRepository
) {

    @Transactional
    fun addComponent(staticServerInfo: StaticServerInfo, serverEntity: ServerInfoEntity): ComponentInfo = componentRepository.save(
            ComponentEntity(0, serverEntity, (staticServerInfo.fullAddress.split(":")[1]).toInt(),
                    staticServerInfo.componentType, staticServerInfo.timestamp, mutableListOf(), mutableListOf()))
            .toComponentInfo()
}