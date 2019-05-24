// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/Mg4j.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Mg4jParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface Mg4jVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link Mg4jParser#query}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQuery(Mg4jParser.QueryContext ctx);

    /**
     * Visit a parse tree produced by {@link Mg4jParser#node}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNode(Mg4jParser.NodeContext ctx);

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
     * Visit a parse tree produced by {@link Mg4jParser#boolOp}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBoolOp(Mg4jParser.BoolOpContext ctx);

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