// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/Mg4j.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Mg4jParser}.
 */
public interface Mg4jListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link Mg4jParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterExpression(Mg4jParser.ExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitExpression(Mg4jParser.ExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#query}.
     *
     * @param ctx the parse tree
     */
    void enterQuery(Mg4jParser.QueryContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#query}.
     *
     * @param ctx the parse tree
     */
    void exitQuery(Mg4jParser.QueryContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#queryNode}.
     *
     * @param ctx the parse tree
     */
    void enterQueryNode(Mg4jParser.QueryNodeContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#queryNode}.
     *
     * @param ctx the parse tree
     */
    void exitQueryNode(Mg4jParser.QueryNodeContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#indexTerm}.
     *
     * @param ctx the parse tree
     */
    void enterIndexTerm(Mg4jParser.IndexTermContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#indexTerm}.
     *
     * @param ctx the parse tree
     */
    void exitIndexTerm(Mg4jParser.IndexTermContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#simpleNode}.
     *
     * @param ctx the parse tree
     */
    void enterSimpleNode(Mg4jParser.SimpleNodeContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#simpleNode}.
     *
     * @param ctx the parse tree
     */
    void exitSimpleNode(Mg4jParser.SimpleNodeContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#nertagSem}.
     *
     * @param ctx the parse tree
     */
    void enterNertagSem(Mg4jParser.NertagSemContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#nertagSem}.
     *
     * @param ctx the parse tree
     */
    void exitNertagSem(Mg4jParser.NertagSemContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#sem}.
     *
     * @param ctx the parse tree
     */
    void enterSem(Mg4jParser.SemContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#sem}.
     *
     * @param ctx the parse tree
     */
    void exitSem(Mg4jParser.SemContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#proximity}.
     *
     * @param ctx the parse tree
     */
    void enterProximity(Mg4jParser.ProximityContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#proximity}.
     *
     * @param ctx the parse tree
     */
    void exitProximity(Mg4jParser.ProximityContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#index}.
     *
     * @param ctx the parse tree
     */
    void enterIndex(Mg4jParser.IndexContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#index}.
     *
     * @param ctx the parse tree
     */
    void exitIndex(Mg4jParser.IndexContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#diff}.
     *
     * @param ctx the parse tree
     */
    void enterDiff(Mg4jParser.DiffContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#diff}.
     *
     * @param ctx the parse tree
     */
    void exitDiff(Mg4jParser.DiffContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#queryReference}.
     *
     * @param ctx the parse tree
     */
    void enterQueryReference(Mg4jParser.QueryReferenceContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#queryReference}.
     *
     * @param ctx the parse tree
     */
    void exitQueryReference(Mg4jParser.QueryReferenceContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#sequence}.
     *
     * @param ctx the parse tree
     */
    void enterSequence(Mg4jParser.SequenceContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#sequence}.
     *
     * @param ctx the parse tree
     */
    void exitSequence(Mg4jParser.SequenceContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#arb}.
     *
     * @param ctx the parse tree
     */
    void enterArb(Mg4jParser.ArbContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#arb}.
     *
     * @param ctx the parse tree
     */
    void exitArb(Mg4jParser.ArbContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#arbTerm}.
     *
     * @param ctx the parse tree
     */
    void enterArbTerm(Mg4jParser.ArbTermContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#arbTerm}.
     *
     * @param ctx the parse tree
     */
    void exitArbTerm(Mg4jParser.ArbTermContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#arbTermLast}.
     *
     * @param ctx the parse tree
     */
    void enterArbTermLast(Mg4jParser.ArbTermLastContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#arbTermLast}.
     *
     * @param ctx the parse tree
     */
    void exitArbTermLast(Mg4jParser.ArbTermLastContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#arbTermWithOp}.
     *
     * @param ctx the parse tree
     */
    void enterArbTermWithOp(Mg4jParser.ArbTermWithOpContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#arbTermWithOp}.
     *
     * @param ctx the parse tree
     */
    void exitArbTermWithOp(Mg4jParser.ArbTermWithOpContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#nodeWithoutReference}.
     *
     * @param ctx the parse tree
     */
    void enterNodeWithoutReference(Mg4jParser.NodeWithoutReferenceContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#nodeWithoutReference}.
     *
     * @param ctx the parse tree
     */
    void exitNodeWithoutReference(Mg4jParser.NodeWithoutReferenceContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#constraintNode}.
     *
     * @param ctx the parse tree
     */
    void enterConstraintNode(Mg4jParser.ConstraintNodeContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#constraintNode}.
     *
     * @param ctx the parse tree
     */
    void exitConstraintNode(Mg4jParser.ConstraintNodeContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#constraint}.
     *
     * @param ctx the parse tree
     */
    void enterConstraint(Mg4jParser.ConstraintContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#constraint}.
     *
     * @param ctx the parse tree
     */
    void exitConstraint(Mg4jParser.ConstraintContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#reference}.
     *
     * @param ctx the parse tree
     */
    void enterReference(Mg4jParser.ReferenceContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#reference}.
     *
     * @param ctx the parse tree
     */
    void exitReference(Mg4jParser.ReferenceContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#binaryOp}.
     *
     * @param ctx the parse tree
     */
    void enterBinaryOp(Mg4jParser.BinaryOpContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#binaryOp}.
     *
     * @param ctx the parse tree
     */
    void exitBinaryOp(Mg4jParser.BinaryOpContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#unaryOp}.
     *
     * @param ctx the parse tree
     */
    void enterUnaryOp(Mg4jParser.UnaryOpContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#unaryOp}.
     *
     * @param ctx the parse tree
     */
    void exitUnaryOp(Mg4jParser.UnaryOpContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jParser#relOp}.
     *
     * @param ctx the parse tree
     */
    void enterRelOp(Mg4jParser.RelOpContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#relOp}.
     *
     * @param ctx the parse tree
     */
    void exitRelOp(Mg4jParser.RelOpContext ctx);
}