package cz.vutbr.fit.knot.enticing.eql.compiler.parser

import cz.vutbr.fit.knot.enticing.dto.PureMgj4Node
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.eql.compiler.ast.visitor.EqlAstGeneratingVisitor
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

internal fun parseWithAntlr(input: String): Pair<EqlParser.RootContext, List<SyntaxError>> {
    val errorListener = EqlCompiler.ErrorListener()
    val parseTree = EqlParser(CommonTokenStream(EqlLexer(CharStreams.fromString(input))))
            .also {
                it.removeErrorListeners()
                it.addErrorListener(errorListener)
            }.root()

    return parseTree to errorListener.errors
}

@Incomplete("perform semantic analysis over syntactically incorrect ast as well")
internal fun parseToEqlAst(input: String): ParsedQuery {
    val (parseTree, errors) = parseWithAntlr(input)
    val ast = if (errors.isEmpty()) parseTree.accept(EqlAstGeneratingVisitor()) else PureMgj4Node(input)

    return ParsedQuery(ast, errors)
}