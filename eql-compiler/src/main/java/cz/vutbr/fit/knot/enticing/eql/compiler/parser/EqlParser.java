// Generated from /home/dkozak/projects/knot/enticing/eql-compiler/src/main/resources/Eql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.eql.compiler.parser;

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
public class EqlParser extends Parser {
    public static final int
            ASSIGN = 1, COLON = 2, EXPONENT = 3, SIMILARITY = 4, SENT = 5, PAR = 6, QUOTATION = 7,
            GLOBAL_CONSTRAINT_SEPARATOR = 8, EQ = 9, NEQ = 10, LT = 11, PAREN_LEFT = 12, PAREN_RIGHT = 13,
            BRACKET_LEFT = 14, BRACKET_RIGHT = 15, RANGE = 16, DOT = 17, OR = 18, AND = 19, NOT = 20,
            NUMBER = 21, DATE = 22, WORD = 23, WS = 24;
    public static final int
            RULE_root = 0, RULE_query = 1, RULE_queryElem = 2, RULE_proximity = 3,
            RULE_alignOperator = 4, RULE_alignElem = 5, RULE_singleValueOrMultiple = 6,
            RULE_literalOrInterval = 7, RULE_assignment = 8, RULE_contextConstraint = 9,
            RULE_indexOperator = 10, RULE_queryLiteral = 11, RULE_interval = 12, RULE_globalConstraint = 13,
            RULE_reference = 14, RULE_comparisonOperator = 15, RULE_logicBinaryOperator = 16,
            RULE_logicUnaryOperator = 17;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\32\u00f1\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\3\2\3\2\3\2\5\2*\n\2\3\2\3\2\3\3\6\3/\n\3\r\3\16\3\60\3\3\5" +
                    "\3\64\n\3\3\3\5\3\67\n\3\3\4\3\4\5\4;\n\4\3\4\3\4\6\4?\n\4\r\4\16\4@\3" +
                    "\4\3\4\3\4\5\4F\n\4\3\4\3\4\5\4J\n\4\3\4\5\4M\n\4\3\4\3\4\3\4\5\4R\n\4" +
                    "\3\4\5\4U\n\4\3\4\3\4\6\4Y\n\4\r\4\16\4Z\3\4\3\4\5\4_\n\4\3\4\3\4\3\4" +
                    "\3\4\3\4\5\4f\n\4\3\4\5\4i\n\4\3\4\3\4\3\4\5\4n\n\4\3\4\3\4\3\4\3\4\3" +
                    "\4\3\4\3\4\3\4\5\4x\n\4\7\4z\n\4\f\4\16\4}\13\4\3\5\3\5\3\5\3\6\3\6\6" +
                    "\6\u0084\n\6\r\6\16\6\u0085\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3" +
                    "\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u009e\n\7\3\b\3\b\3" +
                    "\b\3\b\3\b\7\b\u00a5\n\b\f\b\16\b\u00a8\13\b\3\b\3\b\5\b\u00ac\n\b\3\t" +
                    "\3\t\5\t\u00b0\n\t\3\n\3\n\3\n\3\13\3\13\5\13\u00b7\n\13\3\f\3\f\3\f\3" +
                    "\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00c5\n\16\3\16\3\16\5" +
                    "\16\u00c9\n\16\3\16\5\16\u00cc\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17" +
                    "\3\17\3\17\3\17\3\17\3\17\5\17\u00da\n\17\3\17\3\17\3\17\3\17\7\17\u00e0" +
                    "\n\17\f\17\16\17\u00e3\13\17\3\20\3\20\5\20\u00e7\n\20\3\20\3\20\3\21" +
                    "\3\21\3\22\3\22\3\23\3\23\3\23\2\4\6\34\24\2\4\6\b\n\f\16\20\22\24\26" +
                    "\30\32\34\36 \"$\2\5\4\2\27\27\31\31\3\2\13\f\3\2\24\25\2\u0104\2&\3\2" +
                    "\2\2\4.\3\2\2\2\6m\3\2\2\2\b~\3\2\2\2\n\u0083\3\2\2\2\f\u009d\3\2\2\2" +
                    "\16\u00ab\3\2\2\2\20\u00af\3\2\2\2\22\u00b1\3\2\2\2\24\u00b6\3\2\2\2\26" +
                    "\u00b8\3\2\2\2\30\u00bb\3\2\2\2\32\u00cb\3\2\2\2\34\u00d9\3\2\2\2\36\u00e6" +
                    "\3\2\2\2 \u00ea\3\2\2\2\"\u00ec\3\2\2\2$\u00ee\3\2\2\2&)\5\4\3\2\'(\7" +
                    "\n\2\2(*\5\34\17\2)\'\3\2\2\2)*\3\2\2\2*+\3\2\2\2+,\7\2\2\3,\3\3\2\2\2" +
                    "-/\5\6\4\2.-\3\2\2\2/\60\3\2\2\2\60.\3\2\2\2\60\61\3\2\2\2\61\63\3\2\2" +
                    "\2\62\64\5\b\5\2\63\62\3\2\2\2\63\64\3\2\2\2\64\66\3\2\2\2\65\67\5\24" +
                    "\13\2\66\65\3\2\2\2\66\67\3\2\2\2\67\5\3\2\2\28:\b\4\1\29;\5\22\n\2:9" +
                    "\3\2\2\2:;\3\2\2\2;<\3\2\2\2<>\7\t\2\2=?\5\6\4\2>=\3\2\2\2?@\3\2\2\2@" +
                    ">\3\2\2\2@A\3\2\2\2AB\3\2\2\2BC\7\t\2\2Cn\3\2\2\2DF\5\22\n\2ED\3\2\2\2" +
                    "EF\3\2\2\2FG\3\2\2\2GI\5\30\r\2HJ\5\n\6\2IH\3\2\2\2IJ\3\2\2\2Jn\3\2\2" +
                    "\2KM\5\22\n\2LK\3\2\2\2LM\3\2\2\2MN\3\2\2\2NO\5\26\f\2OQ\5\16\b\2PR\5" +
                    "\n\6\2QP\3\2\2\2QR\3\2\2\2Rn\3\2\2\2SU\5\22\n\2TS\3\2\2\2TU\3\2\2\2UV" +
                    "\3\2\2\2VX\7\16\2\2WY\5\6\4\2XW\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2" +
                    "[\\\3\2\2\2\\^\7\17\2\2]_\5\b\5\2^]\3\2\2\2^_\3\2\2\2_n\3\2\2\2`a\5\22" +
                    "\n\2ab\5\6\4\2bc\7\r\2\2ce\5\6\4\2df\5\b\5\2ed\3\2\2\2ef\3\2\2\2fn\3\2" +
                    "\2\2gi\5\22\n\2hg\3\2\2\2hi\3\2\2\2ij\3\2\2\2jk\5$\23\2kl\5\6\4\3ln\3" +
                    "\2\2\2m8\3\2\2\2mE\3\2\2\2mL\3\2\2\2mT\3\2\2\2m`\3\2\2\2mh\3\2\2\2n{\3" +
                    "\2\2\2op\f\4\2\2pq\5\"\22\2qr\5\6\4\5rz\3\2\2\2st\f\5\2\2tu\7\r\2\2uw" +
                    "\5\6\4\2vx\5\b\5\2wv\3\2\2\2wx\3\2\2\2xz\3\2\2\2yo\3\2\2\2ys\3\2\2\2z" +
                    "}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|\7\3\2\2\2}{\3\2\2\2~\177\7\6\2\2\177\u0080" +
                    "\7\27\2\2\u0080\t\3\2\2\2\u0081\u0082\7\5\2\2\u0082\u0084\5\f\7\2\u0083" +
                    "\u0081\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2" +
                    "\2\2\u0086\13\3\2\2\2\u0087\u0088\7\31\2\2\u0088\u0089\7\4\2\2\u0089\u009e" +
                    "\5\16\b\2\u008a\u008b\7\16\2\2\u008b\u008c\7\31\2\2\u008c\u008d\7\4\2" +
                    "\2\u008d\u008e\5\16\b\2\u008e\u008f\7\17\2\2\u008f\u009e\3\2\2\2\u0090" +
                    "\u0091\7\31\2\2\u0091\u0092\7\23\2\2\u0092\u0093\7\31\2\2\u0093\u0094" +
                    "\7\4\2\2\u0094\u009e\5\16\b\2\u0095\u0096\7\16\2\2\u0096\u0097\7\31\2" +
                    "\2\u0097\u0098\7\23\2\2\u0098\u0099\7\31\2\2\u0099\u009a\7\4\2\2\u009a" +
                    "\u009b\5\16\b\2\u009b\u009c\7\17\2\2\u009c\u009e\3\2\2\2\u009d\u0087\3" +
                    "\2\2\2\u009d\u008a\3\2\2\2\u009d\u0090\3\2\2\2\u009d\u0095\3\2\2\2\u009e" +
                    "\r\3\2\2\2\u009f\u00ac\5\20\t\2\u00a0\u00a1\7\16\2\2\u00a1\u00a6\5\20" +
                    "\t\2\u00a2\u00a3\7\24\2\2\u00a3\u00a5\5\20\t\2\u00a4\u00a2\3\2\2\2\u00a5" +
                    "\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a9\3\2" +
                    "\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00aa\7\17\2\2\u00aa\u00ac\3\2\2\2\u00ab" +
                    "\u009f\3\2\2\2\u00ab\u00a0\3\2\2\2\u00ac\17\3\2\2\2\u00ad\u00b0\5\30\r" +
                    "\2\u00ae\u00b0\5\32\16\2\u00af\u00ad\3\2\2\2\u00af\u00ae\3\2\2\2\u00b0" +
                    "\21\3\2\2\2\u00b1\u00b2\t\2\2\2\u00b2\u00b3\7\3\2\2\u00b3\23\3\2\2\2\u00b4" +
                    "\u00b7\7\b\2\2\u00b5\u00b7\7\7\2\2\u00b6\u00b4\3\2\2\2\u00b6\u00b5\3\2" +
                    "\2\2\u00b7\25\3\2\2\2\u00b8\u00b9\7\31\2\2\u00b9\u00ba\7\4\2\2\u00ba\27" +
                    "\3\2\2\2\u00bb\u00bc\t\2\2\2\u00bc\31\3\2\2\2\u00bd\u00be\7\20\2\2\u00be" +
                    "\u00bf\7\27\2\2\u00bf\u00c0\7\22\2\2\u00c0\u00c1\7\27\2\2\u00c1\u00cc" +
                    "\7\21\2\2\u00c2\u00c4\7\20\2\2\u00c3\u00c5\7\30\2\2\u00c4\u00c3\3\2\2" +
                    "\2\u00c4\u00c5\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c8\7\22\2\2\u00c7" +
                    "\u00c9\7\30\2\2\u00c8\u00c7\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00ca\3" +
                    "\2\2\2\u00ca\u00cc\7\21\2\2\u00cb\u00bd\3\2\2\2\u00cb\u00c2\3\2\2\2\u00cc" +
                    "\33\3\2\2\2\u00cd\u00ce\b\17\1\2\u00ce\u00cf\7\16\2\2\u00cf\u00d0\5\34" +
                    "\17\2\u00d0\u00d1\7\17\2\2\u00d1\u00da\3\2\2\2\u00d2\u00d3\5\36\20\2\u00d3" +
                    "\u00d4\5 \21\2\u00d4\u00d5\5\36\20\2\u00d5\u00da\3\2\2\2\u00d6\u00d7\5" +
                    "$\23\2\u00d7\u00d8\5\34\17\3\u00d8\u00da\3\2\2\2\u00d9\u00cd\3\2\2\2\u00d9" +
                    "\u00d2\3\2\2\2\u00d9\u00d6\3\2\2\2\u00da\u00e1\3\2\2\2\u00db\u00dc\f\4" +
                    "\2\2\u00dc\u00dd\5\"\22\2\u00dd\u00de\5\34\17\5\u00de\u00e0\3\2\2\2\u00df" +
                    "\u00db\3\2\2\2\u00e0\u00e3\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2" +
                    "\2\2\u00e2\35\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e5\t\2\2\2\u00e5\u00e7" +
                    "\7\23\2\2\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e8\3\2\2\2" +
                    "\u00e8\u00e9\7\31\2\2\u00e9\37\3\2\2\2\u00ea\u00eb\t\3\2\2\u00eb!\3\2" +
                    "\2\2\u00ec\u00ed\t\4\2\2\u00ed#\3\2\2\2\u00ee\u00ef\7\26\2\2\u00ef%\3" +
                    "\2\2\2!)\60\63\66:@EILQTZ^ehmwy{\u0085\u009d\u00a6\u00ab\u00af\u00b6\u00c4" +
                    "\u00c8\u00cb\u00d9\u00e1\u00e6";
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

    public EqlParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "root", "query", "queryElem", "proximity", "alignOperator", "alignElem",
                "singleValueOrMultiple", "literalOrInterval", "assignment", "contextConstraint",
                "indexOperator", "queryLiteral", "interval", "globalConstraint", "reference",
                "comparisonOperator", "logicBinaryOperator", "logicUnaryOperator"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "':='", "':'", "'^'", "'~'", "'- _SENT_'", "'- _PAR_'", "'\"'",
                "'&&'", "'='", "'!='", "'<'", "'('", "')'", "'['", "']'", "'..'", "'.'",
                "'|'", "'&'", "'!'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "ASSIGN", "COLON", "EXPONENT", "SIMILARITY", "SENT", "PAR", "QUOTATION",
                "GLOBAL_CONSTRAINT_SEPARATOR", "EQ", "NEQ", "LT", "PAREN_LEFT", "PAREN_RIGHT",
                "BRACKET_LEFT", "BRACKET_RIGHT", "RANGE", "DOT", "OR", "AND", "NOT",
                "NUMBER", "DATE", "WORD", "WS"
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
        return "Eql.g4";
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
                setState(36);
                query();
                setState(39);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == GLOBAL_CONSTRAINT_SEPARATOR) {
                    {
                        setState(37);
                        match(GLOBAL_CONSTRAINT_SEPARATOR);
                        setState(38);
                        globalConstraint(0);
                    }
                }

                setState(41);
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
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(44);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        {
                            setState(43);
                            queryElem(0);
                        }
                    }
                    setState(46);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << NOT) | (1L << NUMBER) | (1L << WORD))) != 0));
                setState(49);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == SIMILARITY) {
                    {
                        setState(48);
                        proximity();
                    }
                }

                setState(52);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == SENT || _la == PAR) {
                    {
                        setState(51);
                        contextConstraint();
                    }
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
                setState(107);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                    case 1: {
                        _localctx = new SequenceContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(56);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == NUMBER || _la == WORD) {
                            {
                                setState(55);
                                assignment();
                            }
                        }

                        setState(58);
                        match(QUOTATION);
                        setState(60);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1: {
                                    {
                                        setState(59);
                                        queryElem(0);
                                    }
                                }
                                break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(62);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
                        } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                        setState(64);
                        match(QUOTATION);
                    }
                    break;
                    case 2: {
                        _localctx = new LiteralContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(67);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
                            case 1: {
                                setState(66);
                                assignment();
                            }
                            break;
                        }
                        setState(69);
                        queryLiteral();
                        setState(71);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
                            case 1: {
                                setState(70);
                                alignOperator();
                            }
                            break;
                        }
                    }
                    break;
                    case 3: {
                        _localctx = new IndexElemContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(74);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                            case 1: {
                                setState(73);
                                assignment();
                            }
                            break;
                        }
                        setState(76);
                        indexOperator();
                        setState(77);
                        singleValueOrMultiple();
                        setState(79);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                            case 1: {
                                setState(78);
                                alignOperator();
                            }
                            break;
                        }
                    }
                    break;
                    case 4: {
                        _localctx = new ParenContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(82);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == NUMBER || _la == WORD) {
                            {
                                setState(81);
                                assignment();
                            }
                        }

                        setState(84);
                        match(PAREN_LEFT);
                        setState(86);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(85);
                                    queryElem(0);
                                }
                            }
                            setState(88);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << NOT) | (1L << NUMBER) | (1L << WORD))) != 0));
                        setState(90);
                        match(PAREN_RIGHT);
                        setState(92);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
                            case 1: {
                                setState(91);
                                proximity();
                            }
                            break;
                        }
                    }
                    break;
                    case 5: {
                        _localctx = new OrderContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(94);
                        assignment();
                        setState(95);
                        queryElem(0);
                        setState(96);
                        match(LT);
                        setState(97);
                        queryElem(0);
                        setState(99);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                            case 1: {
                                setState(98);
                                proximity();
                            }
                            break;
                        }
                    }
                    break;
                    case 6: {
                        _localctx = new LogicUnaryOperationContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(102);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == NUMBER || _la == WORD) {
                            {
                                setState(101);
                                assignment();
                            }
                        }

                        setState(104);
                        logicUnaryOperator();
                        setState(105);
                        queryElem(1);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(121);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(119);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
                                case 1: {
                                    _localctx = new LogicBinaryOperationContext(new QueryElemContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryElem);
                                    setState(109);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(110);
                                    logicBinaryOperator();
                                    setState(111);
                                    queryElem(3);
                                }
                                break;
                                case 2: {
                                    _localctx = new OrderContext(new QueryElemContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryElem);
                                    setState(113);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(114);
                                    match(LT);
                                    setState(115);
                                    queryElem(0);
                                    setState(117);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
                                        case 1: {
                                            setState(116);
                                            proximity();
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    setState(123);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
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

    public final ProximityContext proximity() throws RecognitionException {
        ProximityContext _localctx = new ProximityContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_proximity);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(124);
                match(SIMILARITY);
                setState(125);
                match(NUMBER);
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

    public final AlignOperatorContext alignOperator() throws RecognitionException {
        AlignOperatorContext _localctx = new AlignOperatorContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_alignOperator);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(129);
                _errHandler.sync(this);
                _alt = 1;
                do {
                    switch (_alt) {
                        case 1: {
                            {
                                setState(127);
                                match(EXPONENT);
                                setState(128);
                                alignElem();
                            }
                        }
                        break;
                        default:
                            throw new NoViableAltException(this);
                    }
                    setState(131);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 19, _ctx);
                } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
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

    public final AlignElemContext alignElem() throws RecognitionException {
        AlignElemContext _localctx = new AlignElemContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_alignElem);
        try {
            setState(155);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
                case 1:
                    _localctx = new IndexContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(133);
                    match(WORD);
                    setState(134);
                    match(COLON);
                    setState(135);
                    singleValueOrMultiple();
                }
                break;
                case 2:
                    _localctx = new IndexContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(136);
                    match(PAREN_LEFT);
                    setState(137);
                    match(WORD);
                    setState(138);
                    match(COLON);
                    setState(139);
                    singleValueOrMultiple();
                    setState(140);
                    match(PAREN_RIGHT);
                }
                break;
                case 3:
                    _localctx = new EntityAttributeContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(142);
                    match(WORD);
                    setState(143);
                    match(DOT);
                    setState(144);
                    match(WORD);
                    setState(145);
                    match(COLON);
                    setState(146);
                    singleValueOrMultiple();
                }
                break;
                case 4:
                    _localctx = new EntityAttributeContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(147);
                    match(PAREN_LEFT);
                    setState(148);
                    match(WORD);
                    setState(149);
                    match(DOT);
                    setState(150);
                    match(WORD);
                    setState(151);
                    match(COLON);
                    setState(152);
                    singleValueOrMultiple();
                    setState(153);
                    match(PAREN_RIGHT);
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

    public final SingleValueOrMultipleContext singleValueOrMultiple() throws RecognitionException {
        SingleValueOrMultipleContext _localctx = new SingleValueOrMultipleContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_singleValueOrMultiple);
        int _la;
        try {
            setState(169);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case BRACKET_LEFT:
                case NUMBER:
                case WORD:
                    _localctx = new SingleValueContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(157);
                    literalOrInterval();
                }
                break;
                case PAREN_LEFT:
                    _localctx = new MultipleValuesContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(158);
                    match(PAREN_LEFT);
                    setState(159);
                    literalOrInterval();
                    setState(164);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    while (_la == OR) {
                        {
                            {
                                setState(160);
                                match(OR);
                                setState(161);
                                literalOrInterval();
                            }
                        }
                        setState(166);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    }
                    setState(167);
                    match(PAREN_RIGHT);
                }
                break;
                default:
                    throw new NoViableAltException(this);
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

    public final LiteralOrIntervalContext literalOrInterval() throws RecognitionException {
        LiteralOrIntervalContext _localctx = new LiteralOrIntervalContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_literalOrInterval);
        try {
            setState(173);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case NUMBER:
                case WORD:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(171);
                    queryLiteral();
                }
                break;
                case BRACKET_LEFT:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(172);
                    interval();
                }
                break;
                default:
                    throw new NoViableAltException(this);
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

    public final AssignmentContext assignment() throws RecognitionException {
        AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_assignment);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(175);
                _la = _input.LA(1);
                if (!(_la == NUMBER || _la == WORD)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(176);
                match(ASSIGN);
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

    public final ContextConstraintContext contextConstraint() throws RecognitionException {
        ContextConstraintContext _localctx = new ContextConstraintContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_contextConstraint);
        try {
            setState(180);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case PAR:
                    _localctx = new ParagraphContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(178);
                    match(PAR);
                }
                break;
                case SENT:
                    _localctx = new SentenceContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(179);
                    match(SENT);
                }
                break;
                default:
                    throw new NoViableAltException(this);
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
        enterRule(_localctx, 20, RULE_indexOperator);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(182);
                match(WORD);
                setState(183);
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

    public final QueryLiteralContext queryLiteral() throws RecognitionException {
        QueryLiteralContext _localctx = new QueryLiteralContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_queryLiteral);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(185);
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

    public final IntervalContext interval() throws RecognitionException {
        IntervalContext _localctx = new IntervalContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_interval);
        int _la;
        try {
            setState(201);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 27, _ctx)) {
                case 1:
                    _localctx = new NumberRangeContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(187);
                    match(BRACKET_LEFT);
                    setState(188);
                    match(NUMBER);
                    setState(189);
                    match(RANGE);
                    setState(190);
                    match(NUMBER);
                    setState(191);
                    match(BRACKET_RIGHT);
                }
                break;
                case 2:
                    _localctx = new DateRangeContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(192);
                    match(BRACKET_LEFT);
                    setState(194);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == DATE) {
                        {
                            setState(193);
                            match(DATE);
                        }
                    }

                    setState(196);
                    match(RANGE);
                    setState(198);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == DATE) {
                        {
                            setState(197);
                            match(DATE);
                        }
                    }

                    setState(200);
                    match(BRACKET_RIGHT);
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

    public final GlobalConstraintContext globalConstraint() throws RecognitionException {
        return globalConstraint(0);
    }

    private GlobalConstraintContext globalConstraint(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        GlobalConstraintContext _localctx = new GlobalConstraintContext(_ctx, _parentState);
        GlobalConstraintContext _prevctx = _localctx;
        int _startState = 26;
        enterRecursionRule(_localctx, 26, RULE_globalConstraint, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(215);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case PAREN_LEFT: {
                        _localctx = new ParensContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(204);
                        match(PAREN_LEFT);
                        setState(205);
                        globalConstraint(0);
                        setState(206);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case NUMBER:
                    case WORD: {
                        _localctx = new ComparisonContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(208);
                        reference();
                        setState(209);
                        comparisonOperator();
                        setState(210);
                        reference();
                    }
                    break;
                    case NOT: {
                        _localctx = new ConstraintLogicUnaryOperationContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(212);
                        logicUnaryOperator();
                        setState(213);
                        globalConstraint(1);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(223);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 29, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new ConstraintLogicBinaryOperationContext(new GlobalConstraintContext(_parentctx, _parentState));
                                pushNewRecursionContext(_localctx, _startState, RULE_globalConstraint);
                                setState(217);
                                if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                setState(218);
                                logicBinaryOperator();
                                setState(219);
                                globalConstraint(3);
                            }
                        }
                    }
                    setState(225);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 29, _ctx);
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
        enterRule(_localctx, 28, RULE_reference);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(228);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 30, _ctx)) {
                    case 1: {
                        setState(226);
                        _la = _input.LA(1);
                        if (!(_la == NUMBER || _la == WORD)) {
                            _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(227);
                        match(DOT);
                    }
                    break;
                }
                setState(230);
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

    public final ComparisonOperatorContext comparisonOperator() throws RecognitionException {
        ComparisonOperatorContext _localctx = new ComparisonOperatorContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_comparisonOperator);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(232);
                _la = _input.LA(1);
                if (!(_la == EQ || _la == NEQ)) {
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

    public final LogicBinaryOperatorContext logicBinaryOperator() throws RecognitionException {
        LogicBinaryOperatorContext _localctx = new LogicBinaryOperatorContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_logicBinaryOperator);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(234);
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

    public final LogicUnaryOperatorContext logicUnaryOperator() throws RecognitionException {
        LogicUnaryOperatorContext _localctx = new LogicUnaryOperatorContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_logicUnaryOperator);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(236);
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
            case 13:
                return globalConstraint_sempred((GlobalConstraintContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean queryElem_sempred(QueryElemContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 2);
            case 1:
                return precpred(_ctx, 3);
        }
        return true;
    }

    private boolean globalConstraint_sempred(GlobalConstraintContext _localctx, int predIndex) {
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

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(EqlParser.EOF, 0);
        }

        public TerminalNode GLOBAL_CONSTRAINT_SEPARATOR() {
            return getToken(EqlParser.GLOBAL_CONSTRAINT_SEPARATOR, 0);
        }

        public GlobalConstraintContext globalConstraint() {
            return getRuleContext(GlobalConstraintContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_root;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterRoot(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitRoot(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitRoot(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class QueryContext extends ParserRuleContext {
        public QueryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
        }

        public ProximityContext proximity() {
            return getRuleContext(ProximityContext.class, 0);
        }

        public ContextConstraintContext contextConstraint() {
            return getRuleContext(ContextConstraintContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_query;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterQuery(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitQuery(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitQuery(this);
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
            return getTokens(EqlParser.QUOTATION);
        }

        public TerminalNode QUOTATION(int i) {
            return getToken(EqlParser.QUOTATION, i);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterSequence(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitSequence(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitSequence(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ParenContext extends QueryElemContext {
        public ParenContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(EqlParser.PAREN_LEFT, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(EqlParser.PAREN_RIGHT, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
        }

        public ProximityContext proximity() {
            return getRuleContext(ProximityContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterParen(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitParen(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitParen(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class LogicUnaryOperationContext extends QueryElemContext {
        public LogicUnaryOperationContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public LogicUnaryOperatorContext logicUnaryOperator() {
            return getRuleContext(LogicUnaryOperatorContext.class, 0);
        }

        public QueryElemContext queryElem() {
            return getRuleContext(QueryElemContext.class, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterLogicUnaryOperation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitLogicUnaryOperation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor)
                return ((EqlVisitor<? extends T>) visitor).visitLogicUnaryOperation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IndexElemContext extends QueryElemContext {
        public IndexElemContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public IndexOperatorContext indexOperator() {
            return getRuleContext(IndexOperatorContext.class, 0);
        }

        public SingleValueOrMultipleContext singleValueOrMultiple() {
            return getRuleContext(SingleValueOrMultipleContext.class, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public AlignOperatorContext alignOperator() {
            return getRuleContext(AlignOperatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterIndexElem(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitIndexElem(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitIndexElem(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class LogicBinaryOperationContext extends QueryElemContext {
        public LogicBinaryOperationContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
        }

        public LogicBinaryOperatorContext logicBinaryOperator() {
            return getRuleContext(LogicBinaryOperatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterLogicBinaryOperation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitLogicBinaryOperation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor)
                return ((EqlVisitor<? extends T>) visitor).visitLogicBinaryOperation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class LiteralContext extends QueryElemContext {
        public LiteralContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public QueryLiteralContext queryLiteral() {
            return getRuleContext(QueryLiteralContext.class, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public AlignOperatorContext alignOperator() {
            return getRuleContext(AlignOperatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterLiteral(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitLiteral(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitLiteral(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class OrderContext extends QueryElemContext {
        public OrderContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public List<QueryElemContext> queryElem() {
            return getRuleContexts(QueryElemContext.class);
        }

        public QueryElemContext queryElem(int i) {
            return getRuleContext(QueryElemContext.class, i);
        }

        public TerminalNode LT() {
            return getToken(EqlParser.LT, 0);
        }

        public ProximityContext proximity() {
            return getRuleContext(ProximityContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterOrder(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitOrder(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitOrder(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ProximityContext extends ParserRuleContext {
        public ProximityContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode SIMILARITY() {
            return getToken(EqlParser.SIMILARITY, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(EqlParser.NUMBER, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_proximity;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterProximity(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitProximity(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitProximity(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AlignOperatorContext extends ParserRuleContext {
        public AlignOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> EXPONENT() {
            return getTokens(EqlParser.EXPONENT);
        }

        public TerminalNode EXPONENT(int i) {
            return getToken(EqlParser.EXPONENT, i);
        }

        public List<AlignElemContext> alignElem() {
            return getRuleContexts(AlignElemContext.class);
        }

        public AlignElemContext alignElem(int i) {
            return getRuleContext(AlignElemContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_alignOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterAlignOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitAlignOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitAlignOperator(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AlignElemContext extends ParserRuleContext {
        public AlignElemContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public AlignElemContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_alignElem;
        }

        public void copyFrom(AlignElemContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class EntityAttributeContext extends AlignElemContext {
        public EntityAttributeContext(AlignElemContext ctx) {
            copyFrom(ctx);
        }

        public List<TerminalNode> WORD() {
            return getTokens(EqlParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(EqlParser.WORD, i);
        }

        public TerminalNode DOT() {
            return getToken(EqlParser.DOT, 0);
        }

        public TerminalNode COLON() {
            return getToken(EqlParser.COLON, 0);
        }

        public SingleValueOrMultipleContext singleValueOrMultiple() {
            return getRuleContext(SingleValueOrMultipleContext.class, 0);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(EqlParser.PAREN_LEFT, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(EqlParser.PAREN_RIGHT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterEntityAttribute(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitEntityAttribute(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitEntityAttribute(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IndexContext extends AlignElemContext {
        public IndexContext(AlignElemContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode WORD() {
            return getToken(EqlParser.WORD, 0);
        }

        public TerminalNode COLON() {
            return getToken(EqlParser.COLON, 0);
        }

        public SingleValueOrMultipleContext singleValueOrMultiple() {
            return getRuleContext(SingleValueOrMultipleContext.class, 0);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(EqlParser.PAREN_LEFT, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(EqlParser.PAREN_RIGHT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterIndex(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitIndex(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitIndex(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SingleValueOrMultipleContext extends ParserRuleContext {
        public SingleValueOrMultipleContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public SingleValueOrMultipleContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_singleValueOrMultiple;
        }

        public void copyFrom(SingleValueOrMultipleContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class MultipleValuesContext extends SingleValueOrMultipleContext {
        public MultipleValuesContext(SingleValueOrMultipleContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(EqlParser.PAREN_LEFT, 0);
        }

        public List<LiteralOrIntervalContext> literalOrInterval() {
            return getRuleContexts(LiteralOrIntervalContext.class);
        }

        public LiteralOrIntervalContext literalOrInterval(int i) {
            return getRuleContext(LiteralOrIntervalContext.class, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(EqlParser.PAREN_RIGHT, 0);
        }

        public List<TerminalNode> OR() {
            return getTokens(EqlParser.OR);
        }

        public TerminalNode OR(int i) {
            return getToken(EqlParser.OR, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterMultipleValues(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitMultipleValues(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitMultipleValues(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SingleValueContext extends SingleValueOrMultipleContext {
        public SingleValueContext(SingleValueOrMultipleContext ctx) {
            copyFrom(ctx);
        }

        public LiteralOrIntervalContext literalOrInterval() {
            return getRuleContext(LiteralOrIntervalContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterSingleValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitSingleValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitSingleValue(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class LiteralOrIntervalContext extends ParserRuleContext {
        public LiteralOrIntervalContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public QueryLiteralContext queryLiteral() {
            return getRuleContext(QueryLiteralContext.class, 0);
        }

        public IntervalContext interval() {
            return getRuleContext(IntervalContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_literalOrInterval;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterLiteralOrInterval(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitLiteralOrInterval(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitLiteralOrInterval(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AssignmentContext extends ParserRuleContext {
        public AssignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ASSIGN() {
            return getToken(EqlParser.ASSIGN, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(EqlParser.NUMBER, 0);
        }

        public TerminalNode WORD() {
            return getToken(EqlParser.WORD, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assignment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterAssignment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitAssignment(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitAssignment(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ContextConstraintContext extends ParserRuleContext {
        public ContextConstraintContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ContextConstraintContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_contextConstraint;
        }

        public void copyFrom(ContextConstraintContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SentenceContext extends ContextConstraintContext {
        public SentenceContext(ContextConstraintContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode SENT() {
            return getToken(EqlParser.SENT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterSentence(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitSentence(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitSentence(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ParagraphContext extends ContextConstraintContext {
        public ParagraphContext(ContextConstraintContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode PAR() {
            return getToken(EqlParser.PAR, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterParagraph(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitParagraph(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitParagraph(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IndexOperatorContext extends ParserRuleContext {
        public IndexOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode WORD() {
            return getToken(EqlParser.WORD, 0);
        }

        public TerminalNode COLON() {
            return getToken(EqlParser.COLON, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_indexOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterIndexOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitIndexOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitIndexOperator(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class QueryLiteralContext extends ParserRuleContext {
        public QueryLiteralContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode WORD() {
            return getToken(EqlParser.WORD, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(EqlParser.NUMBER, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_queryLiteral;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterQueryLiteral(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitQueryLiteral(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitQueryLiteral(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IntervalContext extends ParserRuleContext {
        public IntervalContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public IntervalContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_interval;
        }

        public void copyFrom(IntervalContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class DateRangeContext extends IntervalContext {
        public DateRangeContext(IntervalContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode BRACKET_LEFT() {
            return getToken(EqlParser.BRACKET_LEFT, 0);
        }

        public TerminalNode RANGE() {
            return getToken(EqlParser.RANGE, 0);
        }

        public TerminalNode BRACKET_RIGHT() {
            return getToken(EqlParser.BRACKET_RIGHT, 0);
        }

        public List<TerminalNode> DATE() {
            return getTokens(EqlParser.DATE);
        }

        public TerminalNode DATE(int i) {
            return getToken(EqlParser.DATE, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterDateRange(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitDateRange(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitDateRange(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NumberRangeContext extends IntervalContext {
        public NumberRangeContext(IntervalContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode BRACKET_LEFT() {
            return getToken(EqlParser.BRACKET_LEFT, 0);
        }

        public List<TerminalNode> NUMBER() {
            return getTokens(EqlParser.NUMBER);
        }

        public TerminalNode NUMBER(int i) {
            return getToken(EqlParser.NUMBER, i);
        }

        public TerminalNode RANGE() {
            return getToken(EqlParser.RANGE, 0);
        }

        public TerminalNode BRACKET_RIGHT() {
            return getToken(EqlParser.BRACKET_RIGHT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterNumberRange(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitNumberRange(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitNumberRange(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class GlobalConstraintContext extends ParserRuleContext {
        public GlobalConstraintContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public GlobalConstraintContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_globalConstraint;
        }

        public void copyFrom(GlobalConstraintContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class ParensContext extends GlobalConstraintContext {
        public ParensContext(GlobalConstraintContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(EqlParser.PAREN_LEFT, 0);
        }

        public GlobalConstraintContext globalConstraint() {
            return getRuleContext(GlobalConstraintContext.class, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(EqlParser.PAREN_RIGHT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterParens(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitParens(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitParens(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ComparisonContext extends GlobalConstraintContext {
        public ComparisonContext(GlobalConstraintContext ctx) {
            copyFrom(ctx);
        }

        public List<ReferenceContext> reference() {
            return getRuleContexts(ReferenceContext.class);
        }

        public ReferenceContext reference(int i) {
            return getRuleContext(ReferenceContext.class, i);
        }

        public ComparisonOperatorContext comparisonOperator() {
            return getRuleContext(ComparisonOperatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterComparison(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitComparison(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitComparison(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ConstraintLogicUnaryOperationContext extends GlobalConstraintContext {
        public ConstraintLogicUnaryOperationContext(GlobalConstraintContext ctx) {
            copyFrom(ctx);
        }

        public LogicUnaryOperatorContext logicUnaryOperator() {
            return getRuleContext(LogicUnaryOperatorContext.class, 0);
        }

        public GlobalConstraintContext globalConstraint() {
            return getRuleContext(GlobalConstraintContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterConstraintLogicUnaryOperation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitConstraintLogicUnaryOperation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor)
                return ((EqlVisitor<? extends T>) visitor).visitConstraintLogicUnaryOperation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ConstraintLogicBinaryOperationContext extends GlobalConstraintContext {
        public ConstraintLogicBinaryOperationContext(GlobalConstraintContext ctx) {
            copyFrom(ctx);
        }

        public List<GlobalConstraintContext> globalConstraint() {
            return getRuleContexts(GlobalConstraintContext.class);
        }

        public GlobalConstraintContext globalConstraint(int i) {
            return getRuleContext(GlobalConstraintContext.class, i);
        }

        public LogicBinaryOperatorContext logicBinaryOperator() {
            return getRuleContext(LogicBinaryOperatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterConstraintLogicBinaryOperation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitConstraintLogicBinaryOperation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor)
                return ((EqlVisitor<? extends T>) visitor).visitConstraintLogicBinaryOperation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ReferenceContext extends ParserRuleContext {
        public ReferenceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> WORD() {
            return getTokens(EqlParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(EqlParser.WORD, i);
        }

        public TerminalNode DOT() {
            return getToken(EqlParser.DOT, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(EqlParser.NUMBER, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_reference;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterReference(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitReference(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitReference(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ComparisonOperatorContext extends ParserRuleContext {
        public ComparisonOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EQ() {
            return getToken(EqlParser.EQ, 0);
        }

        public TerminalNode NEQ() {
            return getToken(EqlParser.NEQ, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_comparisonOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterComparisonOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitComparisonOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitComparisonOperator(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class LogicBinaryOperatorContext extends ParserRuleContext {
        public LogicBinaryOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode AND() {
            return getToken(EqlParser.AND, 0);
        }

        public TerminalNode OR() {
            return getToken(EqlParser.OR, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_logicBinaryOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterLogicBinaryOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitLogicBinaryOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor)
                return ((EqlVisitor<? extends T>) visitor).visitLogicBinaryOperator(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class LogicUnaryOperatorContext extends ParserRuleContext {
        public LogicUnaryOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode NOT() {
            return getToken(EqlParser.NOT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_logicUnaryOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterLogicUnaryOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitLogicUnaryOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitLogicUnaryOperator(this);
            else return visitor.visitChildren(this);
        }
    }
}