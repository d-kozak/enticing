// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/parser/Mg4jEql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Mg4jEqlParser}.
 */
public interface Mg4jEqlListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#root}.
     *
     * @param ctx the parse tree
     */
    void enterRoot(Mg4jEqlParser.RootContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#root}.
     *
     * @param ctx the parse tree
     */
    void exitRoot(Mg4jEqlParser.RootContext ctx);

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
     * Enter a parse tree produced by the {@code lit}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterLit(Mg4jEqlParser.LitContext ctx);

    /**
     * Exit a parse tree produced by the {@code lit}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitLit(Mg4jEqlParser.LitContext ctx);

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
     * Enter a parse tree produced by the {@code order}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterOrder(Mg4jEqlParser.OrderContext ctx);

    /**
     * Exit a parse tree produced by the {@code order}
     * labeled alternative in {@link Mg4jEqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitOrder(Mg4jEqlParser.OrderContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#literal}.
     *
     * @param ctx the parse tree
     */
    void enterLiteral(Mg4jEqlParser.LiteralContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#literal}.
     *
     * @param ctx the parse tree
     */
    void exitLiteral(Mg4jEqlParser.LiteralContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#align}.
     *
     * @param ctx the parse tree
     */
    void enterAlign(Mg4jEqlParser.AlignContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#align}.
     *
     * @param ctx the parse tree
     */
    void exitAlign(Mg4jEqlParser.AlignContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#identifier}.
     *
     * @param ctx the parse tree
     */
    void enterIdentifier(Mg4jEqlParser.IdentifierContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#identifier}.
     *
     * @param ctx the parse tree
     */
    void exitIdentifier(Mg4jEqlParser.IdentifierContext ctx);

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
     * Enter a parse tree produced by {@link Mg4jEqlParser#indexOperator}.
     *
     * @param ctx the parse tree
     */
    void enterIndexOperator(Mg4jEqlParser.IndexOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#indexOperator}.
     *
     * @param ctx the parse tree
     */
    void exitIndexOperator(Mg4jEqlParser.IndexOperatorContext ctx);

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
     * Enter a parse tree produced by {@link Mg4jEqlParser#binaryOperator}.
     *
     * @param ctx the parse tree
     */
    void enterBinaryOperator(Mg4jEqlParser.BinaryOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#binaryOperator}.
     *
     * @param ctx the parse tree
     */
    void exitBinaryOperator(Mg4jEqlParser.BinaryOperatorContext ctx);

    /**
     * Enter a parse tree produced by {@link Mg4jEqlParser#unaryOperator}.
     *
     * @param ctx the parse tree
     */
    void enterUnaryOperator(Mg4jEqlParser.UnaryOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link Mg4jEqlParser#unaryOperator}.
     *
     * @param ctx the parse tree
     */
    void exitUnaryOperator(Mg4jEqlParser.UnaryOperatorContext ctx);
}