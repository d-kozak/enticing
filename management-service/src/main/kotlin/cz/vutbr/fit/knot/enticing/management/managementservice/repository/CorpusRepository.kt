package cz.vutbr.fit.knot.enticing.management.managementservice.repository

import cz.vutbr.fit.knot.enticing.management.managementservice.entity.CorpusEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CorpusRepository : JpaRepository<CorpusEntity, Long>