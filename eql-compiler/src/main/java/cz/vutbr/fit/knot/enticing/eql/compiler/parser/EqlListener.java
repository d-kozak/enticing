// Generated from /home/dkozak/projects/knot/enticing/eql-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/eql/compiler/parser/Eql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.eql.compiler.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EqlParser}.
 */
public interface EqlListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link EqlParser#root}.
     *
     * @param ctx the parse tree
     */
    void enterRoot(EqlParser.RootContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#root}.
     *
     * @param ctx the parse tree
     */
    void exitRoot(EqlParser.RootContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#query}.
     *
     * @param ctx the parse tree
     */
    void enterQuery(EqlParser.QueryContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#query}.
     *
     * @param ctx the parse tree
     */
    void exitQuery(EqlParser.QueryContext ctx);

    /**
     * Enter a parse tree produced by the {@code sequence}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void enterSequence(EqlParser.SequenceContext ctx);

    /**
     * Exit a parse tree produced by the {@code sequence}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void exitSequence(EqlParser.SequenceContext ctx);

    /**
     * Enter a parse tree produced by the {@code paren}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void enterParen(EqlParser.ParenContext ctx);

    /**
     * Exit a parse tree produced by the {@code paren}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void exitParen(EqlParser.ParenContext ctx);

    /**
     * Enter a parse tree produced by the {@code binaryOperation}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void enterBinaryOperation(EqlParser.BinaryOperationContext ctx);

    /**
     * Exit a parse tree produced by the {@code binaryOperation}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void exitBinaryOperation(EqlParser.BinaryOperationContext ctx);

    /**
     * Enter a parse tree produced by the {@code unaryOperation}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void enterUnaryOperation(EqlParser.UnaryOperationContext ctx);

    /**
     * Exit a parse tree produced by the {@code unaryOperation}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void exitUnaryOperation(EqlParser.UnaryOperationContext ctx);

    /**
     * Enter a parse tree produced by the {@code literal}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void enterLiteral(EqlParser.LiteralContext ctx);

    /**
     * Exit a parse tree produced by the {@code literal}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void exitLiteral(EqlParser.LiteralContext ctx);

    /**
     * Enter a parse tree produced by the {@code order}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void enterOrder(EqlParser.OrderContext ctx);

    /**
     * Exit a parse tree produced by the {@code order}
     * labeled alternative in {@link EqlParser#queryExpression}.
     *
     * @param ctx the parse tree
     */
    void exitOrder(EqlParser.OrderContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#alignOperator}.
     *
     * @param ctx the parse tree
     */
    void enterAlignOperator(EqlParser.AlignOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#alignOperator}.
     *
     * @param ctx the parse tree
     */
    void exitAlignOperator(EqlParser.AlignOperatorContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#assignment}.
     *
     * @param ctx the parse tree
     */
    void enterAssignment(EqlParser.AssignmentContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#assignment}.
     *
     * @param ctx the parse tree
     */
    void exitAssignment(EqlParser.AssignmentContext ctx);

    /**
     * Enter a parse tree produced by the {@code par}
     * labeled alternative in {@link EqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void enterPar(EqlParser.ParContext ctx);

    /**
     * Exit a parse tree produced by the {@code par}
     * labeled alternative in {@link EqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void exitPar(EqlParser.ParContext ctx);

    /**
     * Enter a parse tree produced by the {@code sent}
     * labeled alternative in {@link EqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void enterSent(EqlParser.SentContext ctx);

    /**
     * Exit a parse tree produced by the {@code sent}
     * labeled alternative in {@link EqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void exitSent(EqlParser.SentContext ctx);

    /**
     * Enter a parse tree produced by the {@code proximity}
     * labeled alternative in {@link EqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void enterProximity(EqlParser.ProximityContext ctx);

    /**
     * Exit a parse tree produced by the {@code proximity}
     * labeled alternative in {@link EqlParser#limitation}.
     *
     * @param ctx the parse tree
     */
    void exitProximity(EqlParser.ProximityContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#indexOperator}.
     *
     * @param ctx the parse tree
     */
    void enterIndexOperator(EqlParser.IndexOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#indexOperator}.
     *
     * @param ctx the parse tree
     */
    void exitIndexOperator(EqlParser.IndexOperatorContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#queryLiteral}.
     *
     * @param ctx the parse tree
     */
    void enterQueryLiteral(EqlParser.QueryLiteralContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#queryLiteral}.
     *
     * @param ctx the parse tree
     */
    void exitQueryLiteral(EqlParser.QueryLiteralContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#constraint}.
     *
     * @param ctx the parse tree
     */
    void enterConstraint(EqlParser.ConstraintContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#constraint}.
     *
     * @param ctx the parse tree
     */
    void exitConstraint(EqlParser.ConstraintContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#reference}.
     *
     * @param ctx the parse tree
     */
    void enterReference(EqlParser.ReferenceContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#reference}.
     *
     * @param ctx the parse tree
     */
    void exitReference(EqlParser.ReferenceContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#comparisonOperator}.
     *
     * @param ctx the parse tree
     */
    void enterComparisonOperator(EqlParser.ComparisonOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#comparisonOperator}.
     *
     * @param ctx the parse tree
     */
    void exitComparisonOperator(EqlParser.ComparisonOperatorContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#binaryOperator}.
     *
     * @param ctx the parse tree
     */
    void enterBinaryOperator(EqlParser.BinaryOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#binaryOperator}.
     *
     * @param ctx the parse tree
     */
    void exitBinaryOperator(EqlParser.BinaryOperatorContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#unaryOperator}.
     *
     * @param ctx the parse tree
     */
    void enterUnaryOperator(EqlParser.UnaryOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#unaryOperator}.
     *
     * @param ctx the parse tree
     */
    void exitUnaryOperator(EqlParser.UnaryOperatorContext ctx);
}