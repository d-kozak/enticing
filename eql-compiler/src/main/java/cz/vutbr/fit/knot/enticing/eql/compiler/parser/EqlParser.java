// Generated from /home/dkozak/projects/knot/enticing/eql-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/eql/compiler/parser/Eql.g4 by ANTLR 4.7.2
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
            ARROW = 1, MINUS = 2, COLON = 3, EXPONENT = 4, SIMILARITY = 5, SENT = 6, PAR = 7, QUOTATION = 8,
            QUERY_CONSTRAINT_SEPARATOR = 9, EQ = 10, NEQ = 11, LT = 12, LE = 13, GT = 14, GE = 15,
            PAREN_LEFT = 16, PAREN_RIGHT = 17, DOT = 18, OR = 19, AND = 20, NOT = 21, NUMBER = 22,
            WORD = 23, WS = 24;
    public static final int
            RULE_root = 0, RULE_query = 1, RULE_queryExpression = 2, RULE_alignOperator = 3,
            RULE_assignment = 4, RULE_limitation = 5, RULE_indexOperator = 6, RULE_queryLiteral = 7,
            RULE_constraint = 8, RULE_reference = 9, RULE_comparisonOperator = 10,
            RULE_binaryOperator = 11, RULE_unaryOperator = 12;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\32\u00db\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\5\2 \n\2\3\2\3\2\3\3\5\3%" +
                    "\n\3\3\3\3\3\5\3)\n\3\3\3\5\3,\n\3\6\3.\n\3\r\3\16\3/\3\4\3\4\5\4\64\n" +
                    "\4\3\4\3\4\3\4\3\4\5\4:\n\4\3\4\5\4=\n\4\3\4\5\4@\n\4\3\4\5\4C\n\4\3\4" +
                    "\3\4\5\4G\n\4\3\4\3\4\3\4\3\4\5\4M\n\4\3\4\5\4P\n\4\3\4\3\4\3\4\5\4U\n" +
                    "\4\3\4\5\4X\n\4\3\4\3\4\5\4\\\n\4\3\4\3\4\5\4`\n\4\3\4\5\4c\n\4\3\4\3" +
                    "\4\3\4\5\4h\n\4\3\4\5\4k\n\4\3\4\3\4\5\4o\n\4\3\4\3\4\5\4s\n\4\3\4\5\4" +
                    "v\n\4\3\4\3\4\3\4\5\4{\n\4\3\4\5\4~\n\4\5\4\u0080\n\4\3\4\3\4\3\4\3\4" +
                    "\3\4\5\4\u0087\n\4\3\4\5\4\u008a\n\4\3\4\3\4\5\4\u008e\n\4\3\4\3\4\5\4" +
                    "\u0092\n\4\3\4\5\4\u0095\n\4\7\4\u0097\n\4\f\4\16\4\u009a\13\4\3\5\3\5" +
                    "\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u00a8\n\7\3\b\3\b\7\b\u00ac" +
                    "\n\b\f\b\16\b\u00af\13\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3" +
                    "\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00c6\n\n\3\n\3\n\3\n\3\n\7" +
                    "\n\u00cc\n\n\f\n\16\n\u00cf\13\n\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3" +
                    "\16\3\16\3\16\2\4\6\22\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\5\3\2\30" +
                    "\31\3\2\f\21\3\2\25\26\2\u00f9\2\34\3\2\2\2\4-\3\2\2\2\6\177\3\2\2\2\b" +
                    "\u009b\3\2\2\2\n\u009e\3\2\2\2\f\u00a7\3\2\2\2\16\u00ad\3\2\2\2\20\u00b3" +
                    "\3\2\2\2\22\u00c5\3\2\2\2\24\u00d0\3\2\2\2\26\u00d4\3\2\2\2\30\u00d6\3" +
                    "\2\2\2\32\u00d8\3\2\2\2\34\37\5\4\3\2\35\36\7\13\2\2\36 \5\22\n\2\37\35" +
                    "\3\2\2\2\37 \3\2\2\2 !\3\2\2\2!\"\7\2\2\3\"\3\3\2\2\2#%\5\n\6\2$#\3\2" +
                    "\2\2$%\3\2\2\2%&\3\2\2\2&(\5\6\4\2\')\5\b\5\2(\'\3\2\2\2()\3\2\2\2)+\3" +
                    "\2\2\2*,\5\f\7\2+*\3\2\2\2+,\3\2\2\2,.\3\2\2\2-$\3\2\2\2./\3\2\2\2/-\3" +
                    "\2\2\2/\60\3\2\2\2\60\5\3\2\2\2\61\63\b\4\1\2\62\64\5\n\6\2\63\62\3\2" +
                    "\2\2\63\64\3\2\2\2\64\65\3\2\2\2\65\66\7\n\2\2\66\67\5\4\3\2\679\7\n\2" +
                    "\28:\5\b\5\298\3\2\2\29:\3\2\2\2:<\3\2\2\2;=\5\f\7\2<;\3\2\2\2<=\3\2\2" +
                    "\2=\u0080\3\2\2\2>@\5\n\6\2?>\3\2\2\2?@\3\2\2\2@B\3\2\2\2AC\5\16\b\2B" +
                    "A\3\2\2\2BC\3\2\2\2CD\3\2\2\2D\u0080\5\20\t\2EG\5\n\6\2FE\3\2\2\2FG\3" +
                    "\2\2\2GH\3\2\2\2HI\7\22\2\2IJ\5\4\3\2JL\7\23\2\2KM\5\b\5\2LK\3\2\2\2L" +
                    "M\3\2\2\2MO\3\2\2\2NP\5\f\7\2ON\3\2\2\2OP\3\2\2\2P\u0080\3\2\2\2QR\5\n" +
                    "\6\2RT\5\6\4\2SU\5\b\5\2TS\3\2\2\2TU\3\2\2\2UW\3\2\2\2VX\5\f\7\2WV\3\2" +
                    "\2\2WX\3\2\2\2XY\3\2\2\2Y[\7\16\2\2Z\\\5\n\6\2[Z\3\2\2\2[\\\3\2\2\2\\" +
                    "]\3\2\2\2]_\5\6\4\2^`\5\b\5\2_^\3\2\2\2_`\3\2\2\2`b\3\2\2\2ac\5\f\7\2" +
                    "ba\3\2\2\2bc\3\2\2\2c\u0080\3\2\2\2de\5\n\6\2eg\5\6\4\2fh\5\b\5\2gf\3" +
                    "\2\2\2gh\3\2\2\2hj\3\2\2\2ik\5\f\7\2ji\3\2\2\2jk\3\2\2\2kl\3\2\2\2ln\5" +
                    "\30\r\2mo\5\n\6\2nm\3\2\2\2no\3\2\2\2op\3\2\2\2pr\5\6\4\2qs\5\b\5\2rq" +
                    "\3\2\2\2rs\3\2\2\2su\3\2\2\2tv\5\f\7\2ut\3\2\2\2uv\3\2\2\2v\u0080\3\2" +
                    "\2\2wx\5\32\16\2xz\5\6\4\2y{\5\b\5\2zy\3\2\2\2z{\3\2\2\2{}\3\2\2\2|~\5" +
                    "\f\7\2}|\3\2\2\2}~\3\2\2\2~\u0080\3\2\2\2\177\61\3\2\2\2\177?\3\2\2\2" +
                    "\177F\3\2\2\2\177Q\3\2\2\2\177d\3\2\2\2\177w\3\2\2\2\u0080\u0098\3\2\2" +
                    "\2\u0081\u0082\f\7\2\2\u0082\u0083\7\16\2\2\u0083\u0097\5\6\4\b\u0084" +
                    "\u0086\f\4\2\2\u0085\u0087\5\b\5\2\u0086\u0085\3\2\2\2\u0086\u0087\3\2" +
                    "\2\2\u0087\u0089\3\2\2\2\u0088\u008a\5\f\7\2\u0089\u0088\3\2\2\2\u0089" +
                    "\u008a\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008d\5\30\r\2\u008c\u008e\5" +
                    "\n\6\2\u008d\u008c\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\3\2\2\2\u008f" +
                    "\u0091\5\6\4\2\u0090\u0092\5\b\5\2\u0091\u0090\3\2\2\2\u0091\u0092\3\2" +
                    "\2\2\u0092\u0094\3\2\2\2\u0093\u0095\5\f\7\2\u0094\u0093\3\2\2\2\u0094" +
                    "\u0095\3\2\2\2\u0095\u0097\3\2\2\2\u0096\u0081\3\2\2\2\u0096\u0084\3\2" +
                    "\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099" +
                    "\7\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c\7\6\2\2\u009c\u009d\5\4\3\2" +
                    "\u009d\t\3\2\2\2\u009e\u009f\7\31\2\2\u009f\u00a0\7\3\2\2\u00a0\13\3\2" +
                    "\2\2\u00a1\u00a2\7\4\2\2\u00a2\u00a8\7\t\2\2\u00a3\u00a4\7\4\2\2\u00a4" +
                    "\u00a8\7\b\2\2\u00a5\u00a6\7\7\2\2\u00a6\u00a8\7\30\2\2\u00a7\u00a1\3" +
                    "\2\2\2\u00a7\u00a3\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a8\r\3\2\2\2\u00a9\u00aa" +
                    "\7\31\2\2\u00aa\u00ac\7\24\2\2\u00ab\u00a9\3\2\2\2\u00ac\u00af\3\2\2\2" +
                    "\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00ad" +
                    "\3\2\2\2\u00b0\u00b1\7\31\2\2\u00b1\u00b2\7\5\2\2\u00b2\17\3\2\2\2\u00b3" +
                    "\u00b4\t\2\2\2\u00b4\21\3\2\2\2\u00b5\u00b6\b\n\1\2\u00b6\u00b7\7\22\2" +
                    "\2\u00b7\u00b8\5\22\n\2\u00b8\u00b9\7\23\2\2\u00b9\u00c6\3\2\2\2\u00ba" +
                    "\u00bb\5\24\13\2\u00bb\u00bc\5\26\f\2\u00bc\u00bd\5\24\13\2\u00bd\u00c6" +
                    "\3\2\2\2\u00be\u00bf\5\24\13\2\u00bf\u00c0\5\26\f\2\u00c0\u00c1\7\31\2" +
                    "\2\u00c1\u00c6\3\2\2\2\u00c2\u00c3\5\32\16\2\u00c3\u00c4\5\22\n\3\u00c4" +
                    "\u00c6\3\2\2\2\u00c5\u00b5\3\2\2\2\u00c5\u00ba\3\2\2\2\u00c5\u00be\3\2" +
                    "\2\2\u00c5\u00c2\3\2\2\2\u00c6\u00cd\3\2\2\2\u00c7\u00c8\f\4\2\2\u00c8" +
                    "\u00c9\5\30\r\2\u00c9\u00ca\5\22\n\5\u00ca\u00cc\3\2\2\2\u00cb\u00c7\3" +
                    "\2\2\2\u00cc\u00cf\3\2\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce" +
                    "\23\3\2\2\2\u00cf\u00cd\3\2\2\2\u00d0\u00d1\7\31\2\2\u00d1\u00d2\7\24" +
                    "\2\2\u00d2\u00d3\7\31\2\2\u00d3\25\3\2\2\2\u00d4\u00d5\t\3\2\2\u00d5\27" +
                    "\3\2\2\2\u00d6\u00d7\t\4\2\2\u00d7\31\3\2\2\2\u00d8\u00d9\7\27\2\2\u00d9" +
                    "\33\3\2\2\2\'\37$(+/\639<?BFLOTW[_bgjnruz}\177\u0086\u0089\u008d\u0091" +
                    "\u0094\u0096\u0098\u00a7\u00ad\u00c5\u00cd";
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
                "root", "query", "queryExpression", "alignOperator", "assignment", "limitation",
                "indexOperator", "queryLiteral", "constraint", "reference", "comparisonOperator",
                "binaryOperator", "unaryOperator"
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
                "GE", "PAREN_LEFT", "PAREN_RIGHT", "DOT", "OR", "AND", "NOT", "NUMBER",
                "WORD", "WS"
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
                setState(26);
                query();
                setState(29);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == QUERY_CONSTRAINT_SEPARATOR) {
                    {
                        setState(27);
                        match(QUERY_CONSTRAINT_SEPARATOR);
                        setState(28);
                        constraint(0);
                    }
                }

                setState(31);
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
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(43);
                _errHandler.sync(this);
                _alt = 1;
                do {
                    switch (_alt) {
                        case 1: {
                            {
                                setState(34);
                                _errHandler.sync(this);
                                switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
                                    case 1: {
                                        setState(33);
                                        assignment();
                                    }
                                    break;
                                }
                                setState(36);
                                queryExpression(0);
                                setState(38);
                                _errHandler.sync(this);
                                switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                                    case 1: {
                                        setState(37);
                                        alignOperator();
                                    }
                                    break;
                                }
                                setState(41);
                                _errHandler.sync(this);
                                switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                                    case 1: {
                                        setState(40);
                                        limitation();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                        default:
                            throw new NoViableAltException(this);
                    }
                    setState(45);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
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

    public final QueryExpressionContext queryExpression() throws RecognitionException {
        return queryExpression(0);
    }

    private QueryExpressionContext queryExpression(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        QueryExpressionContext _localctx = new QueryExpressionContext(_ctx, _parentState);
        QueryExpressionContext _prevctx = _localctx;
        int _startState = 4;
        enterRecursionRule(_localctx, 4, RULE_queryExpression, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(125);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 25, _ctx)) {
                    case 1: {
                        _localctx = new SequenceContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(49);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == WORD) {
                            {
                                setState(48);
                                assignment();
                            }
                        }

                        setState(51);
                        match(QUOTATION);
                        setState(52);
                        query();
                        setState(53);
                        match(QUOTATION);
                        setState(55);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
                            case 1: {
                                setState(54);
                                alignOperator();
                            }
                            break;
                        }
                        setState(58);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
                            case 1: {
                                setState(57);
                                limitation();
                            }
                            break;
                        }
                    }
                    break;
                    case 2: {
                        _localctx = new LiteralContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(61);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                            case 1: {
                                setState(60);
                                assignment();
                            }
                            break;
                        }
                        setState(64);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                            case 1: {
                                setState(63);
                                indexOperator();
                            }
                            break;
                        }
                        setState(66);
                        queryLiteral();
                    }
                    break;
                    case 3: {
                        _localctx = new ParenContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(68);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == WORD) {
                            {
                                setState(67);
                                assignment();
                            }
                        }

                        setState(70);
                        match(PAREN_LEFT);
                        setState(71);
                        query();
                        setState(72);
                        match(PAREN_RIGHT);
                        setState(74);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
                            case 1: {
                                setState(73);
                                alignOperator();
                            }
                            break;
                        }
                        setState(77);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
                            case 1: {
                                setState(76);
                                limitation();
                            }
                            break;
                        }
                    }
                    break;
                    case 4: {
                        _localctx = new OrderContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(79);
                        assignment();
                        setState(80);
                        queryExpression(0);
                        setState(82);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == EXPONENT) {
                            {
                                setState(81);
                                alignOperator();
                            }
                        }

                        setState(85);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == MINUS || _la == SIMILARITY) {
                            {
                                setState(84);
                                limitation();
                            }
                        }

                        setState(87);
                        match(LT);
                        setState(89);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                            case 1: {
                                setState(88);
                                assignment();
                            }
                            break;
                        }
                        setState(91);
                        queryExpression(0);
                        setState(93);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
                            case 1: {
                                setState(92);
                                alignOperator();
                            }
                            break;
                        }
                        setState(96);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
                            case 1: {
                                setState(95);
                                limitation();
                            }
                            break;
                        }
                    }
                    break;
                    case 5: {
                        _localctx = new BinaryOperationContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(98);
                        assignment();
                        setState(99);
                        queryExpression(0);
                        setState(101);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == EXPONENT) {
                            {
                                setState(100);
                                alignOperator();
                            }
                        }

                        setState(104);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == MINUS || _la == SIMILARITY) {
                            {
                                setState(103);
                                limitation();
                            }
                        }

                        setState(106);
                        binaryOperator();
                        setState(108);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
                            case 1: {
                                setState(107);
                                assignment();
                            }
                            break;
                        }
                        setState(110);
                        queryExpression(0);
                        setState(112);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 21, _ctx)) {
                            case 1: {
                                setState(111);
                                alignOperator();
                            }
                            break;
                        }
                        setState(115);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 22, _ctx)) {
                            case 1: {
                                setState(114);
                                limitation();
                            }
                            break;
                        }
                    }
                    break;
                    case 6: {
                        _localctx = new UnaryOperationContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(117);
                        unaryOperator();
                        setState(118);
                        queryExpression(0);
                        setState(120);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 23, _ctx)) {
                            case 1: {
                                setState(119);
                                alignOperator();
                            }
                            break;
                        }
                        setState(123);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 24, _ctx)) {
                            case 1: {
                                setState(122);
                                limitation();
                            }
                            break;
                        }
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(150);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 32, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(148);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 31, _ctx)) {
                                case 1: {
                                    _localctx = new OrderContext(new QueryExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryExpression);
                                    setState(127);
                                    if (!(precpred(_ctx, 5)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                                    setState(128);
                                    match(LT);
                                    setState(129);
                                    queryExpression(6);
                                }
                                break;
                                case 2: {
                                    _localctx = new BinaryOperationContext(new QueryExpressionContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryExpression);
                                    setState(130);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(132);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    if (_la == EXPONENT) {
                                        {
                                            setState(131);
                                            alignOperator();
                                        }
                                    }

                                    setState(135);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    if (_la == MINUS || _la == SIMILARITY) {
                                        {
                                            setState(134);
                                            limitation();
                                        }
                                    }

                                    setState(137);
                                    binaryOperator();
                                    setState(139);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 28, _ctx)) {
                                        case 1: {
                                            setState(138);
                                            assignment();
                                        }
                                        break;
                                    }
                                    setState(141);
                                    queryExpression(0);
                                    setState(143);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 29, _ctx)) {
                                        case 1: {
                                            setState(142);
                                            alignOperator();
                                        }
                                        break;
                                    }
                                    setState(146);
                                    _errHandler.sync(this);
                                    switch (getInterpreter().adaptivePredict(_input, 30, _ctx)) {
                                        case 1: {
                                            setState(145);
                                            limitation();
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    setState(152);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 32, _ctx);
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

    public final AlignOperatorContext alignOperator() throws RecognitionException {
        AlignOperatorContext _localctx = new AlignOperatorContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_alignOperator);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(153);
                match(EXPONENT);
                setState(154);
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

    public final AssignmentContext assignment() throws RecognitionException {
        AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_assignment);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(156);
                match(WORD);
                setState(157);
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
        enterRule(_localctx, 10, RULE_limitation);
        try {
            setState(165);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 33, _ctx)) {
                case 1:
                    _localctx = new ParContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(159);
                    match(MINUS);
                    setState(160);
                    match(PAR);
                }
                break;
                case 2:
                    _localctx = new SentContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(161);
                    match(MINUS);
                    setState(162);
                    match(SENT);
                }
                break;
                case 3:
                    _localctx = new ProximityContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(163);
                    match(SIMILARITY);
                    setState(164);
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
        enterRule(_localctx, 12, RULE_indexOperator);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(171);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 34, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(167);
                                match(WORD);
                                setState(168);
                                match(DOT);
                            }
                        }
                    }
                    setState(173);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 34, _ctx);
                }
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
        enterRule(_localctx, 14, RULE_queryLiteral);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(177);
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
                setState(195);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 35, _ctx)) {
                    case 1: {
                        setState(180);
                        match(PAREN_LEFT);
                        setState(181);
                        constraint(0);
                        setState(182);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case 2: {
                        setState(184);
                        reference();
                        setState(185);
                        comparisonOperator();
                        setState(186);
                        reference();
                    }
                    break;
                    case 3: {
                        setState(188);
                        reference();
                        setState(189);
                        comparisonOperator();
                        setState(190);
                        match(WORD);
                    }
                    break;
                    case 4: {
                        setState(192);
                        unaryOperator();
                        setState(193);
                        constraint(1);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(203);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 36, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new ConstraintContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_constraint);
                                setState(197);
                                if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                setState(198);
                                binaryOperator();
                                setState(199);
                                constraint(3);
                            }
                        }
                    }
                    setState(205);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 36, _ctx);
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
                setState(206);
                match(WORD);
                setState(207);
                match(DOT);
                setState(208);
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
        enterRule(_localctx, 20, RULE_comparisonOperator);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(210);
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
                setState(212);
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
                setState(214);
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
                return queryExpression_sempred((QueryExpressionContext) _localctx, predIndex);
            case 8:
                return constraint_sempred((ConstraintContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean queryExpression_sempred(QueryExpressionContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 5);
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

        public List<QueryExpressionContext> queryExpression() {
            return getRuleContexts(QueryExpressionContext.class);
        }

        public QueryExpressionContext queryExpression(int i) {
            return getRuleContext(QueryExpressionContext.class, i);
        }

        public List<AssignmentContext> assignment() {
            return getRuleContexts(AssignmentContext.class);
        }

        public AssignmentContext assignment(int i) {
            return getRuleContext(AssignmentContext.class, i);
        }

        public List<AlignOperatorContext> alignOperator() {
            return getRuleContexts(AlignOperatorContext.class);
        }

        public AlignOperatorContext alignOperator(int i) {
            return getRuleContext(AlignOperatorContext.class, i);
        }

        public List<LimitationContext> limitation() {
            return getRuleContexts(LimitationContext.class);
        }

        public LimitationContext limitation(int i) {
            return getRuleContext(LimitationContext.class, i);
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

    public static class QueryExpressionContext extends ParserRuleContext {
        public QueryExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public QueryExpressionContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_queryExpression;
        }

        public void copyFrom(QueryExpressionContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SequenceContext extends QueryExpressionContext {
        public SequenceContext(QueryExpressionContext ctx) {
            copyFrom(ctx);
        }

        public List<TerminalNode> QUOTATION() {
            return getTokens(EqlParser.QUOTATION);
        }

        public TerminalNode QUOTATION(int i) {
            return getToken(EqlParser.QUOTATION, i);
        }

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public AlignOperatorContext alignOperator() {
            return getRuleContext(AlignOperatorContext.class, 0);
        }

        public LimitationContext limitation() {
            return getRuleContext(LimitationContext.class, 0);
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

    public static class ParenContext extends QueryExpressionContext {
        public ParenContext(QueryExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(EqlParser.PAREN_LEFT, 0);
        }

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(EqlParser.PAREN_RIGHT, 0);
        }

        public AssignmentContext assignment() {
            return getRuleContext(AssignmentContext.class, 0);
        }

        public AlignOperatorContext alignOperator() {
            return getRuleContext(AlignOperatorContext.class, 0);
        }

        public LimitationContext limitation() {
            return getRuleContext(LimitationContext.class, 0);
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

    public static class BinaryOperationContext extends QueryExpressionContext {
        public BinaryOperationContext(QueryExpressionContext ctx) {
            copyFrom(ctx);
        }

        public List<AssignmentContext> assignment() {
            return getRuleContexts(AssignmentContext.class);
        }

        public AssignmentContext assignment(int i) {
            return getRuleContext(AssignmentContext.class, i);
        }

        public List<QueryExpressionContext> queryExpression() {
            return getRuleContexts(QueryExpressionContext.class);
        }

        public QueryExpressionContext queryExpression(int i) {
            return getRuleContext(QueryExpressionContext.class, i);
        }

        public BinaryOperatorContext binaryOperator() {
            return getRuleContext(BinaryOperatorContext.class, 0);
        }

        public List<AlignOperatorContext> alignOperator() {
            return getRuleContexts(AlignOperatorContext.class);
        }

        public AlignOperatorContext alignOperator(int i) {
            return getRuleContext(AlignOperatorContext.class, i);
        }

        public List<LimitationContext> limitation() {
            return getRuleContexts(LimitationContext.class);
        }

        public LimitationContext limitation(int i) {
            return getRuleContext(LimitationContext.class, i);
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

    public static class UnaryOperationContext extends QueryExpressionContext {
        public UnaryOperationContext(QueryExpressionContext ctx) {
            copyFrom(ctx);
        }

        public UnaryOperatorContext unaryOperator() {
            return getRuleContext(UnaryOperatorContext.class, 0);
        }

        public QueryExpressionContext queryExpression() {
            return getRuleContext(QueryExpressionContext.class, 0);
        }

        public AlignOperatorContext alignOperator() {
            return getRuleContext(AlignOperatorContext.class, 0);
        }

        public LimitationContext limitation() {
            return getRuleContext(LimitationContext.class, 0);
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

    public static class LiteralContext extends QueryExpressionContext {
        public LiteralContext(QueryExpressionContext ctx) {
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

    public static class OrderContext extends QueryExpressionContext {
        public OrderContext(QueryExpressionContext ctx) {
            copyFrom(ctx);
        }

        public List<AssignmentContext> assignment() {
            return getRuleContexts(AssignmentContext.class);
        }

        public AssignmentContext assignment(int i) {
            return getRuleContext(AssignmentContext.class, i);
        }

        public List<QueryExpressionContext> queryExpression() {
            return getRuleContexts(QueryExpressionContext.class);
        }

        public QueryExpressionContext queryExpression(int i) {
            return getRuleContext(QueryExpressionContext.class, i);
        }

        public TerminalNode LT() {
            return getToken(EqlParser.LT, 0);
        }

        public List<AlignOperatorContext> alignOperator() {
            return getRuleContexts(AlignOperatorContext.class);
        }

        public AlignOperatorContext alignOperator(int i) {
            return getRuleContext(AlignOperatorContext.class, i);
        }

        public List<LimitationContext> limitation() {
            return getRuleContexts(LimitationContext.class);
        }

        public LimitationContext limitation(int i) {
            return getRuleContext(LimitationContext.class, i);
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

    public static class AlignOperatorContext extends ParserRuleContext {
        public AlignOperatorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EXPONENT() {
            return getToken(EqlParser.EXPONENT, 0);
        }

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
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

    public static class AssignmentContext extends ParserRuleContext {
        public AssignmentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode WORD() {
            return getToken(EqlParser.WORD, 0);
        }

        public TerminalNode ARROW() {
            return getToken(EqlParser.ARROW, 0);
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

    public static class ProximityContext extends LimitationContext {
        public ProximityContext(LimitationContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode SIMILARITY() {
            return getToken(EqlParser.SIMILARITY, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(EqlParser.NUMBER, 0);
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

    public static class SentContext extends LimitationContext {
        public SentContext(LimitationContext ctx) {
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

        public List<TerminalNode> WORD() {
            return getTokens(EqlParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(EqlParser.WORD, i);
        }

        public TerminalNode COLON() {
            return getToken(EqlParser.COLON, 0);
        }

        public List<TerminalNode> DOT() {
            return getTokens(EqlParser.DOT);
        }

        public TerminalNode DOT(int i) {
            return getToken(EqlParser.DOT, i);
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

        public TerminalNode WORD() {
            return getToken(EqlParser.WORD, 0);
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

        public List<TerminalNode> WORD() {
            return getTokens(EqlParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(EqlParser.WORD, i);
        }

        public TerminalNode DOT() {
            return getToken(EqlParser.DOT, 0);
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

        public TerminalNode LT() {
            return getToken(EqlParser.LT, 0);
        }

        public TerminalNode LE() {
            return getToken(EqlParser.LE, 0);
        }

        public TerminalNode GT() {
            return getToken(EqlParser.GT, 0);
        }

        public TerminalNode GE() {
            return getToken(EqlParser.GE, 0);
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