// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/parser/Mg4jEql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Mg4jEqlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ARROW=1, MINUS=2, COLON=3, EXPONENT=4, SIMILARITY=5, SENT=6, PAR=7, QUOTATION=8, 
		QUERY_CONSTRAINT_SEPARATOR=9, EQ=10, NEQ=11, LT=12, LE=13, GT=14, GE=15, 
		PAREN_LEFT=16, PAREN_RIGHT=17, DOT=18, OR=19, AND=20, NOT=21, NUMBER=22, 
		WORD=23, WS=24;
	public static final int
		RULE_root = 0, RULE_query = 1, RULE_queryElem = 2, RULE_queryCore = 3, 
		RULE_literal = 4, RULE_alignOperator = 5, RULE_assignment = 6, RULE_limitation = 7, 
		RULE_indexOperator = 8, RULE_constraint = 9, RULE_reference = 10, RULE_comparisonOperator = 11, 
		RULE_binaryOperator = 12, RULE_unaryOperator = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"root", "query", "queryElem", "queryCore", "literal", "alignOperator", 
			"assignment", "limitation", "indexOperator", "constraint", "reference", 
			"comparisonOperator", "binaryOperator", "unaryOperator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<-'", "'-'", "':'", "'^'", "'~'", "'_SENT_'", "'_PAR_'", "'\"'", 
			"'&&'", "'='", "'!='", "'<'", "'<='", "'>'", "'>='", "'('", "')'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ARROW", "MINUS", "COLON", "EXPONENT", "SIMILARITY", "SENT", "PAR", 
			"QUOTATION", "QUERY_CONSTRAINT_SEPARATOR", "EQ", "NEQ", "LT", "LE", "GT", 
			"GE", "PAREN_LEFT", "PAREN_RIGHT", "DOT", "OR", "AND", "NOT", "NUMBER", 
			"WORD", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Mg4jEql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Mg4jEqlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class RootContext extends ParserRuleContext {
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public TerminalNode EOF() { return getToken(Mg4jEqlParser.EOF, 0); }
		public TerminalNode QUERY_CONSTRAINT_SEPARATOR() { return getToken(Mg4jEqlParser.QUERY_CONSTRAINT_SEPARATOR, 0); }
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitRoot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitRoot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			query();
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==QUERY_CONSTRAINT_SEPARATOR) {
				{
				setState(29);
				match(QUERY_CONSTRAINT_SEPARATOR);
				setState(30);
				constraint(0);
				}
			}

			setState(33);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryContext extends ParserRuleContext {
		public List<QueryElemContext> queryElem() {
			return getRuleContexts(QueryElemContext.class);
		}
		public QueryElemContext queryElem(int i) {
			return getRuleContext(QueryElemContext.class,i);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(35);
				queryElem();
				}
				}
				setState(38); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << NOT) | (1L << NUMBER) | (1L << WORD))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryElemContext extends ParserRuleContext {
		public QueryCoreContext queryCore() {
			return getRuleContext(QueryCoreContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public AlignOperatorContext alignOperator() {
			return getRuleContext(AlignOperatorContext.class,0);
		}
		public LimitationContext limitation() {
			return getRuleContext(LimitationContext.class,0);
		}
		public QueryElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryElem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterQueryElem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitQueryElem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitQueryElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryElemContext queryElem() throws RecognitionException {
		QueryElemContext _localctx = new QueryElemContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_queryElem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(40);
				assignment();
				}
				break;
			}
			setState(43);
			queryCore(0);
			setState(45);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(44);
				alignOperator();
				}
				break;
			}
			setState(48);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(47);
				limitation();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryCoreContext extends ParserRuleContext {
		public QueryCoreContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryCore; }
	 
		public QueryCoreContext() { }
		public void copyFrom(QueryCoreContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SequenceContext extends QueryCoreContext {
		public List<TerminalNode> QUOTATION() { return getTokens(Mg4jEqlParser.QUOTATION); }
		public TerminalNode QUOTATION(int i) {
			return getToken(Mg4jEqlParser.QUOTATION, i);
		}
		public List<QueryElemContext> queryElem() {
			return getRuleContexts(QueryElemContext.class);
		}
		public QueryElemContext queryElem(int i) {
			return getRuleContext(QueryElemContext.class,i);
		}
		public SequenceContext(QueryCoreContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitSequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitSequence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenContext extends QueryCoreContext {
		public TerminalNode PAREN_LEFT() { return getToken(Mg4jEqlParser.PAREN_LEFT, 0); }
		public TerminalNode PAREN_RIGHT() { return getToken(Mg4jEqlParser.PAREN_RIGHT, 0); }
		public List<QueryElemContext> queryElem() {
			return getRuleContexts(QueryElemContext.class);
		}
		public QueryElemContext queryElem(int i) {
			return getRuleContext(QueryElemContext.class,i);
		}
		public ParenContext(QueryCoreContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterParen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitParen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitParen(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LitContext extends QueryCoreContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public IndexOperatorContext indexOperator() {
			return getRuleContext(IndexOperatorContext.class,0);
		}
		public LitContext(QueryCoreContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitLit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitLit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryOperationContext extends QueryCoreContext {
		public QueryCoreContext queryCore() {
			return getRuleContext(QueryCoreContext.class,0);
		}
		public BinaryOperatorContext binaryOperator() {
			return getRuleContext(BinaryOperatorContext.class,0);
		}
		public QueryElemContext queryElem() {
			return getRuleContext(QueryElemContext.class,0);
		}
		public BinaryOperationContext(QueryCoreContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterBinaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitBinaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitBinaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryOperationContext extends QueryCoreContext {
		public UnaryOperatorContext unaryOperator() {
			return getRuleContext(UnaryOperatorContext.class,0);
		}
		public QueryElemContext queryElem() {
			return getRuleContext(QueryElemContext.class,0);
		}
		public UnaryOperationContext(QueryCoreContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterUnaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitUnaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitUnaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrderContext extends QueryCoreContext {
		public QueryCoreContext queryCore() {
			return getRuleContext(QueryCoreContext.class,0);
		}
		public TerminalNode LT() { return getToken(Mg4jEqlParser.LT, 0); }
		public QueryElemContext queryElem() {
			return getRuleContext(QueryElemContext.class,0);
		}
		public OrderContext(QueryCoreContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterOrder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitOrder(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitOrder(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryCoreContext queryCore() throws RecognitionException {
		return queryCore(0);
	}

	private QueryCoreContext queryCore(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		QueryCoreContext _localctx = new QueryCoreContext(_ctx, _parentState);
		QueryCoreContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_queryCore, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QUOTATION:
				{
				_localctx = new SequenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(51);
				match(QUOTATION);
				setState(53); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(52);
						queryElem();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(55); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(57);
				match(QUOTATION);
				}
				break;
			case NUMBER:
			case WORD:
				{
				_localctx = new LitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(60);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(59);
					indexOperator();
					}
					break;
				}
				setState(62);
				literal();
				}
				break;
			case PAREN_LEFT:
				{
				_localctx = new ParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(63);
				match(PAREN_LEFT);
				setState(65); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(64);
					queryElem();
					}
					}
					setState(67); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << NOT) | (1L << NUMBER) | (1L << WORD))) != 0) );
				setState(69);
				match(PAREN_RIGHT);
				}
				break;
			case NOT:
				{
				_localctx = new UnaryOperationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(71);
				unaryOperator();
				setState(72);
				queryElem();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(85);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(83);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new OrderContext(new QueryCoreContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_queryCore);
						setState(76);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(77);
						match(LT);
						setState(78);
						queryElem();
						}
						break;
					case 2:
						{
						_localctx = new BinaryOperationContext(new QueryCoreContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_queryCore);
						setState(79);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(80);
						binaryOperator();
						setState(81);
						queryElem();
						}
						break;
					}
					} 
				}
				setState(87);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(Mg4jEqlParser.WORD, 0); }
		public TerminalNode NUMBER() { return getToken(Mg4jEqlParser.NUMBER, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			_la = _input.LA(1);
			if ( !(_la==NUMBER || _la==WORD) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlignOperatorContext extends ParserRuleContext {
		public TerminalNode EXPONENT() { return getToken(Mg4jEqlParser.EXPONENT, 0); }
		public QueryElemContext queryElem() {
			return getRuleContext(QueryElemContext.class,0);
		}
		public AlignOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alignOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterAlignOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitAlignOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitAlignOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlignOperatorContext alignOperator() throws RecognitionException {
		AlignOperatorContext _localctx = new AlignOperatorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_alignOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(EXPONENT);
			setState(91);
			queryElem();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(Mg4jEqlParser.WORD, 0); }
		public TerminalNode ARROW() { return getToken(Mg4jEqlParser.ARROW, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(WORD);
			setState(94);
			match(ARROW);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LimitationContext extends ParserRuleContext {
		public LimitationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limitation; }
	 
		public LimitationContext() { }
		public void copyFrom(LimitationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParContext extends LimitationContext {
		public TerminalNode MINUS() { return getToken(Mg4jEqlParser.MINUS, 0); }
		public TerminalNode PAR() { return getToken(Mg4jEqlParser.PAR, 0); }
		public ParContext(LimitationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterPar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitPar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitPar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ProximityContext extends LimitationContext {
		public TerminalNode SIMILARITY() { return getToken(Mg4jEqlParser.SIMILARITY, 0); }
		public TerminalNode NUMBER() { return getToken(Mg4jEqlParser.NUMBER, 0); }
		public ProximityContext(LimitationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterProximity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitProximity(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitProximity(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SentContext extends LimitationContext {
		public TerminalNode MINUS() { return getToken(Mg4jEqlParser.MINUS, 0); }
		public TerminalNode SENT() { return getToken(Mg4jEqlParser.SENT, 0); }
		public SentContext(LimitationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterSent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitSent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitSent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LimitationContext limitation() throws RecognitionException {
		LimitationContext _localctx = new LimitationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_limitation);
		try {
			setState(102);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new ParContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				match(MINUS);
				setState(97);
				match(PAR);
				}
				break;
			case 2:
				_localctx = new SentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(98);
				match(MINUS);
				setState(99);
				match(SENT);
				}
				break;
			case 3:
				_localctx = new ProximityContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(100);
				match(SIMILARITY);
				setState(101);
				match(NUMBER);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexOperatorContext extends ParserRuleContext {
		public List<TerminalNode> WORD() { return getTokens(Mg4jEqlParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(Mg4jEqlParser.WORD, i);
		}
		public TerminalNode COLON() { return getToken(Mg4jEqlParser.COLON, 0); }
		public List<TerminalNode> DOT() { return getTokens(Mg4jEqlParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Mg4jEqlParser.DOT, i);
		}
		public IndexOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterIndexOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitIndexOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitIndexOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexOperatorContext indexOperator() throws RecognitionException {
		IndexOperatorContext _localctx = new IndexOperatorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_indexOperator);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(104);
					match(WORD);
					setState(105);
					match(DOT);
					}
					} 
				}
				setState(110);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(111);
			match(WORD);
			setState(112);
			match(COLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintContext extends ParserRuleContext {
		public TerminalNode PAREN_LEFT() { return getToken(Mg4jEqlParser.PAREN_LEFT, 0); }
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public TerminalNode PAREN_RIGHT() { return getToken(Mg4jEqlParser.PAREN_RIGHT, 0); }
		public List<ReferenceContext> reference() {
			return getRuleContexts(ReferenceContext.class);
		}
		public ReferenceContext reference(int i) {
			return getRuleContext(ReferenceContext.class,i);
		}
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public TerminalNode WORD() { return getToken(Mg4jEqlParser.WORD, 0); }
		public UnaryOperatorContext unaryOperator() {
			return getRuleContext(UnaryOperatorContext.class,0);
		}
		public BinaryOperatorContext binaryOperator() {
			return getRuleContext(BinaryOperatorContext.class,0);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		return constraint(0);
	}

	private ConstraintContext constraint(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConstraintContext _localctx = new ConstraintContext(_ctx, _parentState);
		ConstraintContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_constraint, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(115);
				match(PAREN_LEFT);
				setState(116);
				constraint(0);
				setState(117);
				match(PAREN_RIGHT);
				}
				break;
			case 2:
				{
				setState(119);
				reference();
				setState(120);
				comparisonOperator();
				setState(121);
				reference();
				}
				break;
			case 3:
				{
				setState(123);
				reference();
				setState(124);
				comparisonOperator();
				setState(125);
				match(WORD);
				}
				break;
			case 4:
				{
				setState(127);
				unaryOperator();
				setState(128);
				constraint(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(138);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConstraintContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_constraint);
					setState(132);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(133);
					binaryOperator();
					setState(134);
					constraint(3);
					}
					} 
				}
				setState(140);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ReferenceContext extends ParserRuleContext {
		public List<TerminalNode> WORD() { return getTokens(Mg4jEqlParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(Mg4jEqlParser.WORD, i);
		}
		public TerminalNode DOT() { return getToken(Mg4jEqlParser.DOT, 0); }
		public ReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceContext reference() throws RecognitionException {
		ReferenceContext _localctx = new ReferenceContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_reference);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(WORD);
			setState(142);
			match(DOT);
			setState(143);
			match(WORD);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonOperatorContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(Mg4jEqlParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(Mg4jEqlParser.NEQ, 0); }
		public TerminalNode LT() { return getToken(Mg4jEqlParser.LT, 0); }
		public TerminalNode LE() { return getToken(Mg4jEqlParser.LE, 0); }
		public TerminalNode GT() { return getToken(Mg4jEqlParser.GT, 0); }
		public TerminalNode GE() { return getToken(Mg4jEqlParser.GE, 0); }
		public ComparisonOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterComparisonOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitComparisonOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitComparisonOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOperatorContext comparisonOperator() throws RecognitionException {
		ComparisonOperatorContext _localctx = new ComparisonOperatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_comparisonOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << NEQ) | (1L << LT) | (1L << LE) | (1L << GT) | (1L << GE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryOperatorContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(Mg4jEqlParser.AND, 0); }
		public TerminalNode OR() { return getToken(Mg4jEqlParser.OR, 0); }
		public BinaryOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterBinaryOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitBinaryOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitBinaryOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryOperatorContext binaryOperator() throws RecognitionException {
		BinaryOperatorContext _localctx = new BinaryOperatorContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_binaryOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			_la = _input.LA(1);
			if ( !(_la==OR || _la==AND) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryOperatorContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(Mg4jEqlParser.NOT, 0); }
		public UnaryOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).enterUnaryOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mg4jEqlListener ) ((Mg4jEqlListener)listener).exitUnaryOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mg4jEqlVisitor ) return ((Mg4jEqlVisitor<? extends T>)visitor).visitUnaryOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperatorContext unaryOperator() throws RecognitionException {
		UnaryOperatorContext _localctx = new UnaryOperatorContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_unaryOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(NOT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 3:
			return queryCore_sempred((QueryCoreContext)_localctx, predIndex);
		case 9:
			return constraint_sempred((ConstraintContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean queryCore_sempred(QueryCoreContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean constraint_sempred(ConstraintContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\32\u009a\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\5\2\"\n\2\3\2\3"+
		"\2\3\3\6\3\'\n\3\r\3\16\3(\3\4\5\4,\n\4\3\4\3\4\5\4\60\n\4\3\4\5\4\63"+
		"\n\4\3\5\3\5\3\5\6\58\n\5\r\5\16\59\3\5\3\5\3\5\5\5?\n\5\3\5\3\5\3\5\6"+
		"\5D\n\5\r\5\16\5E\3\5\3\5\3\5\3\5\3\5\5\5M\n\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\7\5V\n\5\f\5\16\5Y\13\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\5\ti\n\t\3\n\3\n\7\nm\n\n\f\n\16\np\13\n\3\n\3\n\3\n\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\5\13\u0085\n\13\3\13\3\13\3\13\3\13\7\13\u008b\n\13\f\13\16\13"+
		"\u008e\13\13\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\2\4\b\24"+
		"\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\5\3\2\30\31\3\2\f\21\3\2\25"+
		"\26\2\u009f\2\36\3\2\2\2\4&\3\2\2\2\6+\3\2\2\2\bL\3\2\2\2\nZ\3\2\2\2\f"+
		"\\\3\2\2\2\16_\3\2\2\2\20h\3\2\2\2\22n\3\2\2\2\24\u0084\3\2\2\2\26\u008f"+
		"\3\2\2\2\30\u0093\3\2\2\2\32\u0095\3\2\2\2\34\u0097\3\2\2\2\36!\5\4\3"+
		"\2\37 \7\13\2\2 \"\5\24\13\2!\37\3\2\2\2!\"\3\2\2\2\"#\3\2\2\2#$\7\2\2"+
		"\3$\3\3\2\2\2%\'\5\6\4\2&%\3\2\2\2\'(\3\2\2\2(&\3\2\2\2()\3\2\2\2)\5\3"+
		"\2\2\2*,\5\16\b\2+*\3\2\2\2+,\3\2\2\2,-\3\2\2\2-/\5\b\5\2.\60\5\f\7\2"+
		"/.\3\2\2\2/\60\3\2\2\2\60\62\3\2\2\2\61\63\5\20\t\2\62\61\3\2\2\2\62\63"+
		"\3\2\2\2\63\7\3\2\2\2\64\65\b\5\1\2\65\67\7\n\2\2\668\5\6\4\2\67\66\3"+
		"\2\2\289\3\2\2\29\67\3\2\2\29:\3\2\2\2:;\3\2\2\2;<\7\n\2\2<M\3\2\2\2="+
		"?\5\22\n\2>=\3\2\2\2>?\3\2\2\2?@\3\2\2\2@M\5\n\6\2AC\7\22\2\2BD\5\6\4"+
		"\2CB\3\2\2\2DE\3\2\2\2EC\3\2\2\2EF\3\2\2\2FG\3\2\2\2GH\7\23\2\2HM\3\2"+
		"\2\2IJ\5\34\17\2JK\5\6\4\2KM\3\2\2\2L\64\3\2\2\2L>\3\2\2\2LA\3\2\2\2L"+
		"I\3\2\2\2MW\3\2\2\2NO\f\5\2\2OP\7\16\2\2PV\5\6\4\2QR\f\4\2\2RS\5\32\16"+
		"\2ST\5\6\4\2TV\3\2\2\2UN\3\2\2\2UQ\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2"+
		"\2X\t\3\2\2\2YW\3\2\2\2Z[\t\2\2\2[\13\3\2\2\2\\]\7\6\2\2]^\5\6\4\2^\r"+
		"\3\2\2\2_`\7\31\2\2`a\7\3\2\2a\17\3\2\2\2bc\7\4\2\2ci\7\t\2\2de\7\4\2"+
		"\2ei\7\b\2\2fg\7\7\2\2gi\7\30\2\2hb\3\2\2\2hd\3\2\2\2hf\3\2\2\2i\21\3"+
		"\2\2\2jk\7\31\2\2km\7\24\2\2lj\3\2\2\2mp\3\2\2\2nl\3\2\2\2no\3\2\2\2o"+
		"q\3\2\2\2pn\3\2\2\2qr\7\31\2\2rs\7\5\2\2s\23\3\2\2\2tu\b\13\1\2uv\7\22"+
		"\2\2vw\5\24\13\2wx\7\23\2\2x\u0085\3\2\2\2yz\5\26\f\2z{\5\30\r\2{|\5\26"+
		"\f\2|\u0085\3\2\2\2}~\5\26\f\2~\177\5\30\r\2\177\u0080\7\31\2\2\u0080"+
		"\u0085\3\2\2\2\u0081\u0082\5\34\17\2\u0082\u0083\5\24\13\3\u0083\u0085"+
		"\3\2\2\2\u0084t\3\2\2\2\u0084y\3\2\2\2\u0084}\3\2\2\2\u0084\u0081\3\2"+
		"\2\2\u0085\u008c\3\2\2\2\u0086\u0087\f\4\2\2\u0087\u0088\5\32\16\2\u0088"+
		"\u0089\5\24\13\5\u0089\u008b\3\2\2\2\u008a\u0086\3\2\2\2\u008b\u008e\3"+
		"\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\25\3\2\2\2\u008e"+
		"\u008c\3\2\2\2\u008f\u0090\7\31\2\2\u0090\u0091\7\24\2\2\u0091\u0092\7"+
		"\31\2\2\u0092\27\3\2\2\2\u0093\u0094\t\3\2\2\u0094\31\3\2\2\2\u0095\u0096"+
		"\t\4\2\2\u0096\33\3\2\2\2\u0097\u0098\7\27\2\2\u0098\35\3\2\2\2\21!(+"+
		"/\629>ELUWhn\u0084\u008c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}