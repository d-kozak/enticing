// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/parser/Mg4jEql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Mg4jEqlParser extends Parser {
    public static final int
            ARROW = 1, MINUS = 2, COLON = 3, EXPONENT = 4, SIMILARITY = 5, SENT = 6, PAR = 7, QUOTATION = 8,
            QUERY_CONSTRAINT_SEPARATOR = 9, EQ = 10, NEQ = 11, LT = 12, LE = 13, GT = 14, GE = 15,
            PAREN_LEFT = 16, PAREN_RIGHT = 17, DOT = 18, OR = 19, AND = 20, NOT = 21, WS = 22, NUMBER = 23,
            WORD = 24;
    public static final int
            RULE_root = 0, RULE_query = 1, RULE_queryElem = 2, RULE_literal = 3, RULE_align = 4,
            RULE_identifier = 5, RULE_limitation = 6, RULE_indexOperator = 7, RULE_constraint = 8,
            RULE_reference = 9, RULE_relOp = 10, RULE_binaryOperator = 11, RULE_unaryOperator = 12;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\32\u0097\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\6\2\36\n\2\r\2\16\2\37\3\2\3\2\5\2" +
                    "$\n\2\3\2\3\2\3\3\5\3)\n\3\3\3\3\3\5\3-\n\3\3\3\5\3\60\n\3\3\4\3\4\3\4" +
                    "\6\4\65\n\4\r\4\16\4\66\3\4\3\4\3\4\5\4<\n\4\3\4\3\4\3\4\6\4A\n\4\r\4" +
                    "\16\4B\3\4\3\4\3\4\3\4\3\4\5\4J\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4S\n" +
                    "\4\f\4\16\4V\13\4\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b" +
                    "\3\b\5\bf\n\b\3\t\3\t\7\tj\n\t\f\t\16\tm\13\t\3\t\3\t\3\t\3\n\3\n\3\n" +
                    "\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u0082\n\n\3\n" +
                    "\3\n\3\n\3\n\7\n\u0088\n\n\f\n\16\n\u008b\13\n\3\13\3\13\3\13\3\13\3\f" +
                    "\3\f\3\r\3\r\3\16\3\16\3\16\2\4\6\22\17\2\4\6\b\n\f\16\20\22\24\26\30" +
                    "\32\2\5\3\2\31\32\3\2\f\21\3\2\25\26\2\u009d\2\35\3\2\2\2\4(\3\2\2\2\6" +
                    "I\3\2\2\2\bW\3\2\2\2\nY\3\2\2\2\f\\\3\2\2\2\16e\3\2\2\2\20k\3\2\2\2\22" +
                    "\u0081\3\2\2\2\24\u008c\3\2\2\2\26\u0090\3\2\2\2\30\u0092\3\2\2\2\32\u0094" +
                    "\3\2\2\2\34\36\5\4\3\2\35\34\3\2\2\2\36\37\3\2\2\2\37\35\3\2\2\2\37 \3" +
                    "\2\2\2 #\3\2\2\2!\"\7\13\2\2\"$\5\22\n\2#!\3\2\2\2#$\3\2\2\2$%\3\2\2\2" +
                    "%&\7\2\2\3&\3\3\2\2\2\')\5\f\7\2(\'\3\2\2\2()\3\2\2\2)*\3\2\2\2*,\5\6" +
                    "\4\2+-\5\n\6\2,+\3\2\2\2,-\3\2\2\2-/\3\2\2\2.\60\5\16\b\2/.\3\2\2\2/\60" +
                    "\3\2\2\2\60\5\3\2\2\2\61\62\b\4\1\2\62\64\7\n\2\2\63\65\5\4\3\2\64\63" +
                    "\3\2\2\2\65\66\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\7\n\2" +
                    "\29J\3\2\2\2:<\5\20\t\2;:\3\2\2\2;<\3\2\2\2<=\3\2\2\2=J\5\b\5\2>@\7\22" +
                    "\2\2?A\5\4\3\2@?\3\2\2\2AB\3\2\2\2B@\3\2\2\2BC\3\2\2\2CD\3\2\2\2DE\7\23" +
                    "\2\2EJ\3\2\2\2FG\5\32\16\2GH\5\4\3\2HJ\3\2\2\2I\61\3\2\2\2I;\3\2\2\2I" +
                    ">\3\2\2\2IF\3\2\2\2JT\3\2\2\2KL\f\5\2\2LM\7\16\2\2MS\5\4\3\2NO\f\4\2\2" +
                    "OP\5\30\r\2PQ\5\4\3\2QS\3\2\2\2RK\3\2\2\2RN\3\2\2\2SV\3\2\2\2TR\3\2\2" +
                    "\2TU\3\2\2\2U\7\3\2\2\2VT\3\2\2\2WX\t\2\2\2X\t\3\2\2\2YZ\7\6\2\2Z[\5\4" +
                    "\3\2[\13\3\2\2\2\\]\7\32\2\2]^\7\3\2\2^\r\3\2\2\2_`\7\4\2\2`f\7\t\2\2" +
                    "ab\7\4\2\2bf\7\b\2\2cd\7\7\2\2df\7\31\2\2e_\3\2\2\2ea\3\2\2\2ec\3\2\2" +
                    "\2f\17\3\2\2\2gh\7\32\2\2hj\7\24\2\2ig\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3" +
                    "\2\2\2ln\3\2\2\2mk\3\2\2\2no\7\32\2\2op\7\5\2\2p\21\3\2\2\2qr\b\n\1\2" +
                    "rs\7\22\2\2st\5\22\n\2tu\7\23\2\2u\u0082\3\2\2\2vw\5\24\13\2wx\5\26\f" +
                    "\2xy\5\24\13\2y\u0082\3\2\2\2z{\5\24\13\2{|\5\26\f\2|}\7\32\2\2}\u0082" +
                    "\3\2\2\2~\177\5\32\16\2\177\u0080\5\22\n\3\u0080\u0082\3\2\2\2\u0081q" +
                    "\3\2\2\2\u0081v\3\2\2\2\u0081z\3\2\2\2\u0081~\3\2\2\2\u0082\u0089\3\2" +
                    "\2\2\u0083\u0084\f\4\2\2\u0084\u0085\5\30\r\2\u0085\u0086\5\22\n\5\u0086" +
                    "\u0088\3\2\2\2\u0087\u0083\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2" +
                    "\2\2\u0089\u008a\3\2\2\2\u008a\23\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008d" +
                    "\7\32\2\2\u008d\u008e\7\24\2\2\u008e\u008f\7\32\2\2\u008f\25\3\2\2\2\u0090" +
                    "\u0091\t\3\2\2\u0091\27\3\2\2\2\u0092\u0093\t\4\2\2\u0093\31\3\2\2\2\u0094" +
                    "\u0095\7\27\2\2\u0095\33\3\2\2\2\21\37#(,/\66;BIRTek\u0081\u0089";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    static {
        RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
    }

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

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public Mg4jEqlParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "root", "query", "queryElem", "literal", "align", "identifier", "limitation",
                "indexOperator", "constraint", "reference", "relOp", "binaryOperator",
                "unaryOperator"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'<-'", "'-'", "':'", "'^'", "'~'", "'_SENT_'", "'_PAR_'", "'\"'",
                "'&&'", "'='", "'!='", "'<'", "'<='", "'>'", "'>='", "'('", "')'", "'.'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "ARROW", "MINUS", "COLON", "EXPONENT", "SIMILARITY", "SENT", "PAR",
                "QUOTATION", "QUERY_CONSTRAINT_SEPARATOR", "EQ", "NEQ", "LT", "LE", "GT",
                "GE", "PAREN_LEFT", "PAREN_RIGHT", "DOT", "OR", "AND", "NOT", "WS", "NUMBER",
                "WORD"
        };
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
    public String getGrammarFileName() {
        return "Mg4jEql.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public final RootContext root() throws RecognitionException {
        RootContext _localctx = new RootContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_root);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(27);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        {
                            setState(26);
                            query();
                        }
                    }
                    setState(29);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << NOT) | (1L << NUMBER) | (1L << WORD))) != 0));
                setState(33);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == QUERY_CONSTRAINT_SEPARATOR) {
                    {
                        setState(31);
                        match(QUERY_CONSTRAINT_SEPARATOR);
                        setState(32);
                        constraint(0);
                    }
                }

                setState(35);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final QueryContext query() throws RecognitionException {
        QueryContext _localctx = new QueryContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_query);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(38);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                    case 1: {
                        setState(37);
                        identifier();
                    }
                    break;
                }
                setState(40);
                queryElem(0);
                setState(42);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                    case 1: {
                        setState(41);
                        align();
                    }
                    break;
                }
                setState(45);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
                    case 1: {
                        setState(44);
                        limitation();
                    }
                    break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final QueryElemContext queryElem() throws RecognitionException {
        return queryElem(0);
    }

    private QueryElemContext queryElem(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        QueryElemContext _localctx = new QueryElemContext(_ctx, _parentState);
        QueryElemContext _prevctx = _localctx;
        int _startState = 4;
        enterRecursionRule(_localctx, 4, RULE_queryElem, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(71);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case QUOTATION: {
                        _localctx = new SequenceContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(48);
                        match(QUOTATION);
                        setState(50);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1: {
                                    {
                                        setState(49);
                                        query();
                                    }
                                }
                                break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(52);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
                        } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                        setState(54);
                        match(QUOTATION);
                    }
                    break;
                    case NUMBER:
                    case WORD: {
                        _localctx = new LitContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(57);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
                            case 1: {
                                setState(56);
                                indexOperator();
                            }
                            break;
                        }
                        setState(59);
                        literal();
                    }
                    break;
                    case PAREN_LEFT: {
                        _localctx = new ParenContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(60);
                        match(PAREN_LEFT);
                        setState(62);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(61);
                                    query();
                                }
                            }
                            setState(64);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << NOT) | (1L << NUMBER) | (1L << WORD))) != 0));
                        setState(66);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case NOT: {
                        _localctx = new UnaryOperationContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(68);
                        unaryOperator();
                        setState(69);
                        query();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(82);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(80);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                                case 1: {
                                    _localctx = new OrderContext(new QueryElemContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryElem);
                                    setState(73);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(74);
                                    match(LT);
                                    setState(75);
                                    query();
                                }
                                break;
                                case 2: {
                                    _localctx = new BinaryOperationContext(new QueryElemContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryElem);
                                    setState(76);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(77);
                                    binaryOperator();
                                    setState(78);
                                    query();
                                }
                                break;
                            }
                        }
                    }
                    setState(84);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    public final LiteralContext literal() throws RecognitionException {
        LiteralContext _localctx = new LiteralContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_literal);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(85);
                _la = _input.LA(1);
                if (!(_la == NUMBER || _la == WORD)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final AlignContext align() throws RecognitionException {
        AlignContext _localctx = new AlignContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_align);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(87);
                match(EXPONENT);
                setState(88);
                query();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final IdentifierContext identifier() throws RecognitionException {
        IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_identifier);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(90);
                match(WORD);
                setState(91);
                match(ARROW);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final LimitationContext limitation() throws RecognitionException {
        LimitationContext _localctx = new LimitationContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_limitation);
        try {
            setState(99);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
                case 1:
                    _localctx = new ParContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(93);
                    match(MINUS);
                    setState(94);
                    match(PAR);
                }
                break;
                case 2:
                    _localctx = new SentContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(95);
                    match(MINUS);
                    setState(96);
                    match(SENT);
                }
                break;
                case 3:
                    _localctx = new ProximityContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(97);
                    match(SIMILARITY);
                    setState(98);
                    match(NUMBER);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final IndexOperatorContext indexOperator() throws RecognitionException {
        IndexOperatorContext _localctx = new IndexOperatorContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_indexOperator);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(105);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 12, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(101);
                                match(WORD);
                                setState(102);
                                match(DOT);
                            }
                        }
                    }
                    setState(107);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 12, _ctx);
                }
                setState(108);
                match(WORD);
                setState(109);
                match(COLON);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ConstraintContext constraint() throws RecognitionException {
        return constraint(0);
    }

    private ConstraintContext constraint(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        ConstraintContext _localctx = new ConstraintContext(_ctx, _parentState);
        ConstraintContext _prevctx = _localctx;
        int _startState = 16;
        enterRecursionRule(_localctx, 16, RULE_constraint, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(127);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                    case 1: {
                        setState(112);
                        match(PAREN_LEFT);
                        setState(113);
                        constraint(0);
                        setState(114);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case 2: {
                        setState(116);
                        reference();
                        setState(117);
                        relOp();
                        setState(118);
                        reference();
                    }
                    break;
                    case 3: {
                        setState(120);
                        reference();
                        setState(121);
                        relOp();
                        setState(122);
                        match(WORD);
                    }
                    break;
                    case 4: {
                        setState(124);
                        unaryOperator();
                        setState(125);
                        constraint(1);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(135);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new ConstraintContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_constraint);
                                setState(129);
                                if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                setState(130);
                                binaryOperator();
                                setState(131);
                                constraint(3);
                            }
                        }
                    }
                    setState(137);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    public final ReferenceContext reference() throws RecognitionException {
        ReferenceContext _localctx = new ReferenceContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_reference);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(138);
                match(WORD);
                setState(139);
                match(DOT);
                setState(140);
                match(WORD);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final RelOpContext relOp() throws RecognitionException {
        RelOpContext _localctx = new RelOpContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_relOp);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(142);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << NEQ) | (1L << LT) | (1L << LE) | (1L << GT) | (1L << GE))) != 0))) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final BinaryOperatorContext binaryOperator() throws RecognitionException {
        BinaryOperatorContext _localctx = new BinaryOperatorContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_binaryOperator);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(144);
                _la = _input.LA(1);
                if (!(_la == OR || _la == AND)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final UnaryOperatorContext unaryOperator() throws RecognitionException {
        UnaryOperatorContext _localctx = new UnaryOperatorContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_unaryOperator);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(146);
                match(NOT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 2:
                return queryElem_sempred((QueryElemContext) _localctx, predIndex);
            case 8:
                return constraint_sempred((ConstraintContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean queryElem_sempred(QueryElemContext _localctx, int predIndex) {
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

    public static class RootContext extends ParserRuleContext {
        public RootContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EOF() {
            return getToken(Mg4jEqlParser.EOF, 0);
        }

        public List<QueryContext> query() {
            return getRuleContexts(QueryContext.class);
        }

        public QueryContext query(int i) {
            return getRuleContext(QueryContext.class, i);
        }

        public TerminalNode QUERY_CONSTRAINT_SEPARATOR() {
            return getToken(Mg4jEqlParser.QUERY_CONSTRAINT_SEPARATOR, 0);
        }

        public ConstraintContext constraint() {
            return getRuleContext(ConstraintContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_root;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterRoot(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitRoot(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitRoot(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class QueryContext extends ParserRuleContext {
        public QueryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public QueryElemContext queryElem() {
            return getRuleContext(QueryElemContext.class, 0);
        }

        public IdentifierContext identifier() {
            return getRuleContext(IdentifierContext.class, 0);
        }

        public AlignContext align() {
            return getRuleContext(AlignContext.class, 0);
        }

        public LimitationContext limitation() {
            return getRuleContext(LimitationContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_query;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterQuery(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitQuery(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitQuery(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class QueryElemContext extends ParserRuleContext {
        public QueryElemContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public QueryElemContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_queryElem;
        }

        public void copyFrom(QueryElemContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SequenceContext extends QueryElemContext {
        public SequenceContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public List<TerminalNode> QUOTATION() {
            return getTokens(Mg4jEqlParser.QUOTATION);
        }

        public TerminalNode QUOTATION(int i) {
            return getToken(Mg4jEqlParser.QUOTATION, i);
        }

        public List<QueryContext> query() {
            return getRuleContexts(QueryContext.class);
        }

        public QueryContext query(int i) {
            return getRuleContext(QueryContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterSequence(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitSequence(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitSequence(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ParenContext extends QueryElemContext {
        public ParenContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jEqlParser.PAREN_LEFT, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jEqlParser.PAREN_RIGHT, 0);
        }

        public List<QueryContext> query() {
            return getRuleContexts(QueryContext.class);
        }

        public QueryContext query(int i) {
            return getRuleContext(QueryContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterParen(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitParen(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitParen(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class LitContext extends QueryElemContext {
        public LitContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public LiteralContext literal() {
            return getRuleContext(LiteralContext.class, 0);
        }

        public IndexOperatorContext indexOperator() {
            return getRuleContext(IndexOperatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterLit(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitLit(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitLit(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class BinaryOperationContext extends QueryElemContext {
        public BinaryOperationContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public QueryElemContext queryElem() {
            return getRuleContext(QueryElemContext.class, 0);
        }

        public BinaryOperatorContext binaryOperator() {
            return getRuleContext(BinaryOperatorContext.class, 0);
        }

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterBinaryOperation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitBinaryOperation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor)
                return ((Mg4jEqlVisitor<? extends T>) visitor).visitBinaryOperation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class UnaryOperationContext extends QueryElemContext {
        public UnaryOperationContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public UnaryOperatorContext unaryOperator() {
            return getRuleContext(UnaryOperatorContext.class, 0);
        }

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterUnaryOperation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitUnaryOperation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor)
                return ((Mg4jEqlVisitor<? extends T>) visitor).visitUnaryOperation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class OrderContext extends QueryElemContext {
        public OrderContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public QueryElemContext queryElem() {
            return getRuleContext(QueryElemContext.class, 0);
        }

        public TerminalNode LT() {
            return getToken(Mg4jEqlParser.LT, 0);
        }

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterOrder(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitOrder(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitOrder(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class LiteralContext extends ParserRuleContext {
        public LiteralContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jEqlParser.WORD, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(Mg4jEqlParser.NUMBER, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_literal;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterLiteral(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitLiteral(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitLiteral(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AlignContext extends ParserRuleContext {
        public AlignContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EXPONENT() {
            return getToken(Mg4jEqlParser.EXPONENT, 0);
        }

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_align;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterAlign(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitAlign(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitAlign(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IdentifierContext extends ParserRuleContext {
        public IdentifierContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jEqlParser.WORD, 0);
        }

        public TerminalNode ARROW() {
            return getToken(Mg4jEqlParser.ARROW, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_identifier;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterIdentifier(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitIdentifier(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitIdentifier(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class LimitationContext extends ParserRuleContext {
        public LimitationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public LimitationContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_limitation;
        }

        public void copyFrom(LimitationContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class ParContext extends LimitationContext {
        public ParContext(LimitationContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode MINUS() {
            return getToken(Mg4jEqlParser.MINUS, 0);
        }

        public TerminalNode PAR() {
            return getToken(Mg4jEqlParser.PAR, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterPar(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitPar(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitPar(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ProximityContext extends LimitationContext {
        public ProximityContext(LimitationContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode SIMILARITY() {
            return getToken(Mg4jEqlParser.SIMILARITY, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(Mg4jEqlParser.NUMBER, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterProximity(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitProximity(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitProximity(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SentContext extends LimitationContext {
        public SentContext(LimitationContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode MINUS() {
            return getToken(Mg4jEqlParser.MINUS, 0);
        }

        public TerminalNode SENT() {
            return getToken(Mg4jEqlParser.SENT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterSent(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitSent(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitSent(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IndexOperatorContext extends ParserRuleContext {
        public IndexOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> WORD() {
            return getTokens(Mg4jEqlParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(Mg4jEqlParser.WORD, i);
        }

        public TerminalNode COLON() {
            return getToken(Mg4jEqlParser.COLON, 0);
        }

        public List<TerminalNode> DOT() {
            return getTokens(Mg4jEqlParser.DOT);
        }

        public TerminalNode DOT(int i) {
            return getToken(Mg4jEqlParser.DOT, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_indexOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterIndexOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitIndexOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor)
                return ((Mg4jEqlVisitor<? extends T>) visitor).visitIndexOperator(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ConstraintContext extends ParserRuleContext {
        public ConstraintContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jEqlParser.PAREN_LEFT, 0);
        }

        public List<ConstraintContext> constraint() {
            return getRuleContexts(ConstraintContext.class);
        }

        public ConstraintContext constraint(int i) {
            return getRuleContext(ConstraintContext.class, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jEqlParser.PAREN_RIGHT, 0);
        }

        public List<ReferenceContext> reference() {
            return getRuleContexts(ReferenceContext.class);
        }

        public ReferenceContext reference(int i) {
            return getRuleContext(ReferenceContext.class, i);
        }

        public RelOpContext relOp() {
            return getRuleContext(RelOpContext.class, 0);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jEqlParser.WORD, 0);
        }

        public UnaryOperatorContext unaryOperator() {
            return getRuleContext(UnaryOperatorContext.class, 0);
        }

        public BinaryOperatorContext binaryOperator() {
            return getRuleContext(BinaryOperatorContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_constraint;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterConstraint(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitConstraint(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitConstraint(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ReferenceContext extends ParserRuleContext {
        public ReferenceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> WORD() {
            return getTokens(Mg4jEqlParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(Mg4jEqlParser.WORD, i);
        }

        public TerminalNode DOT() {
            return getToken(Mg4jEqlParser.DOT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_reference;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterReference(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitReference(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitReference(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class RelOpContext extends ParserRuleContext {
        public RelOpContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EQ() {
            return getToken(Mg4jEqlParser.EQ, 0);
        }

        public TerminalNode NEQ() {
            return getToken(Mg4jEqlParser.NEQ, 0);
        }

        public TerminalNode LT() {
            return getToken(Mg4jEqlParser.LT, 0);
        }

        public TerminalNode LE() {
            return getToken(Mg4jEqlParser.LE, 0);
        }

        public TerminalNode GT() {
            return getToken(Mg4jEqlParser.GT, 0);
        }

        public TerminalNode GE() {
            return getToken(Mg4jEqlParser.GE, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_relOp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterRelOp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitRelOp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitRelOp(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class BinaryOperatorContext extends ParserRuleContext {
        public BinaryOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode AND() {
            return getToken(Mg4jEqlParser.AND, 0);
        }

        public TerminalNode OR() {
            return getToken(Mg4jEqlParser.OR, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_binaryOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterBinaryOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitBinaryOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor)
                return ((Mg4jEqlVisitor<? extends T>) visitor).visitBinaryOperator(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class UnaryOperatorContext extends ParserRuleContext {
        public UnaryOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode NOT() {
            return getToken(Mg4jEqlParser.NOT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_unaryOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterUnaryOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitUnaryOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor)
                return ((Mg4jEqlVisitor<? extends T>) visitor).visitUnaryOperator(this);
            else return visitor.visitChildren(this);
        }
    }
}