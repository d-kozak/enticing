package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.SymbolTable
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.RestrictionTypeNode


class ProximityNumberCheck(id:String) : EqlAstCheck<RestrictionTypeNode.ProximityNode>(id, RestrictionTypeNode.ProximityNode::class) {
    override fun analyze(node: RestrictionTypeNode.ProximityNode, symbolTable: SymbolTable, metadataConfiguration: MetadataConfiguration, reporter: Reporter) {
        val num = node.distance.toIntOrNull()
        if (num == null) {
            val numLocation = Interval.valueOf(node.location.from + 2, node.location.to)
            reporter.error("Proximity restriction should be an integer", numLocation, id)
        }
    }
}