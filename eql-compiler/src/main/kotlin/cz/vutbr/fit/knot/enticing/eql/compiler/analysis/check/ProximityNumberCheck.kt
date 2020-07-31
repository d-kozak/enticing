package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.ProximityRestrictionNode


/**
 * Checks whether proximity value is really a number.
 */
class ProximityNumberCheck(id: String) : EqlAstCheck<ProximityRestrictionNode>(id, ProximityRestrictionNode::class) {
    override fun execute(node: ProximityRestrictionNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        val num = node.distance.toIntOrNull()
        if (num == null) {
            val numLocation = Interval.valueOf(node.location.from + 2, node.location.to)
            reporter.error("Proximity restriction should be an integer", numLocation, id)
        }
    }
}