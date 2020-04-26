package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.PerfDto
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.GeneralOperationStatistics
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.PerfEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.ComponentRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.PerfRepository
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.findByFullAddress
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.abs
import kotlin.math.sqrt

@Transactional
@Service
class ManagementPerfService(
        val perfRepository: PerfRepository,
        val componentRepository: ComponentRepository,
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }

    fun add(perf: PerfDto): PerfDto? {
        val component = componentRepository.findByFullAddress(perf.componentAddress)
        if (component == null) {
            logger.warn("Received log from an unknown component at ${perf.componentAddress}")
            return null
        }
        return perfRepository.save(perf.toEntity(component)).toDto()
    }

    fun getPerfLogs(operationId: String?, componentType: ComponentType?, pageable: Pageable): Page<PerfDto> {
        val entities = when {
            operationId != null && componentType != null -> perfRepository.findByOperationIdAndComponentType(operationId, componentType, pageable)
            operationId != null -> perfRepository.findByOperationId(operationId, pageable)
            componentType != null -> perfRepository.findByComponentType(componentType, pageable)
            else -> perfRepository.findAll(pageable)
        }
        return entities.map { it.toDto() }
    }

    fun getAllOperationStats(): Map<String, GeneralOperationStatistics> = perfRepository.findAll()
            .groupBy { it.operationId }
            .map { (operationId, operations) -> computeSingleOperationStats(operationId, operations) }
            .associateBy { it.operationId }

    fun getSingleOperationStats(operationId: String): GeneralOperationStatistics = computeSingleOperationStats(operationId, perfRepository.findByOperationId(operationId))

    internal fun computeSingleOperationStats(operationId: String, operations: List<PerfEntity>): GeneralOperationStatistics {
        val stats = GeneralOperationStatistics(operationId)
        stats.invocationCount = operations.size
        val durationSum = operations.fold(0L) { acc, perf ->
            if (stats.minDuration == 0L || stats.minDuration > perf.duration) stats.minDuration = perf.duration
            if (stats.maxDuration == 0L || stats.maxDuration < perf.duration) stats.maxDuration = perf.duration
            acc + perf.duration
        }
        stats.averageDuration = durationSum.toDouble() / operations.size
        stats.averageDurationDeviation = sqrt(operations.fold(0.0) { acc, perf -> acc + abs(perf.duration - stats.averageDuration) } / operations.size)
        return stats
    }
}