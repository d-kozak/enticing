package cz.vutbr.fit.knot.enticing.management.managementservice.dto

data class GeneralOperationStatistics(
        val operationId: String,
        var averageDuration: Double = 0.0,
        var averageDurationDeviation: Double = 0.0,
        var maxDuration: Long = 0,
        var minDuration: Long = 0,
        var invocationCount: Int = 0
)