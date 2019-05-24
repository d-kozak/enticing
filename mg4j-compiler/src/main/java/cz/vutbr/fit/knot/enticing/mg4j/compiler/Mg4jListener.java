// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/Mg4j.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Mg4jParser}.
 */
public interface Mg4jListener extends ParseTreeListener {
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
     * Enter a parse tree produced by {@link Mg4jParser#node}.
     *
     * @param ctx the parse tree
     */
    void enterNode(Mg4jParser.NodeContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#node}.
     *
     * @param ctx the parse tree
     */
    void exitNode(Mg4jParser.NodeContext ctx);

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
     * Enter a parse tree produced by {@link Mg4jParser#boolOp}.
     *
     * @param ctx the parse tree
     */
    void enterBoolOp(Mg4jParser.BoolOpContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jParser#boolOp}.
     *
     * @param ctx the parse tree
     */
    void exitBoolOp(Mg4jParser.BoolOpContext ctx);

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