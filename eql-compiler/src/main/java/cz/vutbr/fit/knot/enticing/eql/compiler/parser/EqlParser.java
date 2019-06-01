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
            ASSIGN = 1, MINUS = 2, COLON = 3, EXPONENT = 4, SIMILARITY = 5, SENT = 6, PAR = 7, QUOTATION = 8,
            QUERY_CONSTRAINT_SEPARATOR = 9, EQ = 10, NEQ = 11, LT = 12, PAREN_LEFT = 13, PAREN_RIGHT = 14,
            BRACKET_LEFT = 15, BRACKET_RIGHT = 16, RANGE = 17, DOT = 18, OR = 19, AND = 20, NOT = 21,
            NUMBER = 22, DATE = 23, WORD = 24, WS = 25;
    public static final int
            RULE_root = 0, RULE_query = 1, RULE_queryElem = 2, RULE_proximity = 3,
            RULE_alignOperator = 4, RULE_alignElem = 5, RULE_assignment = 6, RULE_contextConstraint = 7,
            RULE_indexOperator = 8, RULE_queryLiteral = 9, RULE_interval = 10, RULE_constraint = 11,
            RULE_reference = 12, RULE_comparisonOperator = 13, RULE_binaryOperator = 14,
            RULE_unaryOperator = 15;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\33\u00ea\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2" +
                    "\3\2\5\2&\n\2\3\2\3\2\3\3\6\3+\n\3\r\3\16\3,\3\3\5\3\60\n\3\3\3\5\3\63" +
                    "\n\3\3\4\3\4\5\4\67\n\4\3\4\3\4\6\4;\n\4\r\4\16\4<\3\4\3\4\3\4\5\4B\n" +
                    "\4\3\4\5\4E\n\4\3\4\3\4\5\4I\n\4\3\4\5\4L\n\4\3\4\3\4\3\4\3\4\3\4\7\4" +
                    "S\n\4\f\4\16\4V\13\4\3\4\3\4\5\4Z\n\4\3\4\5\4]\n\4\3\4\3\4\6\4a\n\4\r" +
                    "\4\16\4b\3\4\3\4\5\4g\n\4\3\4\3\4\3\4\3\4\3\4\5\4n\n\4\3\4\5\4q\n\4\3" +
                    "\4\3\4\3\4\5\4v\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u0080\n\4\7\4" +
                    "\u0082\n\4\f\4\16\4\u0085\13\4\3\5\3\5\3\5\3\6\3\6\6\6\u008c\n\6\r\6\16" +
                    "\6\u008d\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3" +
                    "\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u00a6\n\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\5" +
                    "\t\u00af\n\t\3\n\3\n\3\n\3\13\3\13\3\13\5\13\u00b7\n\13\3\f\3\f\3\f\3" +
                    "\f\3\f\3\f\3\f\5\f\u00c0\n\f\3\f\3\f\5\f\u00c4\n\f\3\f\5\f\u00c7\n\f\3" +
                    "\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00d5\n\r\3\r\3\r\3" +
                    "\r\3\r\7\r\u00db\n\r\f\r\16\r\u00de\13\r\3\16\3\16\3\16\3\16\3\17\3\17" +
                    "\3\20\3\20\3\21\3\21\3\21\2\4\6\30\22\2\4\6\b\n\f\16\20\22\24\26\30\32" +
                    "\34\36 \2\5\4\2\30\30\32\32\3\2\f\r\3\2\25\26\2\u00ff\2\"\3\2\2\2\4*\3" +
                    "\2\2\2\6u\3\2\2\2\b\u0086\3\2\2\2\n\u008b\3\2\2\2\f\u00a5\3\2\2\2\16\u00a7" +
                    "\3\2\2\2\20\u00ae\3\2\2\2\22\u00b0\3\2\2\2\24\u00b6\3\2\2\2\26\u00c6\3" +
                    "\2\2\2\30\u00d4\3\2\2\2\32\u00df\3\2\2\2\34\u00e3\3\2\2\2\36\u00e5\3\2" +
                    "\2\2 \u00e7\3\2\2\2\"%\5\4\3\2#$\7\13\2\2$&\5\30\r\2%#\3\2\2\2%&\3\2\2" +
                    "\2&\'\3\2\2\2\'(\7\2\2\3(\3\3\2\2\2)+\5\6\4\2*)\3\2\2\2+,\3\2\2\2,*\3" +
                    "\2\2\2,-\3\2\2\2-/\3\2\2\2.\60\5\b\5\2/.\3\2\2\2/\60\3\2\2\2\60\62\3\2" +
                    "\2\2\61\63\5\20\t\2\62\61\3\2\2\2\62\63\3\2\2\2\63\5\3\2\2\2\64\66\b\4" +
                    "\1\2\65\67\5\16\b\2\66\65\3\2\2\2\66\67\3\2\2\2\678\3\2\2\28:\7\n\2\2" +
                    "9;\5\6\4\2:9\3\2\2\2;<\3\2\2\2<:\3\2\2\2<=\3\2\2\2=>\3\2\2\2>?\7\n\2\2" +
                    "?v\3\2\2\2@B\5\16\b\2A@\3\2\2\2AB\3\2\2\2BD\3\2\2\2CE\5\22\n\2DC\3\2\2" +
                    "\2DE\3\2\2\2EF\3\2\2\2FH\5\24\13\2GI\5\n\6\2HG\3\2\2\2HI\3\2\2\2Iv\3\2" +
                    "\2\2JL\5\16\b\2KJ\3\2\2\2KL\3\2\2\2LM\3\2\2\2MN\5\22\n\2NO\7\17\2\2OT" +
                    "\7\32\2\2PQ\7\25\2\2QS\7\32\2\2RP\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2" +
                    "\2UW\3\2\2\2VT\3\2\2\2WY\7\20\2\2XZ\5\n\6\2YX\3\2\2\2YZ\3\2\2\2Zv\3\2" +
                    "\2\2[]\5\16\b\2\\[\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^`\7\17\2\2_a\5\6\4\2`" +
                    "_\3\2\2\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2\2cd\3\2\2\2df\7\20\2\2eg\5\b\5\2" +
                    "fe\3\2\2\2fg\3\2\2\2gv\3\2\2\2hi\5\16\b\2ij\5\6\4\2jk\7\16\2\2km\5\6\4" +
                    "\2ln\5\b\5\2ml\3\2\2\2mn\3\2\2\2nv\3\2\2\2oq\5\16\b\2po\3\2\2\2pq\3\2" +
                    "\2\2qr\3\2\2\2rs\5 \21\2st\5\6\4\3tv\3\2\2\2u\64\3\2\2\2uA\3\2\2\2uK\3" +
                    "\2\2\2u\\\3\2\2\2uh\3\2\2\2up\3\2\2\2v\u0083\3\2\2\2wx\f\4\2\2xy\5\36" +
                    "\20\2yz\5\6\4\5z\u0082\3\2\2\2{|\f\5\2\2|}\7\16\2\2}\177\5\6\4\2~\u0080" +
                    "\5\b\5\2\177~\3\2\2\2\177\u0080\3\2\2\2\u0080\u0082\3\2\2\2\u0081w\3\2" +
                    "\2\2\u0081{\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084" +
                    "\3\2\2\2\u0084\7\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0087\7\7\2\2\u0087" +
                    "\u0088\7\30\2\2\u0088\t\3\2\2\2\u0089\u008a\7\6\2\2\u008a\u008c\5\f\7" +
                    "\2\u008b\u0089\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e" +
                    "\3\2\2\2\u008e\13\3\2\2\2\u008f\u0090\7\32\2\2\u0090\u0091\7\5\2\2\u0091" +
                    "\u00a6\5\24\13\2\u0092\u0093\7\17\2\2\u0093\u0094\7\32\2\2\u0094\u0095" +
                    "\7\5\2\2\u0095\u0096\5\24\13\2\u0096\u0097\7\20\2\2\u0097\u00a6\3\2\2" +
                    "\2\u0098\u0099\7\32\2\2\u0099\u009a\7\24\2\2\u009a\u009b\7\32\2\2\u009b" +
                    "\u009c\7\5\2\2\u009c\u00a6\5\24\13\2\u009d\u009e\7\17\2\2\u009e\u009f" +
                    "\7\32\2\2\u009f\u00a0\7\24\2\2\u00a0\u00a1\7\32\2\2\u00a1\u00a2\7\5\2" +
                    "\2\u00a2\u00a3\5\24\13\2\u00a3\u00a4\7\20\2\2\u00a4\u00a6\3\2\2\2\u00a5" +
                    "\u008f\3\2\2\2\u00a5\u0092\3\2\2\2\u00a5\u0098\3\2\2\2\u00a5\u009d\3\2" +
                    "\2\2\u00a6\r\3\2\2\2\u00a7\u00a8\t\2\2\2\u00a8\u00a9\7\3\2\2\u00a9\17" +
                    "\3\2\2\2\u00aa\u00ab\7\4\2\2\u00ab\u00af\7\t\2\2\u00ac\u00ad\7\4\2\2\u00ad" +
                    "\u00af\7\b\2\2\u00ae\u00aa\3\2\2\2\u00ae\u00ac\3\2\2\2\u00af\21\3\2\2" +
                    "\2\u00b0\u00b1\7\32\2\2\u00b1\u00b2\7\5\2\2\u00b2\23\3\2\2\2\u00b3\u00b7" +
                    "\7\32\2\2\u00b4\u00b7\7\30\2\2\u00b5\u00b7\5\26\f\2\u00b6\u00b3\3\2\2" +
                    "\2\u00b6\u00b4\3\2\2\2\u00b6\u00b5\3\2\2\2\u00b7\25\3\2\2\2\u00b8\u00b9" +
                    "\7\21\2\2\u00b9\u00ba\7\30\2\2\u00ba\u00bb\7\23\2\2\u00bb\u00bc\7\30\2" +
                    "\2\u00bc\u00c7\7\22\2\2\u00bd\u00bf\7\21\2\2\u00be\u00c0\7\31\2\2\u00bf" +
                    "\u00be\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c3\7\23" +
                    "\2\2\u00c2\u00c4\7\31\2\2\u00c3\u00c2\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4" +
                    "\u00c5\3\2\2\2\u00c5\u00c7\7\22\2\2\u00c6\u00b8\3\2\2\2\u00c6\u00bd\3" +
                    "\2\2\2\u00c7\27\3\2\2\2\u00c8\u00c9\b\r\1\2\u00c9\u00ca\7\17\2\2\u00ca" +
                    "\u00cb\5\30\r\2\u00cb\u00cc\7\20\2\2\u00cc\u00d5\3\2\2\2\u00cd\u00ce\5" +
                    "\32\16\2\u00ce\u00cf\5\34\17\2\u00cf\u00d0\5\32\16\2\u00d0\u00d5\3\2\2" +
                    "\2\u00d1\u00d2\5 \21\2\u00d2\u00d3\5\30\r\3\u00d3\u00d5\3\2\2\2\u00d4" +
                    "\u00c8\3\2\2\2\u00d4\u00cd\3\2\2\2\u00d4\u00d1\3\2\2\2\u00d5\u00dc\3\2" +
                    "\2\2\u00d6\u00d7\f\4\2\2\u00d7\u00d8\5\36\20\2\u00d8\u00d9\5\30\r\5\u00d9" +
                    "\u00db\3\2\2\2\u00da\u00d6\3\2\2\2\u00db\u00de\3\2\2\2\u00dc\u00da\3\2" +
                    "\2\2\u00dc\u00dd\3\2\2\2\u00dd\31\3\2\2\2\u00de\u00dc\3\2\2\2\u00df\u00e0" +
                    "\t\2\2\2\u00e0\u00e1\7\24\2\2\u00e1\u00e2\7\32\2\2\u00e2\33\3\2\2\2\u00e3" +
                    "\u00e4\t\3\2\2\u00e4\35\3\2\2\2\u00e5\u00e6\t\4\2\2\u00e6\37\3\2\2\2\u00e7" +
                    "\u00e8\7\27\2\2\u00e8!\3\2\2\2 %,/\62\66<ADHKTY\\bfmpu\177\u0081\u0083" +
                    "\u008d\u00a5\u00ae\u00b6\u00bf\u00c3\u00c6\u00d4\u00dc";
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
                "assignment", "contextConstraint", "indexOperator", "queryLiteral", "interval",
                "constraint", "reference", "comparisonOperator", "binaryOperator", "unaryOperator"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "':='", "'-'", "':'", "'^'", "'~'", "'_SENT_'", "'_PAR_'", "'\"'",
                "'&&'", "'='", "'!='", "'<'", "'('", "')'", "'['", "']'", "'..'", "'.'",
                "'|'", "'&'", "'!'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "ASSIGN", "MINUS", "COLON", "EXPONENT", "SIMILARITY", "SENT", "PAR",
                "QUOTATION", "QUERY_CONSTRAINT_SEPARATOR", "EQ", "NEQ", "LT", "PAREN_LEFT",
                "PAREN_RIGHT", "BRACKET_LEFT", "BRACKET_RIGHT", "RANGE", "DOT", "OR",
                "AND", "NOT", "NUMBER", "DATE", "WORD", "WS"
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
                setState(32);
                query();
                setState(35);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == QUERY_CONSTRAINT_SEPARATOR) {
                    {
                        setState(33);
                        match(QUERY_CONSTRAINT_SEPARATOR);
                        setState(34);
                        constraint(0);
                    }
                }

                setState(37);
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
                setState(40);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        {
                            setState(39);
                            queryElem(0);
                        }
                    }
                    setState(42);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << BRACKET_LEFT) | (1L << NOT) | (1L << NUMBER) | (1L << WORD))) != 0));
                setState(45);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == SIMILARITY) {
                    {
                        setState(44);
                        proximity();
                    }
                }

                setState(48);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == MINUS) {
                    {
                        setState(47);
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
                setState(115);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
                    case 1: {
                        _localctx = new SequenceContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(52);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == NUMBER || _la == WORD) {
                            {
                                setState(51);
                                assignment();
                            }
                        }

                        setState(54);
                        match(QUOTATION);
                        setState(56);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1: {
                                    {
                                        setState(55);
                                        queryElem(0);
                                    }
                                }
                                break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(58);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
                        } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                        setState(60);
                        match(QUOTATION);
                    }
                    break;
                    case 2: {
                        _localctx = new IndexWithSingleValueContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(63);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
                            case 1: {
                                setState(62);
                                assignment();
                            }
                            break;
                        }
                        setState(66);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
                            case 1: {
                                setState(65);
                                indexOperator();
                            }
                            break;
                        }
                        setState(68);
                        queryLiteral();
                        setState(70);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                            case 1: {
                                setState(69);
                                alignOperator();
                            }
                            break;
                        }
                    }
                    break;
                    case 3: {
                        _localctx = new IndexWithMultipleValuesContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(73);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                            case 1: {
                                setState(72);
                                assignment();
                            }
                            break;
                        }
                        setState(75);
                        indexOperator();
                        setState(76);
                        match(PAREN_LEFT);
                        setState(77);
                        match(WORD);
                        setState(82);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == OR) {
                            {
                                {
                                    setState(78);
                                    match(OR);
                                    setState(79);
                                    match(WORD);
                                }
                            }
                            setState(84);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                        setState(85);
                        match(PAREN_RIGHT);
                        setState(87);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
                            case 1: {
                                setState(86);
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
                        setState(90);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == NUMBER || _la == WORD) {
                            {
                                setState(89);
                                assignment();
                            }
                        }

                        setState(92);
                        match(PAREN_LEFT);
                        setState(94);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(93);
                                    queryElem(0);
                                }
                            }
                            setState(96);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUOTATION) | (1L << PAREN_LEFT) | (1L << BRACKET_LEFT) | (1L << NOT) | (1L << NUMBER) | (1L << WORD))) != 0));
                        setState(98);
                        match(PAREN_RIGHT);
                        setState(100);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
                            case 1: {
                                setState(99);
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
                        setState(102);
                        assignment();
                        setState(103);
                        queryElem(0);
                        setState(104);
                        match(LT);
                        setState(105);
                        queryElem(0);
                        setState(107);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                            case 1: {
                                setState(106);
                                proximity();
                            }
                            break;
                        }
                    }
                    break;
                    case 6: {
                        _localctx = new UnaryOperationContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(110);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == NUMBER || _la == WORD) {
                            {
                                setState(109);
                                assignment();
                            }
                        }

                        setState(112);
                        unaryOperator();
                        setState(113);
                        queryElem(1);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(129);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(127);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
                                case 1: {
                                    _localctx = new BinaryOperationContext(new QueryElemContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryElem);
                                    setState(117);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(118);
                                    binaryOperator();
                                    setState(119);
                                    queryElem(3);
                                }
                                break;
                                case 2: {
                                    _localctx = new OrderContext(new QueryElemContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryElem);
                                    setState(121);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(122);
                                    match(LT);
                                    setState(123);
                                    queryElem(0);
                                    setState(125);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 18, _ctx)) {
                                        case 1: {
                                            setState(124);
                                            proximity();
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    setState(131);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
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
                setState(132);
                match(SIMILARITY);
                setState(133);
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
                setState(137);
                _errHandler.sync(this);
                _alt = 1;
                do {
                    switch (_alt) {
                        case 1: {
                            {
                                setState(135);
                                match(EXPONENT);
                                setState(136);
                                alignElem();
                            }
                        }
                        break;
                        default:
                            throw new NoViableAltException(this);
                    }
                    setState(139);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 21, _ctx);
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
            setState(163);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 22, _ctx)) {
                case 1:
                    _localctx = new IndexContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(141);
                    match(WORD);
                    setState(142);
                    match(COLON);
                    setState(143);
                    queryLiteral();
                }
                break;
                case 2:
                    _localctx = new IndexContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(144);
                    match(PAREN_LEFT);
                    setState(145);
                    match(WORD);
                    setState(146);
                    match(COLON);
                    setState(147);
                    queryLiteral();
                    setState(148);
                    match(PAREN_RIGHT);
                }
                break;
                case 3:
                    _localctx = new NertagContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(150);
                    match(WORD);
                    setState(151);
                    match(DOT);
                    setState(152);
                    match(WORD);
                    setState(153);
                    match(COLON);
                    setState(154);
                    queryLiteral();
                }
                break;
                case 4:
                    _localctx = new NertagContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(155);
                    match(PAREN_LEFT);
                    setState(156);
                    match(WORD);
                    setState(157);
                    match(DOT);
                    setState(158);
                    match(WORD);
                    setState(159);
                    match(COLON);
                    setState(160);
                    queryLiteral();
                    setState(161);
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

    public final AssignmentContext assignment() throws RecognitionException {
        AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_assignment);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(165);
                _la = _input.LA(1);
                if (!(_la == NUMBER || _la == WORD)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(166);
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
        enterRule(_localctx, 14, RULE_contextConstraint);
        try {
            setState(172);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 23, _ctx)) {
                case 1:
                    _localctx = new ParContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(168);
                    match(MINUS);
                    setState(169);
                    match(PAR);
                }
                break;
                case 2:
                    _localctx = new SentContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(170);
                    match(MINUS);
                    setState(171);
                    match(SENT);
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
        enterRule(_localctx, 16, RULE_indexOperator);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(174);
                match(WORD);
                setState(175);
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
        enterRule(_localctx, 18, RULE_queryLiteral);
        try {
            setState(180);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case WORD:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(177);
                    match(WORD);
                }
                break;
                case NUMBER:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(178);
                    match(NUMBER);
                }
                break;
                case BRACKET_LEFT:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(179);
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

    public final IntervalContext interval() throws RecognitionException {
        IntervalContext _localctx = new IntervalContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_interval);
        int _la;
        try {
            setState(196);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 27, _ctx)) {
                case 1:
                    _localctx = new NumberRangeContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(182);
                    match(BRACKET_LEFT);
                    setState(183);
                    match(NUMBER);
                    setState(184);
                    match(RANGE);
                    setState(185);
                    match(NUMBER);
                    setState(186);
                    match(BRACKET_RIGHT);
                }
                break;
                case 2:
                    _localctx = new DateRangeContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(187);
                    match(BRACKET_LEFT);
                    setState(189);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == DATE) {
                        {
                            setState(188);
                            match(DATE);
                        }
                    }

                    setState(191);
                    match(RANGE);
                    setState(193);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == DATE) {
                        {
                            setState(192);
                            match(DATE);
                        }
                    }

                    setState(195);
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

    public final ConstraintContext constraint() throws RecognitionException {
        return constraint(0);
    }

    private ConstraintContext constraint(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        ConstraintContext _localctx = new ConstraintContext(_ctx, _parentState);
        ConstraintContext _prevctx = _localctx;
        int _startState = 22;
        enterRecursionRule(_localctx, 22, RULE_constraint, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(210);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case PAREN_LEFT: {
                        setState(199);
                        match(PAREN_LEFT);
                        setState(200);
                        constraint(0);
                        setState(201);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case NUMBER:
                    case WORD: {
                        setState(203);
                        reference();
                        setState(204);
                        comparisonOperator();
                        setState(205);
                        reference();
                    }
                    break;
                    case NOT: {
                        setState(207);
                        unaryOperator();
                        setState(208);
                        constraint(1);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(218);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 29, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new ConstraintContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_constraint);
                                setState(212);
                                if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                setState(213);
                                binaryOperator();
                                setState(214);
                                constraint(3);
                            }
                        }
                    }
                    setState(220);
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
        enterRule(_localctx, 24, RULE_reference);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(221);
                _la = _input.LA(1);
                if (!(_la == NUMBER || _la == WORD)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(222);
                match(DOT);
                setState(223);
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
        enterRule(_localctx, 26, RULE_comparisonOperator);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(225);
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

    public final BinaryOperatorContext binaryOperator() throws RecognitionException {
        BinaryOperatorContext _localctx = new BinaryOperatorContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_binaryOperator);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(227);
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
        enterRule(_localctx, 30, RULE_unaryOperator);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(229);
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
            case 11:
                return constraint_sempred((ConstraintContext) _localctx, predIndex);
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

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(EqlParser.EOF, 0);
        }

        public TerminalNode QUERY_CONSTRAINT_SEPARATOR() {
            return getToken(EqlParser.QUERY_CONSTRAINT_SEPARATOR, 0);
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

    public static class IndexWithMultipleValuesContext extends QueryElemContext {
        public IndexWithMultipleValuesContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public IndexOperatorContext indexOperator() {
            return getRuleContext(IndexOperatorContext.class, 0);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(EqlParser.PAREN_LEFT, 0);
        }

        public List<TerminalNode> WORD() {
            return getTokens(EqlParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(EqlParser.WORD, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(EqlParser.PAREN_RIGHT, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public List<TerminalNode> OR() {
            return getTokens(EqlParser.OR);
        }

        public TerminalNode OR(int i) {
            return getToken(EqlParser.OR, i);
        }

        public AlignOperatorContext alignOperator() {
            return getRuleContext(AlignOperatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterIndexWithMultipleValues(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitIndexWithMultipleValues(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor)
                return ((EqlVisitor<? extends T>) visitor).visitIndexWithMultipleValues(this);
            else return visitor.visitChildren(this);
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

    public static class IndexWithSingleValueContext extends QueryElemContext {
        public IndexWithSingleValueContext(QueryElemContext ctx) {
            copyFrom(ctx);
        }

        public QueryLiteralContext queryLiteral() {
            return getRuleContext(QueryLiteralContext.class, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public IndexOperatorContext indexOperator() {
            return getRuleContext(IndexOperatorContext.class, 0);
        }

        public AlignOperatorContext alignOperator() {
            return getRuleContext(AlignOperatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterIndexWithSingleValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitIndexWithSingleValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor)
                return ((EqlVisitor<? extends T>) visitor).visitIndexWithSingleValue(this);
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

        public BinaryOperatorContext binaryOperator() {
            return getRuleContext(BinaryOperatorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterBinaryOperation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitBinaryOperation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitBinaryOperation(this);
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

        public QueryElemContext queryElem() {
            return getRuleContext(QueryElemContext.class, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterUnaryOperation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitUnaryOperation(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitUnaryOperation(this);
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

    public static class NertagContext extends AlignElemContext {
        public NertagContext(AlignElemContext ctx) {
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

        public QueryLiteralContext queryLiteral() {
            return getRuleContext(QueryLiteralContext.class, 0);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(EqlParser.PAREN_LEFT, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(EqlParser.PAREN_RIGHT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterNertag(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitNertag(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitNertag(this);
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

        public QueryLiteralContext queryLiteral() {
            return getRuleContext(QueryLiteralContext.class, 0);
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

    public static class ParContext extends ContextConstraintContext {
        public ParContext(ContextConstraintContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode MINUS() {
            return getToken(EqlParser.MINUS, 0);
        }

        public TerminalNode PAR() {
            return getToken(EqlParser.PAR, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterPar(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitPar(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitPar(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SentContext extends ContextConstraintContext {
        public SentContext(ContextConstraintContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode MINUS() {
            return getToken(EqlParser.MINUS, 0);
        }

        public TerminalNode SENT() {
            return getToken(EqlParser.SENT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterSent(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitSent(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitSent(this);
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

        public IntervalContext interval() {
            return getRuleContext(IntervalContext.class, 0);
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

    public static class ConstraintContext extends ParserRuleContext {
        public ConstraintContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(EqlParser.PAREN_LEFT, 0);
        }

        public List<ConstraintContext> constraint() {
            return getRuleContexts(ConstraintContext.class);
        }

        public ConstraintContext constraint(int i) {
            return getRuleContext(ConstraintContext.class, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(EqlParser.PAREN_RIGHT, 0);
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
            if (listener instanceof EqlListener) ((EqlListener) listener).enterConstraint(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitConstraint(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitConstraint(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ReferenceContext extends ParserRuleContext {
        public ReferenceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode DOT() {
            return getToken(EqlParser.DOT, 0);
        }

        public List<TerminalNode> WORD() {
            return getTokens(EqlParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(EqlParser.WORD, i);
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

    public static class BinaryOperatorContext extends ParserRuleContext {
        public BinaryOperatorContext(ParserRuleContext parent, int invokingState) {
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
            return RULE_binaryOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterBinaryOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitBinaryOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitBinaryOperator(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class UnaryOperatorContext extends ParserRuleContext {
        public UnaryOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode NOT() {
            return getToken(EqlParser.NOT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_unaryOperator;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).enterUnaryOperator(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof EqlListener) ((EqlListener) listener).exitUnaryOperator(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof EqlVisitor) return ((EqlVisitor<? extends T>) visitor).visitUnaryOperator(this);
            else return visitor.visitChildren(this);
        }
    }
}