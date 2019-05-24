// Generated from /home/dkozak/projects/knot/enticing/mg4j-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/mg4j/compiler/Mg4j.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.mg4j.compiler;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Mg4jLexer extends Lexer {
    public static final int
            MINUS = 1, COLON = 2, EXPONENT = 3, SIMILARITY = 4, SENT = 5, PAR = 6, QUOTATION = 7,
            SEP = 8, EQ = 9, NEQ = 10, LT = 11, LE = 12, GT = 13, GE = 14, PAREN_LEFT = 15, PAREN_RIGHT = 16,
            DOT = 17, OR = 18, AND = 19, NOT = 20, NUMBER = 21, WORD = 22, WS = 23;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\31\177\b\1\4\2\t" +
                    "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\3\2\3" +
                    "\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7" +
                    "\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3" +
                    "\16\3\16\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\5" +
                    "\23d\n\23\3\24\3\24\3\24\3\24\5\24j\n\24\3\25\3\25\3\25\3\25\5\25p\n\25" +
                    "\3\26\6\26s\n\26\r\26\16\26t\3\27\6\27x\n\27\r\27\16\27y\3\30\3\30\3\30" +
                    "\3\30\2\2\31\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16" +
                    "\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\3\2\5\3\2\62;\6\2" +
                    "\62;C\\aac|\4\2\13\13\"\"\2\u0083\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2" +
                    "\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3" +
                    "\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2" +
                    "\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2" +
                    "\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\3\61\3\2\2\2\5\63\3\2\2\2\7\65\3\2" +
                    "\2\2\t\67\3\2\2\2\139\3\2\2\2\r@\3\2\2\2\17F\3\2\2\2\21H\3\2\2\2\23K\3" +
                    "\2\2\2\25M\3\2\2\2\27P\3\2\2\2\31R\3\2\2\2\33U\3\2\2\2\35W\3\2\2\2\37" +
                    "Z\3\2\2\2!\\\3\2\2\2#^\3\2\2\2%c\3\2\2\2\'i\3\2\2\2)o\3\2\2\2+r\3\2\2" +
                    "\2-w\3\2\2\2/{\3\2\2\2\61\62\7/\2\2\62\4\3\2\2\2\63\64\7<\2\2\64\6\3\2" +
                    "\2\2\65\66\7`\2\2\66\b\3\2\2\2\678\7\u0080\2\28\n\3\2\2\29:\7a\2\2:;\7" +
                    "U\2\2;<\7G\2\2<=\7P\2\2=>\7V\2\2>?\7a\2\2?\f\3\2\2\2@A\7a\2\2AB\7R\2\2" +
                    "BC\7C\2\2CD\7T\2\2DE\7a\2\2E\16\3\2\2\2FG\7$\2\2G\20\3\2\2\2HI\7(\2\2" +
                    "IJ\7(\2\2J\22\3\2\2\2KL\7?\2\2L\24\3\2\2\2MN\7#\2\2NO\7?\2\2O\26\3\2\2" +
                    "\2PQ\7>\2\2Q\30\3\2\2\2RS\7>\2\2ST\7?\2\2T\32\3\2\2\2UV\7@\2\2V\34\3\2" +
                    "\2\2WX\7@\2\2XY\7?\2\2Y\36\3\2\2\2Z[\7*\2\2[ \3\2\2\2\\]\7+\2\2]\"\3\2" +
                    "\2\2^_\7\60\2\2_$\3\2\2\2`d\7~\2\2ab\7q\2\2bd\7t\2\2c`\3\2\2\2ca\3\2\2" +
                    "\2d&\3\2\2\2ej\7(\2\2fg\7c\2\2gh\7p\2\2hj\7f\2\2ie\3\2\2\2if\3\2\2\2j" +
                    "(\3\2\2\2kp\7#\2\2lm\7p\2\2mn\7q\2\2np\7v\2\2ok\3\2\2\2ol\3\2\2\2p*\3" +
                    "\2\2\2qs\t\2\2\2rq\3\2\2\2st\3\2\2\2tr\3\2\2\2tu\3\2\2\2u,\3\2\2\2vx\t" +
                    "\3\2\2wv\3\2\2\2xy\3\2\2\2yw\3\2\2\2yz\3\2\2\2z.\3\2\2\2{|\t\4\2\2|}\3" +
                    "\2\2\2}~\b\30\2\2~\60\3\2\2\2\b\2cioty\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

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

    public Mg4jLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "MINUS", "COLON", "EXPONENT", "SIMILARITY", "SENT", "PAR", "QUOTATION",
                "SEP", "EQ", "NEQ", "LT", "LE", "GT", "GE", "PAREN_LEFT", "PAREN_RIGHT",
                "DOT", "OR", "AND", "NOT", "NUMBER", "WORD", "WS"
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
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}