package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.ComponentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ComponentRepository : JpaRepository<ComponentEntity, Long> {

    fun findByServerId(id: Long, pageable: Pageable): Page<ComponentEntity>
    fun findByServerAddressAndPort(serverAddress: String, port: Int): ComponentEntity?
}


fun ComponentRepository.findByFullAddress(fullAddress: String): ComponentEntity? {
    val (address, port) = fullAddress.split(":")
    return this.findByServerAddressAndPort(address, port.toInt())
}