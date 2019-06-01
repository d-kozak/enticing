// Generated from /home/dkozak/projects/knot/enticing/eql-compiler/src/main/resources/Eql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.eql.compiler.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link EqlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface EqlVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link EqlParser#root}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRoot(EqlParser.RootContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#query}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQuery(EqlParser.QueryContext ctx);

    /**
     * Visit a parse tree produced by the {@code indexWithMultipleValues}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexWithMultipleValues(EqlParser.IndexWithMultipleValuesContext ctx);

    /**
     * Visit a parse tree produced by the {@code sequence}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSequence(EqlParser.SequenceContext ctx);

    /**
     * Visit a parse tree produced by the {@code paren}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParen(EqlParser.ParenContext ctx);

    /**
     * Visit a parse tree produced by the {@code indexWithSingleValue}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexWithSingleValue(EqlParser.IndexWithSingleValueContext ctx);

    /**
     * Visit a parse tree produced by the {@code binaryOperation}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBinaryOperation(EqlParser.BinaryOperationContext ctx);

    /**
     * Visit a parse tree produced by the {@code unaryOperation}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUnaryOperation(EqlParser.UnaryOperationContext ctx);

    /**
     * Visit a parse tree produced by the {@code order}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOrder(EqlParser.OrderContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#proximity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProximity(EqlParser.ProximityContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#alignOperator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAlignOperator(EqlParser.AlignOperatorContext ctx);

    /**
     * Visit a parse tree produced by the {@code index}
     * labeled alternative in {@link EqlParser#alignElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndex(EqlParser.IndexContext ctx);

    /**
     * Visit a parse tree produced by the {@code nertag}
     * labeled alternative in {@link EqlParser#alignElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNertag(EqlParser.NertagContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignment(EqlParser.AssignmentContext ctx);

    /**
     * Visit a parse tree produced by the {@code par}
     * labeled alternative in {@link EqlParser#contextConstraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPar(EqlParser.ParContext ctx);

    /**
     * Visit a parse tree produced by the {@code sent}
     * labeled alternative in {@link EqlParser#contextConstraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSent(EqlParser.SentContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#indexOperator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexOperator(EqlParser.IndexOperatorContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#queryLiteral}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQueryLiteral(EqlParser.QueryLiteralContext ctx);

    /**
     * Visit a parse tree produced by the {@code numberRange}
     * labeled alternative in {@link EqlParser#interval}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNumberRange(EqlParser.NumberRangeContext ctx);

    /**
     * Visit a parse tree produced by the {@code dateRange}
     * labeled alternative in {@link EqlParser#interval}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDateRange(EqlParser.DateRangeContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#constraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitConstraint(EqlParser.ConstraintContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#reference}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitReference(EqlParser.ReferenceContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#comparisonOperator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComparisonOperator(EqlParser.ComparisonOperatorContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#binaryOperator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBinaryOperator(EqlParser.BinaryOperatorContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#unaryOperator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUnaryOperator(EqlParser.UnaryOperatorContext ctx);
}