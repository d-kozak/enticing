// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/Mg4j.g4 by ANTLR 4.7.2
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
public class Mg4jParser extends Parser {
    public static final int
            MINUS = 1, COLON = 2, EXPONENT = 3, SIMILARITY = 4, SENT = 5, PAR = 6, QUOTATION = 7,
            SEP = 8, EQ = 9, NEQ = 10, LT = 11, LE = 12, GT = 13, GE = 14, PAREN_LEFT = 15, PAREN_RIGHT = 16,
            DOT = 17, OR = 18, AND = 19, NOT = 20, NUMBER = 21, WORD = 22, WS = 23;
    public static final int
            RULE_expression = 0, RULE_query = 1, RULE_queryNode = 2, RULE_indexTerm = 3,
            RULE_simpleNode = 4, RULE_nertagSem = 5, RULE_sem = 6, RULE_proximity = 7,
            RULE_index = 8, RULE_diff = 9, RULE_queryReference = 10, RULE_sequence = 11,
            RULE_arb = 12, RULE_arbTerm = 13, RULE_arbTermLast = 14, RULE_arbTermWithOp = 15,
            RULE_nodeWithoutReference = 16, RULE_constraintNode = 17, RULE_constraint = 18,
            RULE_reference = 19, RULE_binaryOp = 20, RULE_unaryOp = 21, RULE_relOp = 22;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\31\u012d\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\3\2\3" +
                    "\2\5\2\64\n\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3A\n\3\3\4" +
                    "\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4Q\n\4\3\4\3\4" +
                    "\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4\\\n\4\f\4\16\4_\13\4\3\5\3\5\3\5\3\5" +
                    "\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6n\n\6\3\6\3\6\3\6\3\6\3\6\3\6" +
                    "\7\6v\n\6\f\6\16\6y\13\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3" +
                    "\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u008e\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3" +
                    "\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u009f\n\n\3\n\3\n\3\n\7\n\u00a4" +
                    "\n\n\f\n\16\n\u00a7\13\n\3\13\3\13\3\13\5\13\u00ac\n\13\3\f\3\f\3\f\3" +
                    "\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\5\16\u00bd\n\16\3\17" +
                    "\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00c7\n\17\3\20\3\20\3\20\3\20" +
                    "\3\20\3\20\3\20\3\20\5\20\u00d1\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21" +
                    "\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00e6" +
                    "\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22" +
                    "\5\22\u00f5\n\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\7\22\u0100" +
                    "\n\22\f\22\16\22\u0103\13\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3" +
                    "\23\5\23\u010e\n\23\3\23\3\23\3\23\3\23\7\23\u0114\n\23\f\23\16\23\u0117" +
                    "\13\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0121\n\24\3\25\3" +
                    "\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\2\7\6\n\22\"$\31\2\4" +
                    "\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\2\4\3\2\24\25\3\2\13\20\2" +
                    "\u0147\2\60\3\2\2\2\4@\3\2\2\2\6P\3\2\2\2\b`\3\2\2\2\nm\3\2\2\2\fz\3\2" +
                    "\2\2\16\u008d\3\2\2\2\20\u008f\3\2\2\2\22\u009e\3\2\2\2\24\u00ab\3\2\2" +
                    "\2\26\u00ad\3\2\2\2\30\u00b3\3\2\2\2\32\u00bc\3\2\2\2\34\u00c6\3\2\2\2" +
                    "\36\u00d0\3\2\2\2 \u00e5\3\2\2\2\"\u00f4\3\2\2\2$\u010d\3\2\2\2&\u0120" +
                    "\3\2\2\2(\u0122\3\2\2\2*\u0126\3\2\2\2,\u0128\3\2\2\2.\u012a\3\2\2\2\60" +
                    "\63\5\4\3\2\61\62\7\n\2\2\62\64\5$\23\2\63\61\3\2\2\2\63\64\3\2\2\2\64" +
                    "\65\3\2\2\2\65\66\7\2\2\3\66\3\3\2\2\2\678\5\6\4\289\7\3\2\29:\5\6\4\2" +
                    ":A\3\2\2\2;<\5\6\4\2<=\7\3\2\2=>\5\24\13\2>A\3\2\2\2?A\5\6\4\2@\67\3\2" +
                    "\2\2@;\3\2\2\2@?\3\2\2\2A\5\3\2\2\2BC\b\4\1\2CD\7\21\2\2DE\5\6\4\2EF\7" +
                    "\22\2\2FQ\3\2\2\2GH\5,\27\2HI\5\6\4\nIQ\3\2\2\2JQ\5\26\f\2KQ\5\f\7\2L" +
                    "Q\5\b\5\2MQ\5\20\t\2NQ\5\30\r\2OQ\7\30\2\2PB\3\2\2\2PG\3\2\2\2PJ\3\2\2" +
                    "\2PK\3\2\2\2PL\3\2\2\2PM\3\2\2\2PN\3\2\2\2PO\3\2\2\2Q]\3\2\2\2RS\f\f\2" +
                    "\2ST\5*\26\2TU\5\6\4\rU\\\3\2\2\2VW\f\13\2\2W\\\5\6\4\fXY\f\t\2\2YZ\7" +
                    "\r\2\2Z\\\5\6\4\n[R\3\2\2\2[V\3\2\2\2[X\3\2\2\2\\_\3\2\2\2][\3\2\2\2]" +
                    "^\3\2\2\2^\7\3\2\2\2_]\3\2\2\2`a\7\30\2\2ab\7\4\2\2bc\5\n\6\2c\t\3\2\2" +
                    "\2de\b\6\1\2ef\7\21\2\2fg\5\n\6\2gh\7\22\2\2hn\3\2\2\2ij\5,\27\2jk\5\n" +
                    "\6\5kn\3\2\2\2ln\7\30\2\2md\3\2\2\2mi\3\2\2\2ml\3\2\2\2nw\3\2\2\2op\f" +
                    "\6\2\2pq\5*\26\2qr\5\n\6\7rv\3\2\2\2st\f\4\2\2tv\5\n\6\5uo\3\2\2\2us\3" +
                    "\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x\13\3\2\2\2yw\3\2\2\2z{\7\30\2\2" +
                    "{|\7\4\2\2|}\7\30\2\2}~\7\5\2\2~\177\5\16\b\2\177\r\3\2\2\2\u0080\u0081" +
                    "\7\21\2\2\u0081\u0082\5\16\b\2\u0082\u0083\7\22\2\2\u0083\u008e\3\2\2" +
                    "\2\u0084\u0085\7\21\2\2\u0085\u0086\5\16\b\2\u0086\u0087\7\5\2\2\u0087" +
                    "\u0088\5\16\b\2\u0088\u0089\7\22\2\2\u0089\u008e\3\2\2\2\u008a\u008b\7" +
                    "\30\2\2\u008b\u008c\7\23\2\2\u008c\u008e\5\b\5\2\u008d\u0080\3\2\2\2\u008d" +
                    "\u0084\3\2\2\2\u008d\u008a\3\2\2\2\u008e\17\3\2\2\2\u008f\u0090\7\21\2" +
                    "\2\u0090\u0091\5\6\4\2\u0091\u0092\7\22\2\2\u0092\u0093\7\6\2\2\u0093" +
                    "\u0094\7\27\2\2\u0094\21\3\2\2\2\u0095\u0096\b\n\1\2\u0096\u0097\7\21" +
                    "\2\2\u0097\u0098\5\22\n\2\u0098\u0099\7\22\2\2\u0099\u009f\3\2\2\2\u009a" +
                    "\u009b\5,\27\2\u009b\u009c\5\22\n\4\u009c\u009f\3\2\2\2\u009d\u009f\7" +
                    "\27\2\2\u009e\u0095\3\2\2\2\u009e\u009a\3\2\2\2\u009e\u009d\3\2\2\2\u009f" +
                    "\u00a5\3\2\2\2\u00a0\u00a1\f\5\2\2\u00a1\u00a2\7\24\2\2\u00a2\u00a4\5" +
                    "\22\n\6\u00a3\u00a0\3\2\2\2\u00a4\u00a7\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a5" +
                    "\u00a6\3\2\2\2\u00a6\23\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a8\u00ac\3\2\2" +
                    "\2\u00a9\u00ac\7\7\2\2\u00aa\u00ac\7\b\2\2\u00ab\u00a8\3\2\2\2\u00ab\u00a9" +
                    "\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\25\3\2\2\2\u00ad\u00ae\7\27\2\2\u00ae" +
                    "\u00af\7\4\2\2\u00af\u00b0\7\21\2\2\u00b0\u00b1\5\"\22\2\u00b1\u00b2\7" +
                    "\22\2\2\u00b2\27\3\2\2\2\u00b3\u00b4\7\t\2\2\u00b4\u00b5\5\34\17\2\u00b5" +
                    "\u00b6\5\32\16\2\u00b6\u00b7\7\t\2\2\u00b7\31\3\2\2\2\u00b8\u00b9\5\34" +
                    "\17\2\u00b9\u00ba\5\32\16\2\u00ba\u00bd\3\2\2\2\u00bb\u00bd\5\36\20\2" +
                    "\u00bc\u00b8\3\2\2\2\u00bc\u00bb\3\2\2\2\u00bd\33\3\2\2\2\u00be\u00bf" +
                    "\7\21\2\2\u00bf\u00c0\5 \21\2\u00c0\u00c1\7\22\2\2\u00c1\u00c7\3\2\2\2" +
                    "\u00c2\u00c7\5\26\f\2\u00c3\u00c7\5\f\7\2\u00c4\u00c7\5\b\5\2\u00c5\u00c7" +
                    "\7\30\2\2\u00c6\u00be\3\2\2\2\u00c6\u00c2\3\2\2\2\u00c6\u00c3\3\2\2\2" +
                    "\u00c6\u00c4\3\2\2\2\u00c6\u00c5\3\2\2\2\u00c7\35\3\2\2\2\u00c8\u00c9" +
                    "\7\21\2\2\u00c9\u00ca\5 \21\2\u00ca\u00cb\7\22\2\2\u00cb\u00d1\3\2\2\2" +
                    "\u00cc\u00d1\5\26\f\2\u00cd\u00d1\5\f\7\2\u00ce\u00d1\5\b\5\2\u00cf\u00d1" +
                    "\7\30\2\2\u00d0\u00c8\3\2\2\2\u00d0\u00cc\3\2\2\2\u00d0\u00cd\3\2\2\2" +
                    "\u00d0\u00ce\3\2\2\2\u00d0\u00cf\3\2\2\2\u00d1\37\3\2\2\2\u00d2\u00d3" +
                    "\5\34\17\2\u00d3\u00d4\5*\26\2\u00d4\u00d5\5\32\16\2\u00d5\u00e6\3\2\2" +
                    "\2\u00d6\u00d7\5,\27\2\u00d7\u00d8\5\34\17\2\u00d8\u00d9\5*\26\2\u00d9" +
                    "\u00da\5\32\16\2\u00da\u00e6\3\2\2\2\u00db\u00dc\5,\27\2\u00dc\u00dd\5" +
                    "\34\17\2\u00dd\u00de\5\32\16\2\u00de\u00e6\3\2\2\2\u00df\u00e0\5,\27\2" +
                    "\u00e0\u00e1\5\32\16\2\u00e1\u00e6\3\2\2\2\u00e2\u00e3\5\34\17\2\u00e3" +
                    "\u00e4\5\32\16\2\u00e4\u00e6\3\2\2\2\u00e5\u00d2\3\2\2\2\u00e5\u00d6\3" +
                    "\2\2\2\u00e5\u00db\3\2\2\2\u00e5\u00df\3\2\2\2\u00e5\u00e2\3\2\2\2\u00e6" +
                    "!\3\2\2\2\u00e7\u00e8\b\22\1\2\u00e8\u00e9\7\21\2\2\u00e9\u00ea\5\"\22" +
                    "\2\u00ea\u00eb\7\22\2\2\u00eb\u00f5\3\2\2\2\u00ec\u00ed\5,\27\2\u00ed" +
                    "\u00ee\5\"\22\t\u00ee\u00f5\3\2\2\2\u00ef\u00f5\5\f\7\2\u00f0\u00f5\5" +
                    "\b\5\2\u00f1\u00f5\5\20\t\2\u00f2\u00f5\5\30\r\2\u00f3\u00f5\7\30\2\2" +
                    "\u00f4\u00e7\3\2\2\2\u00f4\u00ec\3\2\2\2\u00f4\u00ef\3\2\2\2\u00f4\u00f0" +
                    "\3\2\2\2\u00f4\u00f1\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f4\u00f3\3\2\2\2\u00f5" +
                    "\u0101\3\2\2\2\u00f6\u00f7\f\13\2\2\u00f7\u00f8\5*\26\2\u00f8\u00f9\5" +
                    "\"\22\f\u00f9\u0100\3\2\2\2\u00fa\u00fb\f\n\2\2\u00fb\u0100\5\"\22\13" +
                    "\u00fc\u00fd\f\b\2\2\u00fd\u00fe\7\r\2\2\u00fe\u0100\5\"\22\t\u00ff\u00f6" +
                    "\3\2\2\2\u00ff\u00fa\3\2\2\2\u00ff\u00fc\3\2\2\2\u0100\u0103\3\2\2\2\u0101" +
                    "\u00ff\3\2\2\2\u0101\u0102\3\2\2\2\u0102#\3\2\2\2\u0103\u0101\3\2\2\2" +
                    "\u0104\u0105\b\23\1\2\u0105\u0106\7\21\2\2\u0106\u0107\5$\23\2\u0107\u0108" +
                    "\7\22\2\2\u0108\u010e\3\2\2\2\u0109\u010a\5,\27\2\u010a\u010b\5$\23\4" +
                    "\u010b\u010e\3\2\2\2\u010c\u010e\5&\24\2\u010d\u0104\3\2\2\2\u010d\u0109" +
                    "\3\2\2\2\u010d\u010c\3\2\2\2\u010e\u0115\3\2\2\2\u010f\u0110\f\5\2\2\u0110" +
                    "\u0111\5*\26\2\u0111\u0112\5$\23\6\u0112\u0114\3\2\2\2\u0113\u010f\3\2" +
                    "\2\2\u0114\u0117\3\2\2\2\u0115\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116" +
                    "%\3\2\2\2\u0117\u0115\3\2\2\2\u0118\u0119\5(\25\2\u0119\u011a\5.\30\2" +
                    "\u011a\u011b\5(\25\2\u011b\u0121\3\2\2\2\u011c\u011d\5(\25\2\u011d\u011e" +
                    "\5.\30\2\u011e\u011f\7\30\2\2\u011f\u0121\3\2\2\2\u0120\u0118\3\2\2\2" +
                    "\u0120\u011c\3\2\2\2\u0121\'\3\2\2\2\u0122\u0123\7\27\2\2\u0123\u0124" +
                    "\7\23\2\2\u0124\u0125\7\30\2\2\u0125)\3\2\2\2\u0126\u0127\t\2\2\2\u0127" +
                    "+\3\2\2\2\u0128\u0129\7\26\2\2\u0129-\3\2\2\2\u012a\u012b\t\3\2\2\u012b" +
                    "/\3\2\2\2\30\63@P[]muw\u008d\u009e\u00a5\u00ab\u00bc\u00c6\u00d0\u00e5" +
                    "\u00f4\u00ff\u0101\u010d\u0115\u0120";
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

    public Mg4jParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "expression", "query", "queryNode", "indexTerm", "simpleNode", "nertagSem",
                "sem", "proximity", "index", "diff", "queryReference", "sequence", "arb",
                "arbTerm", "arbTermLast", "arbTermWithOp", "nodeWithoutReference", "constraintNode",
                "constraint", "reference", "binaryOp", "unaryOp", "relOp"
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
                "SEP", "EQ", "NEQ", "LT", "LE", "GT", "GE", "PAREN_LEFT", "PAREN_RIGHT",
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
        return "Mg4j.g4";
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

    public final ExpressionContext expression() throws RecognitionException {
        ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_expression);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(46);
                query();
                setState(49);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == SEP) {
                    {
                        setState(47);
                        match(SEP);
                        setState(48);
                        constraintNode(0);
                    }
                }

                setState(51);
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
            setState(62);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(53);
                    queryNode(0);
                    setState(54);
                    match(MINUS);
                    setState(55);
                    queryNode(0);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(57);
                    queryNode(0);
                    setState(58);
                    match(MINUS);
                    setState(59);
                    diff();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(61);
                    queryNode(0);
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

    public final QueryNodeContext queryNode() throws RecognitionException {
        return queryNode(0);
    }

    private QueryNodeContext queryNode(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        QueryNodeContext _localctx = new QueryNodeContext(_ctx, _parentState);
        QueryNodeContext _prevctx = _localctx;
        int _startState = 4;
        enterRecursionRule(_localctx, 4, RULE_queryNode, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(78);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                    case 1: {
                        setState(65);
                        match(PAREN_LEFT);
                        setState(66);
                        queryNode(0);
                        setState(67);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case 2: {
                        setState(69);
                        unaryOp();
                        setState(70);
                        queryNode(8);
                    }
                    break;
                    case 3: {
                        setState(72);
                        queryReference();
                    }
                    break;
                    case 4: {
                        setState(73);
                        nertagSem();
                    }
                    break;
                    case 5: {
                        setState(74);
                        indexTerm();
                    }
                    break;
                    case 6: {
                        setState(75);
                        proximity();
                    }
                    break;
                    case 7: {
                        setState(76);
                        sequence();
                    }
                    break;
                    case 8: {
                        setState(77);
                        match(WORD);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(91);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(89);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                                case 1: {
                                    _localctx = new QueryNodeContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryNode);
                                    setState(80);
                                    if (!(precpred(_ctx, 10)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 10)");
                                    setState(81);
                                    binaryOp();
                                    setState(82);
                                    queryNode(11);
                                }
                                break;
                                case 2: {
                                    _localctx = new QueryNodeContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryNode);
                                    setState(84);
                                    if (!(precpred(_ctx, 9)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 9)");
                                    setState(85);
                                    queryNode(10);
                                }
                                break;
                                case 3: {
                                    _localctx = new QueryNodeContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_queryNode);
                                    setState(86);
                                    if (!(precpred(_ctx, 7)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 7)");
                                    setState(87);
                                    match(LT);
                                    setState(88);
                                    queryNode(8);
                                }
                                break;
                            }
                        }
                    }
                    setState(93);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
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

    public final IndexTermContext indexTerm() throws RecognitionException {
        IndexTermContext _localctx = new IndexTermContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_indexTerm);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(94);
                match(WORD);
                setState(95);
                match(COLON);
                setState(96);
                simpleNode(0);
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

    public final SimpleNodeContext simpleNode() throws RecognitionException {
        return simpleNode(0);
    }

    private SimpleNodeContext simpleNode(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        SimpleNodeContext _localctx = new SimpleNodeContext(_ctx, _parentState);
        SimpleNodeContext _prevctx = _localctx;
        int _startState = 8;
        enterRecursionRule(_localctx, 8, RULE_simpleNode, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(107);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case PAREN_LEFT: {
                        setState(99);
                        match(PAREN_LEFT);
                        setState(100);
                        simpleNode(0);
                        setState(101);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case NOT: {
                        setState(103);
                        unaryOp();
                        setState(104);
                        simpleNode(3);
                    }
                    break;
                    case WORD: {
                        setState(106);
                        match(WORD);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(117);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(115);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
                                case 1: {
                                    _localctx = new SimpleNodeContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_simpleNode);
                                    setState(109);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(110);
                                    binaryOp();
                                    setState(111);
                                    simpleNode(5);
                                }
                                break;
                                case 2: {
                                    _localctx = new SimpleNodeContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_simpleNode);
                                    setState(113);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(114);
                                    simpleNode(3);
                                }
                                break;
                            }
                        }
                    }
                    setState(119);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
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

    public final NertagSemContext nertagSem() throws RecognitionException {
        NertagSemContext _localctx = new NertagSemContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_nertagSem);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(120);
                match(WORD);
                setState(121);
                match(COLON);
                setState(122);
                match(WORD);
                setState(123);
                match(EXPONENT);
                setState(124);
                sem();
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

    public final SemContext sem() throws RecognitionException {
        SemContext _localctx = new SemContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_sem);
        try {
            setState(139);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(126);
                    match(PAREN_LEFT);
                    setState(127);
                    sem();
                    setState(128);
                    match(PAREN_RIGHT);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(130);
                    match(PAREN_LEFT);
                    setState(131);
                    sem();
                    setState(132);
                    match(EXPONENT);
                    setState(133);
                    sem();
                    setState(134);
                    match(PAREN_RIGHT);
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(136);
                    match(WORD);
                    setState(137);
                    match(DOT);
                    setState(138);
                    indexTerm();
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

    public final ProximityContext proximity() throws RecognitionException {
        ProximityContext _localctx = new ProximityContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_proximity);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(141);
                match(PAREN_LEFT);
                setState(142);
                queryNode(0);
                setState(143);
                match(PAREN_RIGHT);
                setState(144);
                match(SIMILARITY);
                setState(145);
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

    public final IndexContext index() throws RecognitionException {
        return index(0);
    }

    private IndexContext index(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        IndexContext _localctx = new IndexContext(_ctx, _parentState);
        IndexContext _prevctx = _localctx;
        int _startState = 16;
        enterRecursionRule(_localctx, 16, RULE_index, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(156);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case PAREN_LEFT: {
                        setState(148);
                        match(PAREN_LEFT);
                        setState(149);
                        index(0);
                        setState(150);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case NOT: {
                        setState(152);
                        unaryOp();
                        setState(153);
                        index(2);
                    }
                    break;
                    case NUMBER: {
                        setState(155);
                        match(NUMBER);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(163);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new IndexContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_index);
                                setState(158);
                                if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                setState(159);
                                match(OR);
                                setState(160);
                                index(4);
                            }
                        }
                    }
                    setState(165);
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

    public final DiffContext diff() throws RecognitionException {
        DiffContext _localctx = new DiffContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_diff);
        try {
            setState(169);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case EOF:
                case SEP:
                    enterOuterAlt(_localctx, 1);
                {
                }
                break;
                case SENT:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(167);
                    match(SENT);
                }
                break;
                case PAR:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(168);
                    match(PAR);
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

    public final QueryReferenceContext queryReference() throws RecognitionException {
        QueryReferenceContext _localctx = new QueryReferenceContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_queryReference);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(171);
                match(NUMBER);
                setState(172);
                match(COLON);
                setState(173);
                match(PAREN_LEFT);
                setState(174);
                nodeWithoutReference(0);
                setState(175);
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

    public final SequenceContext sequence() throws RecognitionException {
        SequenceContext _localctx = new SequenceContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_sequence);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(177);
                match(QUOTATION);
                setState(178);
                arbTerm();
                setState(179);
                arb();
                setState(180);
                match(QUOTATION);
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

    public final ArbContext arb() throws RecognitionException {
        ArbContext _localctx = new ArbContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_arb);
        try {
            setState(186);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(182);
                    arbTerm();
                    setState(183);
                    arb();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(185);
                    arbTermLast();
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

    public final ArbTermContext arbTerm() throws RecognitionException {
        ArbTermContext _localctx = new ArbTermContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_arbTerm);
        try {
            setState(196);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(188);
                    match(PAREN_LEFT);
                    setState(189);
                    arbTermWithOp();
                    setState(190);
                    match(PAREN_RIGHT);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(192);
                    queryReference();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(193);
                    nertagSem();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(194);
                    indexTerm();
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(195);
                    match(WORD);
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

    public final ArbTermLastContext arbTermLast() throws RecognitionException {
        ArbTermLastContext _localctx = new ArbTermLastContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_arbTermLast);
        try {
            setState(206);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(198);
                    match(PAREN_LEFT);
                    setState(199);
                    arbTermWithOp();
                    setState(200);
                    match(PAREN_RIGHT);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(202);
                    queryReference();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(203);
                    nertagSem();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(204);
                    indexTerm();
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(205);
                    match(WORD);
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

    public final ArbTermWithOpContext arbTermWithOp() throws RecognitionException {
        ArbTermWithOpContext _localctx = new ArbTermWithOpContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_arbTermWithOp);
        try {
            setState(227);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(208);
                    arbTerm();
                    setState(209);
                    binaryOp();
                    setState(210);
                    arb();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(212);
                    unaryOp();
                    setState(213);
                    arbTerm();
                    setState(214);
                    binaryOp();
                    setState(215);
                    arb();
                }
                break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                {
                    setState(217);
                    unaryOp();
                    setState(218);
                    arbTerm();
                    setState(219);
                    arb();
                }
                break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                {
                    setState(221);
                    unaryOp();
                    setState(222);
                    arb();
                }
                break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                {
                    setState(224);
                    arbTerm();
                    setState(225);
                    arb();
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

    public final NodeWithoutReferenceContext nodeWithoutReference() throws RecognitionException {
        return nodeWithoutReference(0);
    }

    private NodeWithoutReferenceContext nodeWithoutReference(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        NodeWithoutReferenceContext _localctx = new NodeWithoutReferenceContext(_ctx, _parentState);
        NodeWithoutReferenceContext _prevctx = _localctx;
        int _startState = 32;
        enterRecursionRule(_localctx, 32, RULE_nodeWithoutReference, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(242);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
                    case 1: {
                        setState(230);
                        match(PAREN_LEFT);
                        setState(231);
                        nodeWithoutReference(0);
                        setState(232);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case 2: {
                        setState(234);
                        unaryOp();
                        setState(235);
                        nodeWithoutReference(7);
                    }
                    break;
                    case 3: {
                        setState(237);
                        nertagSem();
                    }
                    break;
                    case 4: {
                        setState(238);
                        indexTerm();
                    }
                    break;
                    case 5: {
                        setState(239);
                        proximity();
                    }
                    break;
                    case 6: {
                        setState(240);
                        sequence();
                    }
                    break;
                    case 7: {
                        setState(241);
                        match(WORD);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(255);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(253);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
                                case 1: {
                                    _localctx = new NodeWithoutReferenceContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_nodeWithoutReference);
                                    setState(244);
                                    if (!(precpred(_ctx, 9)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 9)");
                                    setState(245);
                                    binaryOp();
                                    setState(246);
                                    nodeWithoutReference(10);
                                }
                                break;
                                case 2: {
                                    _localctx = new NodeWithoutReferenceContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_nodeWithoutReference);
                                    setState(248);
                                    if (!(precpred(_ctx, 8)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                                    setState(249);
                                    nodeWithoutReference(9);
                                }
                                break;
                                case 3: {
                                    _localctx = new NodeWithoutReferenceContext(_parentctx, _parentState);
                                    pushNewRecursionContext(_localctx, _startState, RULE_nodeWithoutReference);
                                    setState(250);
                                    if (!(precpred(_ctx, 6)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                                    setState(251);
                                    match(LT);
                                    setState(252);
                                    nodeWithoutReference(7);
                                }
                                break;
                            }
                        }
                    }
                    setState(257);
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

    public final ConstraintNodeContext constraintNode() throws RecognitionException {
        return constraintNode(0);
    }

    private ConstraintNodeContext constraintNode(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        ConstraintNodeContext _localctx = new ConstraintNodeContext(_ctx, _parentState);
        ConstraintNodeContext _prevctx = _localctx;
        int _startState = 34;
        enterRecursionRule(_localctx, 34, RULE_constraintNode, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(267);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case PAREN_LEFT: {
                        setState(259);
                        match(PAREN_LEFT);
                        setState(260);
                        constraintNode(0);
                        setState(261);
                        match(PAREN_RIGHT);
                    }
                    break;
                    case NOT: {
                        setState(263);
                        unaryOp();
                        setState(264);
                        constraintNode(2);
                    }
                    break;
                    case NUMBER: {
                        setState(266);
                        constraint();
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(275);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            {
                                _localctx = new ConstraintNodeContext(_parentctx, _parentState);
                                pushNewRecursionContext(_localctx, _startState, RULE_constraintNode);
                                setState(269);
                                if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                setState(270);
                                binaryOp();
                                setState(271);
                                constraintNode(4);
                            }
                        }
                    }
                    setState(277);
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

    public final ConstraintContext constraint() throws RecognitionException {
        ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_constraint);
        try {
            setState(286);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 21, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(278);
                    reference();
                    setState(279);
                    relOp();
                    setState(280);
                    reference();
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(282);
                    reference();
                    setState(283);
                    relOp();
                    setState(284);
                    match(WORD);
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

    public final ReferenceContext reference() throws RecognitionException {
        ReferenceContext _localctx = new ReferenceContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_reference);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(288);
                match(NUMBER);
                setState(289);
                match(DOT);
                setState(290);
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

    public final BinaryOpContext binaryOp() throws RecognitionException {
        BinaryOpContext _localctx = new BinaryOpContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_binaryOp);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(292);
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
        enterRule(_localctx, 42, RULE_unaryOp);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(294);
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

    public final RelOpContext relOp() throws RecognitionException {
        RelOpContext _localctx = new RelOpContext(_ctx, getState());
        enterRule(_localctx, 44, RULE_relOp);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(296);
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

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 2:
                return queryNode_sempred((QueryNodeContext) _localctx, predIndex);
            case 4:
                return simpleNode_sempred((SimpleNodeContext) _localctx, predIndex);
            case 8:
                return index_sempred((IndexContext) _localctx, predIndex);
            case 16:
                return nodeWithoutReference_sempred((NodeWithoutReferenceContext) _localctx, predIndex);
            case 17:
                return constraintNode_sempred((ConstraintNodeContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean queryNode_sempred(QueryNodeContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 10);
            case 1:
                return precpred(_ctx, 9);
            case 2:
                return precpred(_ctx, 7);
        }
        return true;
    }

    private boolean simpleNode_sempred(SimpleNodeContext _localctx, int predIndex) {
        switch (predIndex) {
            case 3:
                return precpred(_ctx, 4);
            case 4:
                return precpred(_ctx, 2);
        }
        return true;
    }

    private boolean index_sempred(IndexContext _localctx, int predIndex) {
        switch (predIndex) {
            case 5:
                return precpred(_ctx, 3);
        }
        return true;
    }

    private boolean nodeWithoutReference_sempred(NodeWithoutReferenceContext _localctx, int predIndex) {
        switch (predIndex) {
            case 6:
                return precpred(_ctx, 9);
            case 7:
                return precpred(_ctx, 8);
            case 8:
                return precpred(_ctx, 6);
        }
        return true;
    }

    private boolean constraintNode_sempred(ConstraintNodeContext _localctx, int predIndex) {
        switch (predIndex) {
            case 9:
                return precpred(_ctx, 3);
        }
        return true;
    }

    public static class ExpressionContext extends ParserRuleContext {
        public ExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public QueryContext query() {
            return getRuleContext(QueryContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(Mg4jParser.EOF, 0);
        }

        public TerminalNode SEP() {
            return getToken(Mg4jParser.SEP, 0);
        }

        public ConstraintNodeContext constraintNode() {
            return getRuleContext(ConstraintNodeContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class QueryContext extends ParserRuleContext {
        public QueryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<QueryNodeContext> queryNode() {
            return getRuleContexts(QueryNodeContext.class);
        }

        public QueryNodeContext queryNode(int i) {
            return getRuleContext(QueryNodeContext.class, i);
        }

        public TerminalNode MINUS() {
            return getToken(Mg4jParser.MINUS, 0);
        }

        public DiffContext diff() {
            return getRuleContext(DiffContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_query;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterQuery(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitQuery(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitQuery(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class QueryNodeContext extends ParserRuleContext {
        public QueryNodeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public List<QueryNodeContext> queryNode() {
            return getRuleContexts(QueryNodeContext.class);
        }

        public QueryNodeContext queryNode(int i) {
            return getRuleContext(QueryNodeContext.class, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        public UnaryOpContext unaryOp() {
            return getRuleContext(UnaryOpContext.class, 0);
        }

        public QueryReferenceContext queryReference() {
            return getRuleContext(QueryReferenceContext.class, 0);
        }

        public NertagSemContext nertagSem() {
            return getRuleContext(NertagSemContext.class, 0);
        }

        public IndexTermContext indexTerm() {
            return getRuleContext(IndexTermContext.class, 0);
        }

        public ProximityContext proximity() {
            return getRuleContext(ProximityContext.class, 0);
        }

        public SequenceContext sequence() {
            return getRuleContext(SequenceContext.class, 0);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jParser.WORD, 0);
        }

        public BinaryOpContext binaryOp() {
            return getRuleContext(BinaryOpContext.class, 0);
        }

        public TerminalNode LT() {
            return getToken(Mg4jParser.LT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_queryNode;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterQueryNode(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitQueryNode(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitQueryNode(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IndexTermContext extends ParserRuleContext {
        public IndexTermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jParser.WORD, 0);
        }

        public TerminalNode COLON() {
            return getToken(Mg4jParser.COLON, 0);
        }

        public SimpleNodeContext simpleNode() {
            return getRuleContext(SimpleNodeContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_indexTerm;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterIndexTerm(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitIndexTerm(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitIndexTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SimpleNodeContext extends ParserRuleContext {
        public SimpleNodeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public List<SimpleNodeContext> simpleNode() {
            return getRuleContexts(SimpleNodeContext.class);
        }

        public SimpleNodeContext simpleNode(int i) {
            return getRuleContext(SimpleNodeContext.class, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        public UnaryOpContext unaryOp() {
            return getRuleContext(UnaryOpContext.class, 0);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jParser.WORD, 0);
        }

        public BinaryOpContext binaryOp() {
            return getRuleContext(BinaryOpContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_simpleNode;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterSimpleNode(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitSimpleNode(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitSimpleNode(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NertagSemContext extends ParserRuleContext {
        public NertagSemContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> WORD() {
            return getTokens(Mg4jParser.WORD);
        }

        public TerminalNode WORD(int i) {
            return getToken(Mg4jParser.WORD, i);
        }

        public TerminalNode COLON() {
            return getToken(Mg4jParser.COLON, 0);
        }

        public TerminalNode EXPONENT() {
            return getToken(Mg4jParser.EXPONENT, 0);
        }

        public SemContext sem() {
            return getRuleContext(SemContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nertagSem;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterNertagSem(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitNertagSem(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitNertagSem(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SemContext extends ParserRuleContext {
        public SemContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public List<SemContext> sem() {
            return getRuleContexts(SemContext.class);
        }

        public SemContext sem(int i) {
            return getRuleContext(SemContext.class, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        public TerminalNode EXPONENT() {
            return getToken(Mg4jParser.EXPONENT, 0);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jParser.WORD, 0);
        }

        public TerminalNode DOT() {
            return getToken(Mg4jParser.DOT, 0);
        }

        public IndexTermContext indexTerm() {
            return getRuleContext(IndexTermContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_sem;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterSem(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitSem(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitSem(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ProximityContext extends ParserRuleContext {
        public ProximityContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public QueryNodeContext queryNode() {
            return getRuleContext(QueryNodeContext.class, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        public TerminalNode SIMILARITY() {
            return getToken(Mg4jParser.SIMILARITY, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(Mg4jParser.NUMBER, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_proximity;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterProximity(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitProximity(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitProximity(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IndexContext extends ParserRuleContext {
        public IndexContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public List<IndexContext> index() {
            return getRuleContexts(IndexContext.class);
        }

        public IndexContext index(int i) {
            return getRuleContext(IndexContext.class, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        public UnaryOpContext unaryOp() {
            return getRuleContext(UnaryOpContext.class, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(Mg4jParser.NUMBER, 0);
        }

        public TerminalNode OR() {
            return getToken(Mg4jParser.OR, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_index;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterIndex(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitIndex(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitIndex(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class DiffContext extends ParserRuleContext {
        public DiffContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode SENT() {
            return getToken(Mg4jParser.SENT, 0);
        }

        public TerminalNode PAR() {
            return getToken(Mg4jParser.PAR, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_diff;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterDiff(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitDiff(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitDiff(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class QueryReferenceContext extends ParserRuleContext {
        public QueryReferenceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode NUMBER() {
            return getToken(Mg4jParser.NUMBER, 0);
        }

        public TerminalNode COLON() {
            return getToken(Mg4jParser.COLON, 0);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public NodeWithoutReferenceContext nodeWithoutReference() {
            return getRuleContext(NodeWithoutReferenceContext.class, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_queryReference;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterQueryReference(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitQueryReference(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitQueryReference(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SequenceContext extends ParserRuleContext {
        public SequenceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> QUOTATION() {
            return getTokens(Mg4jParser.QUOTATION);
        }

        public TerminalNode QUOTATION(int i) {
            return getToken(Mg4jParser.QUOTATION, i);
        }

        public ArbTermContext arbTerm() {
            return getRuleContext(ArbTermContext.class, 0);
        }

        public ArbContext arb() {
            return getRuleContext(ArbContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_sequence;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterSequence(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitSequence(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitSequence(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ArbContext extends ParserRuleContext {
        public ArbContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ArbTermContext arbTerm() {
            return getRuleContext(ArbTermContext.class, 0);
        }

        public ArbContext arb() {
            return getRuleContext(ArbContext.class, 0);
        }

        public ArbTermLastContext arbTermLast() {
            return getRuleContext(ArbTermLastContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_arb;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterArb(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitArb(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitArb(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ArbTermContext extends ParserRuleContext {
        public ArbTermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public ArbTermWithOpContext arbTermWithOp() {
            return getRuleContext(ArbTermWithOpContext.class, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        public QueryReferenceContext queryReference() {
            return getRuleContext(QueryReferenceContext.class, 0);
        }

        public NertagSemContext nertagSem() {
            return getRuleContext(NertagSemContext.class, 0);
        }

        public IndexTermContext indexTerm() {
            return getRuleContext(IndexTermContext.class, 0);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jParser.WORD, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_arbTerm;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterArbTerm(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitArbTerm(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitArbTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ArbTermLastContext extends ParserRuleContext {
        public ArbTermLastContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public ArbTermWithOpContext arbTermWithOp() {
            return getRuleContext(ArbTermWithOpContext.class, 0);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        public QueryReferenceContext queryReference() {
            return getRuleContext(QueryReferenceContext.class, 0);
        }

        public NertagSemContext nertagSem() {
            return getRuleContext(NertagSemContext.class, 0);
        }

        public IndexTermContext indexTerm() {
            return getRuleContext(IndexTermContext.class, 0);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jParser.WORD, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_arbTermLast;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterArbTermLast(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitArbTermLast(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitArbTermLast(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ArbTermWithOpContext extends ParserRuleContext {
        public ArbTermWithOpContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ArbTermContext arbTerm() {
            return getRuleContext(ArbTermContext.class, 0);
        }

        public BinaryOpContext binaryOp() {
            return getRuleContext(BinaryOpContext.class, 0);
        }

        public ArbContext arb() {
            return getRuleContext(ArbContext.class, 0);
        }

        public UnaryOpContext unaryOp() {
            return getRuleContext(UnaryOpContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_arbTermWithOp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterArbTermWithOp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitArbTermWithOp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitArbTermWithOp(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NodeWithoutReferenceContext extends ParserRuleContext {
        public NodeWithoutReferenceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public List<NodeWithoutReferenceContext> nodeWithoutReference() {
            return getRuleContexts(NodeWithoutReferenceContext.class);
        }

        public NodeWithoutReferenceContext nodeWithoutReference(int i) {
            return getRuleContext(NodeWithoutReferenceContext.class, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        public UnaryOpContext unaryOp() {
            return getRuleContext(UnaryOpContext.class, 0);
        }

        public NertagSemContext nertagSem() {
            return getRuleContext(NertagSemContext.class, 0);
        }

        public IndexTermContext indexTerm() {
            return getRuleContext(IndexTermContext.class, 0);
        }

        public ProximityContext proximity() {
            return getRuleContext(ProximityContext.class, 0);
        }

        public SequenceContext sequence() {
            return getRuleContext(SequenceContext.class, 0);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jParser.WORD, 0);
        }

        public BinaryOpContext binaryOp() {
            return getRuleContext(BinaryOpContext.class, 0);
        }

        public TerminalNode LT() {
            return getToken(Mg4jParser.LT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nodeWithoutReference;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterNodeWithoutReference(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitNodeWithoutReference(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor)
                return ((Mg4jVisitor<? extends T>) visitor).visitNodeWithoutReference(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ConstraintNodeContext extends ParserRuleContext {
        public ConstraintNodeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PAREN_LEFT() {
            return getToken(Mg4jParser.PAREN_LEFT, 0);
        }

        public List<ConstraintNodeContext> constraintNode() {
            return getRuleContexts(ConstraintNodeContext.class);
        }

        public ConstraintNodeContext constraintNode(int i) {
            return getRuleContext(ConstraintNodeContext.class, i);
        }

        public TerminalNode PAREN_RIGHT() {
            return getToken(Mg4jParser.PAREN_RIGHT, 0);
        }

        public UnaryOpContext unaryOp() {
            return getRuleContext(UnaryOpContext.class, 0);
        }

        public ConstraintContext constraint() {
            return getRuleContext(ConstraintContext.class, 0);
        }

        public BinaryOpContext binaryOp() {
            return getRuleContext(BinaryOpContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_constraintNode;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterConstraintNode(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitConstraintNode(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitConstraintNode(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ConstraintContext extends ParserRuleContext {
        public ConstraintContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
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
            return getToken(Mg4jParser.WORD, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_constraint;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterConstraint(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitConstraint(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitConstraint(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ReferenceContext extends ParserRuleContext {
        public ReferenceContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode NUMBER() {
            return getToken(Mg4jParser.NUMBER, 0);
        }

        public TerminalNode DOT() {
            return getToken(Mg4jParser.DOT, 0);
        }

        public TerminalNode WORD() {
            return getToken(Mg4jParser.WORD, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_reference;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterReference(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitReference(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitReference(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class BinaryOpContext extends ParserRuleContext {
        public BinaryOpContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode OR() {
            return getToken(Mg4jParser.OR, 0);
        }

        public TerminalNode AND() {
            return getToken(Mg4jParser.AND, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_binaryOp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterBinaryOp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitBinaryOp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitBinaryOp(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class UnaryOpContext extends ParserRuleContext {
        public UnaryOpContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode NOT() {
            return getToken(Mg4jParser.NOT, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_unaryOp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterUnaryOp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitUnaryOp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitUnaryOp(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class RelOpContext extends ParserRuleContext {
        public RelOpContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EQ() {
            return getToken(Mg4jParser.EQ, 0);
        }

        public TerminalNode NEQ() {
            return getToken(Mg4jParser.NEQ, 0);
        }

        public TerminalNode LT() {
            return getToken(Mg4jParser.LT, 0);
        }

        public TerminalNode LE() {
            return getToken(Mg4jParser.LE, 0);
        }

        public TerminalNode GT() {
            return getToken(Mg4jParser.GT, 0);
        }

        public TerminalNode GE() {
            return getToken(Mg4jParser.GE, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_relOp;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).enterRelOp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Mg4jListener) ((Mg4jListener) listener).exitRelOp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof Mg4jVisitor) return ((Mg4jVisitor<? extends T>) visitor).visitRelOp(this);
            else return visitor.visitChildren(this);
        }
    }
}