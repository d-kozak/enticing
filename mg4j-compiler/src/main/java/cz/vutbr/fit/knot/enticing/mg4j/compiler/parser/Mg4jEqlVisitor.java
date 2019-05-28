// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/parser/Mg4jEql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Mg4jEqlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Mg4jEqlVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(Mg4jEqlParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(Mg4jEqlParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#queryPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPart(Mg4jEqlParser.QueryPartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sequence}
	 * labeled alternative in {@link Mg4jEqlParser#queryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSequence(Mg4jEqlParser.SequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code paren}
	 * labeled alternative in {@link Mg4jEqlParser#queryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParen(Mg4jEqlParser.ParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lit}
	 * labeled alternative in {@link Mg4jEqlParser#queryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLit(Mg4jEqlParser.LitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryOperation}
	 * labeled alternative in {@link Mg4jEqlParser#queryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOperation(Mg4jEqlParser.BinaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryOperation}
	 * labeled alternative in {@link Mg4jEqlParser#queryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperation(Mg4jEqlParser.UnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code order}
	 * labeled alternative in {@link Mg4jEqlParser#queryCore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrder(Mg4jEqlParser.OrderContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(Mg4jEqlParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#alignOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlignOperator(Mg4jEqlParser.AlignOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(Mg4jEqlParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code par}
	 * labeled alternative in {@link Mg4jEqlParser#limitation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPar(Mg4jEqlParser.ParContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sent}
	 * labeled alternative in {@link Mg4jEqlParser#limitation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSent(Mg4jEqlParser.SentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code proximity}
	 * labeled alternative in {@link Mg4jEqlParser#limitation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProximity(Mg4jEqlParser.ProximityContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#indexOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexOperator(Mg4jEqlParser.IndexOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#constraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraint(Mg4jEqlParser.ConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#reference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReference(Mg4jEqlParser.ReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#relOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelOp(Mg4jEqlParser.RelOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#binaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOperator(Mg4jEqlParser.BinaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Mg4jEqlParser#unaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(Mg4jEqlParser.UnaryOperatorContext ctx);
}