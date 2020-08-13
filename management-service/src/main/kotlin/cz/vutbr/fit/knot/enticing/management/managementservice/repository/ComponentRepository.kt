package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ComponentEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CorpusEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ComponentRepository : JpaRepository<ComponentEntity, Long> {

    fun findByIdIn(ids: List<Long>): List<ComponentEntity>
    fun findByIdNotIn(ids: List<Long>): List<ComponentEntity>

    fun findByLastHeartbeatBefore(limit: LocalDateTime): List<ComponentEntity>
    fun findByServerId(id: Long, pageable: Pageable): Page<ComponentEntity>
    fun findByServerAddressAndPort(serverAddress: String, port: Int): ComponentEntity?
    fun findByType(componentType: ComponentType, pageable: Pageable): Page<ComponentEntity>

    fun findByCorpusesContains(corpusEntity: CorpusEntity, pageable: Pageable): Page<ComponentEntity>
}


fun ComponentRepository.findByFullAddress(fullAddress: String): ComponentEntity? {
    val (address, port) = fullAddress.split(":")
    return this.findByServerAddressAndPort(address, port.toInt())
}