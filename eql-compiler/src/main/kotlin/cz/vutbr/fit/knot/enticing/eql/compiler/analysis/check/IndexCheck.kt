package cz.vutbr.fit.knot.enticing.eql.compiler.analysis.check

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.EqlAstCheck
import cz.vutbr.fit.knot.enticing.eql.compiler.analysis.Reporter
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

class IndexCheck(id: String) : EqlAstCheck<QueryElemNode.IndexNode>(id, QueryElemNode.IndexNode::class) {
    override fun analyze(node: QueryElemNode.IndexNode, corpusConfiguration: CorpusConfiguration, reporter: Reporter) {
        if (node.index !in corpusConfiguration.indexes) {
            reporter.error("Index '${node.index}' is not available", node.location, id)
        }
    }
}