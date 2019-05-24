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
            EQ = 1, NEQ = 2, LT = 3, LE = 4, GT = 5, GE = 6, PAREN_LEFT = 7, PAREN_RIGHT = 8, DOT = 9,
            OR = 10, AND = 11, NOT = 12, NUMBER = 13, WORD = 14, WS = 15;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21U\b\1\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\3\3\3\3\3" +
                    "\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13" +
                    "\3\13\3\13\5\13:\n\13\3\f\3\f\3\f\3\f\5\f@\n\f\3\r\3\r\3\r\3\r\5\rF\n" +
                    "\r\3\16\6\16I\n\16\r\16\16\16J\3\17\6\17N\n\17\r\17\16\17O\3\20\3\20\3" +
                    "\20\3\20\2\2\21\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31" +
                    "\16\33\17\35\20\37\21\3\2\5\3\2\62;\5\2\62;C\\c|\4\2\13\13\"\"\2Y\2\3" +
                    "\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2" +
                    "\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31" +
                    "\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\3!\3\2\2\2\5#\3\2\2\2" +
                    "\7&\3\2\2\2\t(\3\2\2\2\13+\3\2\2\2\r-\3\2\2\2\17\60\3\2\2\2\21\62\3\2" +
                    "\2\2\23\64\3\2\2\2\259\3\2\2\2\27?\3\2\2\2\31E\3\2\2\2\33H\3\2\2\2\35" +
                    "M\3\2\2\2\37Q\3\2\2\2!\"\7?\2\2\"\4\3\2\2\2#$\7#\2\2$%\7?\2\2%\6\3\2\2" +
                    "\2&\'\7>\2\2\'\b\3\2\2\2()\7>\2\2)*\7?\2\2*\n\3\2\2\2+,\7@\2\2,\f\3\2" +
                    "\2\2-.\7@\2\2./\7?\2\2/\16\3\2\2\2\60\61\7*\2\2\61\20\3\2\2\2\62\63\7" +
                    "+\2\2\63\22\3\2\2\2\64\65\7\60\2\2\65\24\3\2\2\2\66:\7~\2\2\678\7q\2\2" +
                    "8:\7t\2\29\66\3\2\2\29\67\3\2\2\2:\26\3\2\2\2;@\7(\2\2<=\7c\2\2=>\7p\2" +
                    "\2>@\7f\2\2?;\3\2\2\2?<\3\2\2\2@\30\3\2\2\2AF\7#\2\2BC\7p\2\2CD\7q\2\2" +
                    "DF\7v\2\2EA\3\2\2\2EB\3\2\2\2F\32\3\2\2\2GI\t\2\2\2HG\3\2\2\2IJ\3\2\2" +
                    "\2JH\3\2\2\2JK\3\2\2\2K\34\3\2\2\2LN\t\3\2\2ML\3\2\2\2NO\3\2\2\2OM\3\2" +
                    "\2\2OP\3\2\2\2P\36\3\2\2\2QR\t\4\2\2RS\3\2\2\2ST\b\20\2\2T \3\2\2\2\b" +
                    "\29?EJO\3\b\2\2";
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
                "EQ", "NEQ", "LT", "LE", "GT", "GE", "PAREN_LEFT", "PAREN_RIGHT", "DOT",
                "OR", "AND", "NOT", "NUMBER", "WORD", "WS"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'='", "'!='", "'<'", "'<='", "'>'", "'>='", "'('", "')'", "'.'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "EQ", "NEQ", "LT", "LE", "GT", "GE", "PAREN_LEFT", "PAREN_RIGHT",
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