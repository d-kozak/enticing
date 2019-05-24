// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/Mg4j.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Mg4jParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Mg4jVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link Mg4jParser#expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExpression(Mg4jParser.ExpressionContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#query}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQuery(Mg4jParser.QueryContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#queryNode}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQueryNode(Mg4jParser.QueryNodeContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#indexTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexTerm(Mg4jParser.IndexTermContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#simpleNode}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSimpleNode(Mg4jParser.SimpleNodeContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#nertagSem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNertagSem(Mg4jParser.NertagSemContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#sem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSem(Mg4jParser.SemContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#proximity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProximity(Mg4jParser.ProximityContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#index}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndex(Mg4jParser.IndexContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#diff}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDiff(Mg4jParser.DiffContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#queryReference}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQueryReference(Mg4jParser.QueryReferenceContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#sequence}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSequence(Mg4jParser.SequenceContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#arb}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArb(Mg4jParser.ArbContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#arbTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArbTerm(Mg4jParser.ArbTermContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#arbTermLast}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArbTermLast(Mg4jParser.ArbTermLastContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#arbTermWithOp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArbTermWithOp(Mg4jParser.ArbTermWithOpContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#nodeWithoutReference}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNodeWithoutReference(Mg4jParser.NodeWithoutReferenceContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#constraintNode}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitConstraintNode(Mg4jParser.ConstraintNodeContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#constraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitConstraint(Mg4jParser.ConstraintContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#reference}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitReference(Mg4jParser.ReferenceContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#binaryOp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBinaryOp(Mg4jParser.BinaryOpContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#unaryOp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUnaryOp(Mg4jParser.UnaryOpContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#relOp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRelOp(Mg4jParser.RelOpContext ctx);
}