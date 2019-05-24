// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/Mg4jEql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Mg4jEqlParser}.
 */
public interface Mg4jEqlListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#query}.
     *
     * @param ctx the parse tree
     */
    void enterQuery(Mg4jEqlParser.QueryContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#query}.
     *
     * @param ctx the parse tree
     */
    void exitQuery(Mg4jEqlParser.QueryContext ctx);

    /**
     * Enter a parse tree produced by the {@code sequence}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterSequence(Mg4jEqlParser.SequenceContext ctx);

    /**
     * Exit a parse tree produced by the {@code sequence}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitSequence(Mg4jEqlParser.SequenceContext ctx);

    /**
     * Enter a parse tree produced by the {@code paren}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterParen(Mg4jEqlParser.ParenContext ctx);

    /**
     * Exit a parse tree produced by the {@code paren}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitParen(Mg4jEqlParser.ParenContext ctx);

    /**
     * Enter a parse tree produced by the {@code nertag}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterNertag(Mg4jEqlParser.NertagContext ctx);

    /**
     * Exit a parse tree produced by the {@code nertag}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitNertag(Mg4jEqlParser.NertagContext ctx);

    /**
     * Enter a parse tree produced by the {@code orderOperation}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterOrderOperation(Mg4jEqlParser.OrderOperationContext ctx);

    /**
     * Exit a parse tree produced by the {@code orderOperation}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitOrderOperation(Mg4jEqlParser.OrderOperationContext ctx);

    /**
     * Enter a parse tree produced by the {@code binaryOperation}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterBinaryOperation(Mg4jEqlParser.BinaryOperationContext ctx);

    /**
     * Exit a parse tree produced by the {@code binaryOperation}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitBinaryOperation(Mg4jEqlParser.BinaryOperationContext ctx);

    /**
     * Enter a parse tree produced by the {@code unaryOperation}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterUnaryOperation(Mg4jEqlParser.UnaryOperationContext ctx);

    /**
     * Exit a parse tree produced by the {@code unaryOperation}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitUnaryOperation(Mg4jEqlParser.UnaryOperationContext ctx);

    /**
     * Enter a parse tree produced by the {@code word}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterWord(Mg4jEqlParser.WordContext ctx);

    /**
     * Exit a parse tree produced by the {@code word}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitWord(Mg4jEqlParser.WordContext ctx);

    /**
     * Enter a parse tree produced by the {@code par}
     * labeled alternative in {@link Mg4jEqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void enterPar(Mg4jEqlParser.ParContext ctx);

    /**
     * Exit a parse tree produced by the {@code par}
     * labeled alternative in {@link Mg4jEqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void exitPar(Mg4jEqlParser.ParContext ctx);

    /**
     * Enter a parse tree produced by the {@code sent}
     * labeled alternative in {@link Mg4jEqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void enterSent(Mg4jEqlParser.SentContext ctx);

    /**
     * Exit a parse tree produced by the {@code sent}
     * labeled alternative in {@link Mg4jEqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void exitSent(Mg4jEqlParser.SentContext ctx);

    /**
     * Enter a parse tree produced by the {@code proximity}
     * labeled alternative in {@link Mg4jEqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void enterProximity(Mg4jEqlParser.ProximityContext ctx);

    /**
     * Exit a parse tree produced by the {@code proximity}
     * labeled alternative in {@link Mg4jEqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void exitProximity(Mg4jEqlParser.ProximityContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#attribute}.
     *
     * @param ctx the parse tree
     */
    void enterAttribute(Mg4jEqlParser.AttributeContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#attribute}.
     *
     * @param ctx the parse tree
     */
    void exitAttribute(Mg4jEqlParser.AttributeContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#constraint}.
     *
     * @param ctx the parse tree
     */
    void enterConstraint(Mg4jEqlParser.ConstraintContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#constraint}.
     *
     * @param ctx the parse tree
     */
    void exitConstraint(Mg4jEqlParser.ConstraintContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#reference}.
     *
     * @param ctx the parse tree
     */
    void enterReference(Mg4jEqlParser.ReferenceContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#reference}.
     *
     * @param ctx the parse tree
     */
    void exitReference(Mg4jEqlParser.ReferenceContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#relOp}.
     *
     * @param ctx the parse tree
     */
    void enterRelOp(Mg4jEqlParser.RelOpContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#relOp}.
     *
     * @param ctx the parse tree
     */
    void exitRelOp(Mg4jEqlParser.RelOpContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#binaryOp}.
     *
     * @param ctx the parse tree
     */
    void enterBinaryOp(Mg4jEqlParser.BinaryOpContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#binaryOp}.
     *
     * @param ctx the parse tree
     */
    void exitBinaryOp(Mg4jEqlParser.BinaryOpContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#unaryOp}.
     *
     * @param ctx the parse tree
     */
    void enterUnaryOp(Mg4jEqlParser.UnaryOpContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#unaryOp}.
     *
     * @param ctx the parse tree
     */
    void exitUnaryOp(Mg4jEqlParser.UnaryOpContext ctx);
}