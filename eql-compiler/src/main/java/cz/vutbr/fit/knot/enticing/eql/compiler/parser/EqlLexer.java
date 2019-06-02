// Generated from /home/dkozak/projects/knot/enticing/eql-compiler/src/main/resources/Eql.g4 by ANTLR 4.7.2
package cz.vutbr.fit.knot.enticing.eql.compiler.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EqlLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
            ASSIGN = 1, COLON = 2, EXPONENT = 3, SIMILARITY = 4, SENT = 5, PAR = 6, QUOTATION = 7,
            GLOBAL_CONSTRAINT_SEPARATOR = 8, EQ = 9, NEQ = 10, LT = 11, PAREN_LEFT = 12, PAREN_RIGHT = 13,
            BRACKET_LEFT = 14, BRACKET_RIGHT = 15, RANGE = 16, DOT = 17, OR = 18, AND = 19, NOT = 20,
            NUMBER = 21, DATE = 22, WORD = 23, WS = 24;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\32\u0096\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6" +
                    "\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3" +
                    "\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17" +
                    "\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26" +
                    "\6\26r\n\26\r\26\16\26s\3\27\6\27w\n\27\r\27\16\27x\3\27\3\27\6\27}\n" +
                    "\27\r\27\16\27~\3\27\3\27\6\27\u0083\n\27\r\27\16\27\u0084\3\30\6\30\u0088" +
                    "\n\30\r\30\16\30\u0089\3\30\5\30\u008d\n\30\3\31\3\31\3\32\3\32\3\33\3" +
                    "\33\3\33\3\33\2\2\34\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27" +
                    "\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\2\63\2" +
                    "\65\32\3\2\5\3\2\62;\6\2\62;C\\aac|\4\2\13\13\"\"\2\u0099\2\3\3\2\2\2" +
                    "\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2" +
                    "\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2" +
                    "\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2" +
                    "\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\65\3\2\2" +
                    "\2\3\67\3\2\2\2\5:\3\2\2\2\7<\3\2\2\2\t>\3\2\2\2\13@\3\2\2\2\rI\3\2\2" +
                    "\2\17Q\3\2\2\2\21S\3\2\2\2\23V\3\2\2\2\25X\3\2\2\2\27[\3\2\2\2\31]\3\2" +
                    "\2\2\33_\3\2\2\2\35a\3\2\2\2\37c\3\2\2\2!e\3\2\2\2#h\3\2\2\2%j\3\2\2\2" +
                    "\'l\3\2\2\2)n\3\2\2\2+q\3\2\2\2-v\3\2\2\2/\u0087\3\2\2\2\61\u008e\3\2" +
                    "\2\2\63\u0090\3\2\2\2\65\u0092\3\2\2\2\678\7<\2\289\7?\2\29\4\3\2\2\2" +
                    ":;\7<\2\2;\6\3\2\2\2<=\7`\2\2=\b\3\2\2\2>?\7\u0080\2\2?\n\3\2\2\2@A\7" +
                    "/\2\2AB\7\"\2\2BC\7a\2\2CD\7U\2\2DE\7G\2\2EF\7P\2\2FG\7V\2\2GH\7a\2\2" +
                    "H\f\3\2\2\2IJ\7/\2\2JK\7\"\2\2KL\7a\2\2LM\7R\2\2MN\7C\2\2NO\7T\2\2OP\7" +
                    "a\2\2P\16\3\2\2\2QR\7$\2\2R\20\3\2\2\2ST\7(\2\2TU\7(\2\2U\22\3\2\2\2V" +
                    "W\7?\2\2W\24\3\2\2\2XY\7#\2\2YZ\7?\2\2Z\26\3\2\2\2[\\\7>\2\2\\\30\3\2" +
                    "\2\2]^\7*\2\2^\32\3\2\2\2_`\7+\2\2`\34\3\2\2\2ab\7]\2\2b\36\3\2\2\2cd" +
                    "\7_\2\2d \3\2\2\2ef\7\60\2\2fg\7\60\2\2g\"\3\2\2\2hi\7\60\2\2i$\3\2\2" +
                    "\2jk\7~\2\2k&\3\2\2\2lm\7(\2\2m(\3\2\2\2no\7#\2\2o*\3\2\2\2pr\t\2\2\2" +
                    "qp\3\2\2\2rs\3\2\2\2sq\3\2\2\2st\3\2\2\2t,\3\2\2\2uw\t\2\2\2vu\3\2\2\2" +
                    "wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2yz\3\2\2\2z|\7\61\2\2{}\t\2\2\2|{\3\2\2" +
                    "\2}~\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177\u0080\3\2\2\2\u0080\u0082\7\61" +
                    "\2\2\u0081\u0083\t\2\2\2\u0082\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084" +
                    "\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085.\3\2\2\2\u0086\u0088\5\61\31" +
                    "\2\u0087\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a" +
                    "\3\2\2\2\u008a\u008c\3\2\2\2\u008b\u008d\5\63\32\2\u008c\u008b\3\2\2\2" +
                    "\u008c\u008d\3\2\2\2\u008d\60\3\2\2\2\u008e\u008f\t\3\2\2\u008f\62\3\2" +
                    "\2\2\u0090\u0091\7,\2\2\u0091\64\3\2\2\2\u0092\u0093\t\4\2\2\u0093\u0094" +
                    "\3\2\2\2\u0094\u0095\b\33\2\2\u0095\66\3\2\2\2\t\2sx~\u0084\u0089\u008c" +
                    "\3\b\2\2";
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

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

    public EqlLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "ASSIGN", "COLON", "EXPONENT", "SIMILARITY", "SENT", "PAR", "QUOTATION",
                "GLOBAL_CONSTRAINT_SEPARATOR", "EQ", "NEQ", "LT", "PAREN_LEFT", "PAREN_RIGHT",
                "BRACKET_LEFT", "BRACKET_RIGHT", "RANGE", "DOT", "OR", "AND", "NOT",
                "NUMBER", "DATE", "WORD", "ANY_VALID_CHAR", "WILDCARD", "WS"
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
        return "Eql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}