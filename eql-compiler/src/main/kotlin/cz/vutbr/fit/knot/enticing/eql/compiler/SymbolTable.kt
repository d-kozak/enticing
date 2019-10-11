package cz.vutbr.fit.knot.enticing.eql.compiler

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.QueryElemNode

fun emptySymbolTable(): SymbolTable = mutableMapOf()

typealias SymbolTable = MutableMap<String, QueryElemNode.AssignNode>