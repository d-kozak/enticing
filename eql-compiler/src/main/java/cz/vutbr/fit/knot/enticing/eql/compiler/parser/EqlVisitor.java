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
     * Visit a parse tree produced by the {@code logicUnaryOperation}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLogicUnaryOperation(EqlParser.LogicUnaryOperationContext ctx);

    /**
     * Visit a parse tree produced by the {@code indexElem}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIndexElem(EqlParser.IndexElemContext ctx);

    /**
     * Visit a parse tree produced by the {@code logicBinaryOperation}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLogicBinaryOperation(EqlParser.LogicBinaryOperationContext ctx);

    /**
     * Visit a parse tree produced by the {@code literal}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLiteral(EqlParser.LiteralContext ctx);

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
     * Visit a parse tree produced by the {@code entityAttribute}
     * labeled alternative in {@link EqlParser#alignElem}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEntityAttribute(EqlParser.EntityAttributeContext ctx);

    /**
     * Visit a parse tree produced by the {@code singleValue}
     * labeled alternative in {@link EqlParser#singleValueOrMultiple}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSingleValue(EqlParser.SingleValueContext ctx);

    /**
     * Visit a parse tree produced by the {@code multipleValues}
     * labeled alternative in {@link EqlParser#singleValueOrMultiple}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMultipleValues(EqlParser.MultipleValuesContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#literalOrInterval}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLiteralOrInterval(EqlParser.LiteralOrIntervalContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignment(EqlParser.AssignmentContext ctx);

    /**
     * Visit a parse tree produced by the {@code paragraph}
     * labeled alternative in {@link EqlParser#contextConstraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParagraph(EqlParser.ParagraphContext ctx);

    /**
     * Visit a parse tree produced by the {@code sentence}
     * labeled alternative in {@link EqlParser#contextConstraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSentence(EqlParser.SentenceContext ctx);

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
     * Visit a parse tree produced by the {@code parens}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParens(EqlParser.ParensContext ctx);

    /**
     * Visit a parse tree produced by the {@code comparison}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComparison(EqlParser.ComparisonContext ctx);

    /**
     * Visit a parse tree produced by the {@code constraintLogicUnaryOperation}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitConstraintLogicUnaryOperation(EqlParser.ConstraintLogicUnaryOperationContext ctx);

    /**
     * Visit a parse tree produced by the {@code constraintLogicBinaryOperation}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitConstraintLogicBinaryOperation(EqlParser.ConstraintLogicBinaryOperationContext ctx);

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
     * Visit a parse tree produced by {@link EqlParser#logicBinaryOperator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLogicBinaryOperator(EqlParser.LogicBinaryOperatorContext ctx);

    /**
     * Visit a parse tree produced by {@link EqlParser#logicUnaryOperator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLogicUnaryOperator(EqlParser.LogicUnaryOperatorContext ctx);
}