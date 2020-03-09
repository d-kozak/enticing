package cz.vutbr.fit.knot.enticing.management.managementservice.service

import cz.vutbr.fit.knot.enticing.log.PerfDto
import cz.vutbr.fit.knot.enticing.management.managementservice.dto.GeneralOperationStatistics
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.PerfEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toDto
import cz.vutbr.fit.knot.enticing.management.managementservice.entity.toEntity
import cz.vutbr.fit.knot.enticing.management.managementservice.repository.PerfRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.math.abs
import kotlin.math.sqrt

@Service
class ManagementPerfService(val perfRepository: PerfRepository) {

    fun add(Perf: PerfDto) = perfRepository.save(Perf.toEntity()).toDto()


    fun computeOperationStatistics(): Map<String, GeneralOperationStatistics> = perfRepository.findAll()
            .groupBy { it.operationId }
            .map { (operationId, operations) -> singleOperationStats(operationId, operations) }
            .associateBy { it.operationId }


    internal fun singleOperationStats(operationId: String, operations: List<PerfEntity>): GeneralOperationStatistics {
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

    fun getAll(pageable: Pageable) = perfRepository.findAll(pageable).map { it.toDto() }
}