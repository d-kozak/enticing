// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/Mg4jEql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler;

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
            MINUS = 1, COLON = 2, EXPONENT = 3, SIMILARITY = 4, SENT = 5, PAR = 6, QUOTATION = 7,
            SEPARATOR = 8, EQ = 9, NEQ = 10, LT = 11, LE = 12, GT = 13, GE = 14, PAREN_LEFT = 15,
            PAREN_RIGHT = 16, DOT = 17, OR = 18, AND = 19, NOT = 20, NUMBER = 21, WORD = 22, WS = 23;
    public static final int
            RULE_query = 0, RULE_queryElem = 1, RULE_limitation = 2, RULE_attribute = 3,
            RULE_constraint = 4, RULE_reference = 5, RULE_relOp = 6, RULE_binaryOp = 7,
            RULE_unaryOp = 8;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\31\u008e\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\7" +
                    "\2\26\n\2\f\2\16\2\31\13\2\3\2\3\2\5\2\35\n\2\3\2\3\2\3\3\3\3\3\3\6\3" +
                    "$\n\3\r\3\16\3%\3\3\3\3\3\3\3\3\5\3,\n\3\3\3\3\3\3\3\5\3\61\n\3\3\3\3" +
                    "\3\3\3\3\3\3\3\7\38\n\3\f\3\16\3;\13\3\3\3\3\3\7\3?\n\3\f\3\16\3B\13\3" +
                    "\3\3\3\3\5\3F\n\3\3\3\3\3\3\3\5\3K\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3" +
                    "T\n\3\f\3\16\3W\13\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4_\n\4\3\5\3\5\3\5\3\5" +
                    "\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3" +
                    "\6\3\6\3\6\5\6y\n\6\3\6\3\6\3\6\3\6\7\6\177\n\6\f\6\16\6\u0082\13\6\3" +
                    "\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\2\4\4\n\13\2\4\6\b\n\f\16\20" +
                    "\22\2\5\3\2\27\30\3\2\13\20\3\2\24\25\2\u0098\2\27\3\2\2\2\4J\3\2\2\2" +
                    "\6^\3\2\2\2\b`\3\2\2\2\nx\3\2\2\2\f\u0083\3\2\2\2\16\u0087\3\2\2\2\20" +
                    "\u0089\3\2\2\2\22\u008b\3\2\2\2\24\26\5\4\3\2\25\24\3\2\2\2\26\31\3\2" +
                    "\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30\34\3\2\2\2\31\27\3\2\2\2\32\33\7\n" +
                    "\2\2\33\35\5\n\6\2\34\32\3\2\2\2\34\35\3\2\2\2\35\36\3\2\2\2\36\37\7\2" +
                    "\2\3\37\3\3\2\2\2 !\b\3\1\2!#\7\t\2\2\"$\5\4\3\2#\"\3\2\2\2$%\3\2\2\2" +
                    "%#\3\2\2\2%&\3\2\2\2&\'\3\2\2\2\'(\7\t\2\2(K\3\2\2\2)*\7\30\2\2*,\7\4" +
                    "\2\2+)\3\2\2\2+,\3\2\2\2,-\3\2\2\2-K\7\30\2\2./\7\30\2\2/\61\7\4\2\2\60" +
                    ".\3\2\2\2\60\61\3\2\2\2\61\62\3\2\2\2\62\63\7\30\2\2\63\64\7\4\2\2\64" +
                    "9\7\30\2\2\65\66\7\5\2\2\668\5\b\5\2\67\65\3\2\2\28;\3\2\2\29\67\3\2\2" +
                    "\29:\3\2\2\2:K\3\2\2\2;9\3\2\2\2<@\7\21\2\2=?\5\4\3\2>=\3\2\2\2?B\3\2" +
                    "\2\2@>\3\2\2\2@A\3\2\2\2AC\3\2\2\2B@\3\2\2\2CE\7\22\2\2DF\5\6\4\2ED\3" +
                    "\2\2\2EF\3\2\2\2FK\3\2\2\2GH\5\22\n\2HI\5\4\3\3IK\3\2\2\2J \3\2\2\2J+" +
                    "\3\2\2\2J\60\3\2\2\2J<\3\2\2\2JG\3\2\2\2KU\3\2\2\2LM\f\5\2\2MN\7\r\2\2" +
                    "NT\5\4\3\6OP\f\4\2\2PQ\5\20\t\2QR\5\4\3\5RT\3\2\2\2SL\3\2\2\2SO\3\2\2" +
                    "\2TW\3\2\2\2US\3\2\2\2UV\3\2\2\2V\5\3\2\2\2WU\3\2\2\2XY\7\3\2\2Y_\7\b" +
                    "\2\2Z[\7\3\2\2[_\7\7\2\2\\]\7\6\2\2]_\7\27\2\2^X\3\2\2\2^Z\3\2\2\2^\\" +
                    "\3\2\2\2_\7\3\2\2\2`a\7\21\2\2ab\7\30\2\2bc\7\23\2\2cd\7\30\2\2de\7\4" +
                    "\2\2ef\t\2\2\2fg\7\22\2\2g\t\3\2\2\2hi\b\6\1\2ij\7\21\2\2jk\5\n\6\2kl" +
                    "\7\22\2\2ly\3\2\2\2mn\5\f\7\2no\5\16\b\2op\5\n\6\6py\3\2\2\2qr\5\f\7\2" +
                    "rs\5\16\b\2st\7\30\2\2ty\3\2\2\2uv\5\22\n\2vw\5\n\6\3wy\3\2\2\2xh\3\2" +
                    "\2\2xm\3\2\2\2xq\3\2\2\2xu\3\2\2\2y\u0080\3\2\2\2z{\f\4\2\2{|\5\20\t\2" +
                    "|}\5\n\6\5}\177\3\2\2\2~z\3\2\2\2\177\u0082\3\2\2\2\u0080~\3\2\2\2\u0080" +
                    "\u0081\3\2\2\2\u0081\13\3\2\2\2\u0082\u0080\3\2\2\2\u0083\u0084\7\30\2" +
                    "\2\u0084\u0085\7\23\2\2\u0085\u0086\7\30\2\2\u0086\r\3\2\2\2\u0087\u0088" +
                    "\t\3\2\2\u0088\17\3\2\2\2\u0089\u008a\t\4\2\2\u008a\21\3\2\2\2\u008b\u008c" +
                    "\7\26\2\2\u008c\23\3\2\2\2\20\27\34%+\609@EJSU^x\u0080";
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
                "query", "queryElem", "limitation", "attribute", "constraint", "reference",
                "relOp", "binaryOp", "unaryOp"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'-'", "':'", "'^'", "'~'", "'_SENT_'", "'_PAR_'", "'\"'", "'&&'",
                "'='", "'!='", "'<'", "'<='", "'>'", "'>='", "'('", "')'", "'.'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "MINUS", "COLON", "EXPONENT", "SIMILARITY", "SENT", "PAR", "QUOTATION",
                "SEPARATOR", "EQ", "NEQ", "LT", "LE", "GT", "GE", "PAREN_LEFT", "PAREN_RIGHT",
                "DOT", "OR", "AND", "NOT", "NUMBER", "WORD", "WS"
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

    public final QueryContext query() throws RecognitionException {
        QueryContext _localctx = new QueryContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_query);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(21);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << NOT) | (1L << WORD))) != 0)) {
                    {
                        {
                            setState(18);
                            queryElem(0);
                        }
                    }
                    setState(23);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(26);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == SEPARATOR) {
                    {
                        setState(24);
                        match(SEPARATOR);
                        setState(25);
                        constraint(0);
                    }
                }

                setState(28);
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

    public final QueryElemContext queryElem() throws RecognitionException {
        return queryElem(0);
    }

    private QueryElemContext queryElem(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        QueryElemContext _localctx = new QueryElemContext(_ctx, _parentState);
        QueryElemContext _prevctx = _localctx;
        int _startState = 2;
        enterRecursionRule(_localctx, 2, RULE_queryElem, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(72);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                    case 1: {
                        _localctx = new SequenceContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(31);
                        match(QUOTATION);
                        setState(33);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1: {
                                    {
                                        setState(32);
                                        queryElem(0);
                                    }
                                }
                                break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(35);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                        } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                        setState(37);
                        match(QUOTATION);
                    }
                    break;
                    case 2: {
                        _localctx = new WordContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(41);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                            case 1: {
                                setState(39);
                                match(WORD);
                                setState(40);
                                match(COLON);
                            }
                            break;
                        }
                        setState(43);
                        match(WORD);
                    }
                    break;
                    case 3: {
                        _localctx = new NertagContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(46);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
                            case 1: {
                                setState(44);
                                match(WORD);
                                setState(45);
                                match(COLON);
                            }
                            break;
                        }
                        setState(48);
                        match(WORD);
                        setState(49);
                        match(COLON);
                        setState(50);
                        match(WORD);
                        setState(55);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
                        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(51);
                                        match(EXPONENT);
                                        setState(52);
                                        attribute();
                                    }
                                }
                            }
                            setState(57);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
                        }
                    }
                    break;
                    case 4: {
                        _localctx = new ParenContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(58);
                        match(PAREN_LEFT);
                        setState(62);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << NOT) | (1L << WORD))) != 0)) {
                            {
                                {
                                    setState(59);
                                    queryElem(0);
                                }
                            }
                            setState(64);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                        setState(65);
                        match(PAREN_RIGHT);
                        setState(67);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
                            case 1: {
                                setState(66);
                                limitation();
                            }
                            break;
                        }
                    }
                    break;
                    case 5: {
                        _localctx = new UnaryOperationContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(69);
                        unaryOp();
                        setState(70);
                        queryElem(1);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(83);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(81);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                                case 1: {
                                    _localctx = new OrderOperationContext(new QueryElemContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryElem);
                                    setState(74);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(75);
                                    match(LT);
                                    setState(76);
                                    queryElem(4);
                                }
                                break;
                                case 2: {
                                    _localctx = new BinaryOperationContext(new QueryElemContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryElem);
                                    setState(77);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(78);
                                    binaryOp();
                                    setState(79);
                                    queryElem(3);
                                }
                                break;
                            }
                        }
                    }
                    setState(85);
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

    public final LimitationContext limitation() throws RecognitionException {
        LimitationContext _localctx = new LimitationContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_limitation);
        try {
            setState(92);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
                case 1:
                    _localctx = new ParContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(86);
                    match(MINUS);
                    setState(87);
                    match(PAR);
                }
                break;
                case 2:
                    _localctx = new SentContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(88);
                    match(MINUS);
                    setState(89);
                    match(SENT);
                }
                break;
                case 3:
                    _localctx = new ProximityContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(90);
                    match(SIMILARITY);
                    setState(91);
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

    public final AttributeContext attribute() throws RecognitionException {
        AttributeContext _localctx = new AttributeContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_attribute);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(94);
                match(PAREN_LEFT);
                setState(95);
                match(WORD);
                setState(96);
                match(DOT);
                setState(97);
                match(WORD);
                setState(98);
                match(COLON);
                setState(99);
                _la = _input.LA(1);
                if (!(_la == NUMBER || _la == WORD)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(100);
                match(PAREN_RIGHT);
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
        int _startState = 8;
        enterRecursionRule(_localctx, 8, RULE_constraint, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(118);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
                    case 1: {
                        setState(103);
                        match(PAREN_LEFT);
                        setState(104);
                        constraint(0);
                        setState(105);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case 2: {
                        setState(107);
                        reference();
                        setState(108);
                        relOp();
                        setState(109);
                        constraint(4);
                    }
                    break;
                    case 3: {
                        setState(111);
                        reference();
                        setState(112);
                        relOp();
                        setState(113);
                        match(WORD);
                    }
                    break;
                    case 4: {
                        setState(115);
                        unaryOp();
                        setState(116);
                        constraint(1);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(126);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 13, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new ConstraintContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_constraint);
                                setState(120);
                                if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                setState(121);
                                binaryOp();
                                setState(122);
                                constraint(3);
                            }
                        }
                    }
                    setState(128);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 13, _ctx);
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
        enterRule(_localctx, 10, RULE_reference);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(129);
                match(WORD);
                setState(130);
                match(DOT);
                setState(131);
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
        enterRule(_localctx, 12, RULE_relOp);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(133);
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

    public final BinaryOpContext binaryOp() throws RecognitionException {
        BinaryOpContext _localctx = new BinaryOpContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_binaryOp);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(135);
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

    public final UnaryOpContext unaryOp() throws RecognitionException {
        UnaryOpContext _localctx = new UnaryOpContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_unaryOp);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(137);
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
            case 1:
                return queryElem_sempred((QueryElemContext) _localctx, predIndex);
            case 4:
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

    public static class QueryContext extends ParserRuleContext {
        public QueryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EOF() {
            return getToken(Mg4jEqlParser.EOF, 0);
        }

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
        }

        public TerminalNode SEPARATOR() {
            return getToken(Mg4jEqlParser.SEPARATOR, 0);
        }

        public ConstraintContext constraint() {
            return getRuleContext(ConstraintContext.class, 0);
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

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
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

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
        }

        public LimitationContext limitation() {
            return getRuleContext(LimitationContext.class, 0);
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

    public static class NertagContext extends QueryElemContext {
        public NertagContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public List<TerminalNode> WORD() {
            return getTokens(Mg4jEqlParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(Mg4jEqlParser.WORD, i);
        }

        public List<TerminalNode> COLON() {
            return getTokens(Mg4jEqlParser.COLON);
        }

        public TerminalNode COLON(int i) {
            return getToken(Mg4jEqlParser.COLON, i);
        }

        public List<TerminalNode> EXPONENT() {
            return getTokens(Mg4jEqlParser.EXPONENT);
        }

        public TerminalNode EXPONENT(int i) {
            return getToken(Mg4jEqlParser.EXPONENT, i);
        }

        public List<AttributeContext> attribute() {
            return getRuleContexts(AttributeContext.class);
        }

        public AttributeContext attribute(int i) {
            return getRuleContext(AttributeContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterNertag(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitNertag(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitNertag(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class OrderOperationContext extends QueryElemContext {
        public OrderOperationContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
        }

        public TerminalNode LT() {
            return getToken(Mg4jEqlParser.LT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterOrderOperation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitOrderOperation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor)
                return ((Mg4jEqlVisitor<? extends T>) visitor).visitOrderOperation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class BinaryOperationContext extends QueryElemContext {
        public BinaryOperationContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
        }

        public BinaryOpContext binaryOp() {
            return getRuleContext(BinaryOpContext.class, 0);
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

        public UnaryOpContext unaryOp() {
            return getRuleContext(UnaryOpContext.class, 0);
        }

        public QueryElemContext queryElem() {
            return getRuleContext(QueryElemContext.class, 0);
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

    public static class WordContext extends QueryElemContext {
        public WordContext(QueryElemContext ctx) {
            copyFrom(ctx);
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

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterWord(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitWord(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitWord(this);
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

    public static class AttributeContext extends ParserRuleContext {
        public AttributeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jEqlParser.PAREN_LEFT, 0);
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

        public TerminalNode COLON() {
            return getToken(Mg4jEqlParser.COLON, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jEqlParser.PAREN_RIGHT, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(Mg4jEqlParser.NUMBER, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_attribute;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterAttribute(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitAttribute(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitAttribute(this);
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

        public ReferenceContext reference() {
            return getRuleContext(ReferenceContext.class, 0);
        }

        public RelOpContext relOp() {
            return getRuleContext(RelOpContext.class, 0);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jEqlParser.WORD, 0);
        }

        public UnaryOpContext unaryOp() {
            return getRuleContext(UnaryOpContext.class, 0);
        }

        public BinaryOpContext binaryOp() {
            return getRuleContext(BinaryOpContext.class, 0);
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

    public static class BinaryOpContext extends ParserRuleContext {
        public BinaryOpContext(ParserRuleContext parent, int invokingState) {
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
            return RULE_binaryOp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterBinaryOp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitBinaryOp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitBinaryOp(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class UnaryOpContext extends ParserRuleContext {
        public UnaryOpContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode NOT() {
            return getToken(Mg4jEqlParser.NOT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_unaryOp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).enterUnaryOp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jEqlListener) ((Mg4jEqlListener) listener).exitUnaryOp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jEqlVisitor) return ((Mg4jEqlVisitor<? extends T>) visitor).visitUnaryOp(this);
            else return visitor.visitChildren(this);
        }
    }
}