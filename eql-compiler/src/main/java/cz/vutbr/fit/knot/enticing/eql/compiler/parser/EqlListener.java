// Generated from /home/dkozak/projects/knot/enticing/eql-compiler/src/main/resources/Eql.g4 by ANTLR 4.7.2
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
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterSequence(EqlParser.SequenceContext ctx);

    /**
     * Exit a parse tree produced by the {@code sequence}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitSequence(EqlParser.SequenceContext ctx);

    /**
     * Enter a parse tree produced by the {@code paren}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterParen(EqlParser.ParenContext ctx);

    /**
     * Exit a parse tree produced by the {@code paren}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitParen(EqlParser.ParenContext ctx);

    /**
     * Enter a parse tree produced by the {@code logicUnaryOperation}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterLogicUnaryOperation(EqlParser.LogicUnaryOperationContext ctx);

    /**
     * Exit a parse tree produced by the {@code logicUnaryOperation}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitLogicUnaryOperation(EqlParser.LogicUnaryOperationContext ctx);

    /**
     * Enter a parse tree produced by the {@code indexElem}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterIndexElem(EqlParser.IndexElemContext ctx);

    /**
     * Exit a parse tree produced by the {@code indexElem}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitIndexElem(EqlParser.IndexElemContext ctx);

    /**
     * Enter a parse tree produced by the {@code logicBinaryOperation}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterLogicBinaryOperation(EqlParser.LogicBinaryOperationContext ctx);

    /**
     * Exit a parse tree produced by the {@code logicBinaryOperation}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitLogicBinaryOperation(EqlParser.LogicBinaryOperationContext ctx);

    /**
     * Enter a parse tree produced by the {@code literal}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterLiteral(EqlParser.LiteralContext ctx);

    /**
     * Exit a parse tree produced by the {@code literal}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitLiteral(EqlParser.LiteralContext ctx);

    /**
     * Enter a parse tree produced by the {@code order}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void enterOrder(EqlParser.OrderContext ctx);

    /**
     * Exit a parse tree produced by the {@code order}
     * labeled alternative in {@link EqlParser#queryElem}.
     *
     * @param ctx the parse tree
     */
    void exitOrder(EqlParser.OrderContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#proximity}.
     *
     * @param ctx the parse tree
     */
    void enterProximity(EqlParser.ProximityContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#proximity}.
     *
     * @param ctx the parse tree
     */
    void exitProximity(EqlParser.ProximityContext ctx);

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
     * Enter a parse tree produced by the {@code index}
     * labeled alternative in {@link EqlParser#alignElem}.
     *
     * @param ctx the parse tree
     */
    void enterIndex(EqlParser.IndexContext ctx);

    /**
     * Exit a parse tree produced by the {@code index}
     * labeled alternative in {@link EqlParser#alignElem}.
     *
     * @param ctx the parse tree
     */
    void exitIndex(EqlParser.IndexContext ctx);

    /**
     * Enter a parse tree produced by the {@code entityAttribute}
     * labeled alternative in {@link EqlParser#alignElem}.
     *
     * @param ctx the parse tree
     */
    void enterEntityAttribute(EqlParser.EntityAttributeContext ctx);

    /**
     * Exit a parse tree produced by the {@code entityAttribute}
     * labeled alternative in {@link EqlParser#alignElem}.
     *
     * @param ctx the parse tree
     */
    void exitEntityAttribute(EqlParser.EntityAttributeContext ctx);

    /**
     * Enter a parse tree produced by the {@code singleValue}
     * labeled alternative in {@link EqlParser#singleValueOrMultiple}.
     *
     * @param ctx the parse tree
     */
    void enterSingleValue(EqlParser.SingleValueContext ctx);

    /**
     * Exit a parse tree produced by the {@code singleValue}
     * labeled alternative in {@link EqlParser#singleValueOrMultiple}.
     *
     * @param ctx the parse tree
     */
    void exitSingleValue(EqlParser.SingleValueContext ctx);

    /**
     * Enter a parse tree produced by the {@code multipleValues}
     * labeled alternative in {@link EqlParser#singleValueOrMultiple}.
     *
     * @param ctx the parse tree
     */
    void enterMultipleValues(EqlParser.MultipleValuesContext ctx);

    /**
     * Exit a parse tree produced by the {@code multipleValues}
     * labeled alternative in {@link EqlParser#singleValueOrMultiple}.
     *
     * @param ctx the parse tree
     */
    void exitMultipleValues(EqlParser.MultipleValuesContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#literalOrInterval}.
     *
     * @param ctx the parse tree
     */
    void enterLiteralOrInterval(EqlParser.LiteralOrIntervalContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#literalOrInterval}.
     *
     * @param ctx the parse tree
     */
    void exitLiteralOrInterval(EqlParser.LiteralOrIntervalContext ctx);

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
     * Enter a parse tree produced by the {@code paragraph}
     * labeled alternative in {@link EqlParser#contextConstraint}.
     *
     * @param ctx the parse tree
     */
    void enterParagraph(EqlParser.ParagraphContext ctx);

    /**
     * Exit a parse tree produced by the {@code paragraph}
     * labeled alternative in {@link EqlParser#contextConstraint}.
     *
     * @param ctx the parse tree
     */
    void exitParagraph(EqlParser.ParagraphContext ctx);

    /**
     * Enter a parse tree produced by the {@code sentence}
     * labeled alternative in {@link EqlParser#contextConstraint}.
     *
     * @param ctx the parse tree
     */
    void enterSentence(EqlParser.SentenceContext ctx);

    /**
     * Exit a parse tree produced by the {@code sentence}
     * labeled alternative in {@link EqlParser#contextConstraint}.
     *
     * @param ctx the parse tree
     */
    void exitSentence(EqlParser.SentenceContext ctx);

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
     * Enter a parse tree produced by the {@code numberRange}
     * labeled alternative in {@link EqlParser#interval}.
     *
     * @param ctx the parse tree
     */
    void enterNumberRange(EqlParser.NumberRangeContext ctx);

    /**
     * Exit a parse tree produced by the {@code numberRange}
     * labeled alternative in {@link EqlParser#interval}.
     *
     * @param ctx the parse tree
     */
    void exitNumberRange(EqlParser.NumberRangeContext ctx);

    /**
     * Enter a parse tree produced by the {@code dateRange}
     * labeled alternative in {@link EqlParser#interval}.
     *
     * @param ctx the parse tree
     */
    void enterDateRange(EqlParser.DateRangeContext ctx);

    /**
     * Exit a parse tree produced by the {@code dateRange}
     * labeled alternative in {@link EqlParser#interval}.
     *
     * @param ctx the parse tree
     */
    void exitDateRange(EqlParser.DateRangeContext ctx);

    /**
     * Enter a parse tree produced by the {@code parens}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     */
    void enterParens(EqlParser.ParensContext ctx);

    /**
     * Exit a parse tree produced by the {@code parens}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     */
    void exitParens(EqlParser.ParensContext ctx);

    /**
     * Enter a parse tree produced by the {@code comparison}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     */
    void enterComparison(EqlParser.ComparisonContext ctx);

    /**
     * Exit a parse tree produced by the {@code comparison}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     */
    void exitComparison(EqlParser.ComparisonContext ctx);

    /**
     * Enter a parse tree produced by the {@code constraintLogicUnaryOperation}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     */
    void enterConstraintLogicUnaryOperation(EqlParser.ConstraintLogicUnaryOperationContext ctx);

    /**
     * Exit a parse tree produced by the {@code constraintLogicUnaryOperation}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     */
    void exitConstraintLogicUnaryOperation(EqlParser.ConstraintLogicUnaryOperationContext ctx);

    /**
     * Enter a parse tree produced by the {@code constraintLogicBinaryOperation}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     */
    void enterConstraintLogicBinaryOperation(EqlParser.ConstraintLogicBinaryOperationContext ctx);

    /**
     * Exit a parse tree produced by the {@code constraintLogicBinaryOperation}
     * labeled alternative in {@link EqlParser#globalConstraint}.
     *
     * @param ctx the parse tree
     */
    void exitConstraintLogicBinaryOperation(EqlParser.ConstraintLogicBinaryOperationContext ctx);

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
     * Enter a parse tree produced by {@link EqlParser#logicBinaryOperator}.
     *
     * @param ctx the parse tree
     */
    void enterLogicBinaryOperator(EqlParser.LogicBinaryOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#logicBinaryOperator}.
     *
     * @param ctx the parse tree
     */
    void exitLogicBinaryOperator(EqlParser.LogicBinaryOperatorContext ctx);

    /**
     * Enter a parse tree produced by {@link EqlParser#logicUnaryOperator}.
     *
     * @param ctx the parse tree
     */
    void enterLogicUnaryOperator(EqlParser.LogicUnaryOperatorContext ctx);

    /**
     * Exit a parse tree produced by {@link EqlParser#logicUnaryOperator}.
     *
     * @param ctx the parse tree
     */
    void exitLogicUnaryOperator(EqlParser.LogicUnaryOperatorContext ctx);
}