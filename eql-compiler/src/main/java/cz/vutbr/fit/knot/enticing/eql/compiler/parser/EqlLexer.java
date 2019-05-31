// Generated from /home/dkozak/projects/knot/enticing/eql-compiler/src/main/kotlin/cz/vutbr/fit/knot/enticing/eql/compiler/parser/Eql.g4 by ANTLR 4.7.2
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
		ARROW=1, MINUS=2, COLON=3, EXPONENT=4, SIMILARITY=5, SENT=6, PAR=7, QUOTATION=8, 
		QUERY_CONSTRAINT_SEPARATOR=9, EQ=10, NEQ=11, LT=12, LE=13, GT=14, GE=15,
            PAREN_LEFT = 16, PAREN_RIGHT = 17, BRACKET_LEFT = 18, BRACKET_RIGHT = 19, RANGE = 20,
            DOT = 21, OR = 22, AND = 23, NOT = 24, NUMBER = 25, DATE = 26, WORD = 27, WS = 28;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\36\u00a4\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2" +
                    "\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3" +
                    "\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3" +
                    "\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3" +
                    "\23\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3" +
                    "\32\6\32\u0080\n\32\r\32\16\32\u0081\3\33\6\33\u0085\n\33\r\33\16\33\u0086" +
                    "\3\33\3\33\6\33\u008b\n\33\r\33\16\33\u008c\3\33\3\33\6\33\u0091\n\33" +
                    "\r\33\16\33\u0092\3\34\6\34\u0096\n\34\r\34\16\34\u0097\3\34\5\34\u009b" +
                    "\n\34\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3\37\2\2 \3\3\5\4\7\5\t\6\13" +
                    "\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'" +
                    "\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\2;\2=\36\3\2\5\3\2\62;\6" +
                    "\2\62;C\\aac|\4\2\13\13\"\"\2\u00a7\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2" +
                    "\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23" +
                    "\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2" +
                    "\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2" +
                    "\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3" +
                    "\2\2\2\2\67\3\2\2\2\2=\3\2\2\2\3?\3\2\2\2\5B\3\2\2\2\7D\3\2\2\2\tF\3\2" +
                    "\2\2\13H\3\2\2\2\rJ\3\2\2\2\17Q\3\2\2\2\21W\3\2\2\2\23Y\3\2\2\2\25\\\3" +
                    "\2\2\2\27^\3\2\2\2\31a\3\2\2\2\33c\3\2\2\2\35f\3\2\2\2\37h\3\2\2\2!k\3" +
                    "\2\2\2#m\3\2\2\2%o\3\2\2\2\'q\3\2\2\2)s\3\2\2\2+v\3\2\2\2-x\3\2\2\2/z" +
                    "\3\2\2\2\61|\3\2\2\2\63\177\3\2\2\2\65\u0084\3\2\2\2\67\u0095\3\2\2\2" +
                    "9\u009c\3\2\2\2;\u009e\3\2\2\2=\u00a0\3\2\2\2?@\7>\2\2@A\7/\2\2A\4\3\2" +
                    "\2\2BC\7/\2\2C\6\3\2\2\2DE\7<\2\2E\b\3\2\2\2FG\7`\2\2G\n\3\2\2\2HI\7\u0080" +
                    "\2\2I\f\3\2\2\2JK\7a\2\2KL\7U\2\2LM\7G\2\2MN\7P\2\2NO\7V\2\2OP\7a\2\2" +
                    "P\16\3\2\2\2QR\7a\2\2RS\7R\2\2ST\7C\2\2TU\7T\2\2UV\7a\2\2V\20\3\2\2\2" +
                    "WX\7$\2\2X\22\3\2\2\2YZ\7(\2\2Z[\7(\2\2[\24\3\2\2\2\\]\7?\2\2]\26\3\2" +
                    "\2\2^_\7#\2\2_`\7?\2\2`\30\3\2\2\2ab\7>\2\2b\32\3\2\2\2cd\7>\2\2de\7?" +
                    "\2\2e\34\3\2\2\2fg\7@\2\2g\36\3\2\2\2hi\7@\2\2ij\7?\2\2j \3\2\2\2kl\7" +
                    "*\2\2l\"\3\2\2\2mn\7+\2\2n$\3\2\2\2op\7]\2\2p&\3\2\2\2qr\7_\2\2r(\3\2" +
                    "\2\2st\7\60\2\2tu\7\60\2\2u*\3\2\2\2vw\7\60\2\2w,\3\2\2\2xy\7~\2\2y.\3" +
                    "\2\2\2z{\7(\2\2{\60\3\2\2\2|}\7#\2\2}\62\3\2\2\2~\u0080\t\2\2\2\177~\3" +
                    "\2\2\2\u0080\u0081\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082" +
                    "\64\3\2\2\2\u0083\u0085\t\2\2\2\u0084\u0083\3\2\2\2\u0085\u0086\3\2\2" +
                    "\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008a" +
                    "\7\61\2\2\u0089\u008b\t\2\2\2\u008a\u0089\3\2\2\2\u008b\u008c\3\2\2\2" +
                    "\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u0090" +
                    "\7\61\2\2\u008f\u0091\t\2\2\2\u0090\u008f\3\2\2\2\u0091\u0092\3\2\2\2" +
                    "\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\66\3\2\2\2\u0094\u0096" +
                    "\59\35\2\u0095\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0095\3\2\2\2\u0097" +
                    "\u0098\3\2\2\2\u0098\u009a\3\2\2\2\u0099\u009b\5;\36\2\u009a\u0099\3\2" +
                    "\2\2\u009a\u009b\3\2\2\2\u009b8\3\2\2\2\u009c\u009d\t\3\2\2\u009d:\3\2" +
                    "\2\2\u009e\u009f\7,\2\2\u009f<\3\2\2\2\u00a0\u00a1\t\4\2\2\u00a1\u00a2" +
                    "\3\2\2\2\u00a2\u00a3\b\37\2\2\u00a3>\3\2\2\2\t\2\u0081\u0086\u008c\u0092" +
                    "\u0097\u009a\3\b\2\2";
    public static final String[] ruleNames = makeRuleNames();

    public EqlLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

	private static String[] makeRuleNames() {
		return new String[] {
                "ARROW", "MINUS", "COLON", "EXPONENT", "SIMILARITY", "SENT", "PAR", "QUOTATION",
                "QUERY_CONSTRAINT_SEPARATOR", "EQ", "NEQ", "LT", "LE", "GT", "GE", "PAREN_LEFT",
                "PAREN_RIGHT", "BRACKET_LEFT", "BRACKET_RIGHT", "RANGE", "DOT", "OR",
                "AND", "NOT", "NUMBER", "DATE", "WORD", "ANY_VALID_CHAR", "WILDCARD",
                "WS"
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

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'<-'", "'-'", "':'", "'^'", "'~'", "'_SENT_'", "'_PAR_'", "'\"'",
                "'&&'", "'='", "'!='", "'<'", "'<='", "'>'", "'>='", "'('", "')'", "'['",
                "']'", "'..'", "'.'", "'|'", "'&'", "'!'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "ARROW", "MINUS", "COLON", "EXPONENT", "SIMILARITY", "SENT", "PAR",
                "QUOTATION", "QUERY_CONSTRAINT_SEPARATOR", "EQ", "NEQ", "LT", "LE", "GT",
                "GE", "PAREN_LEFT", "PAREN_RIGHT", "BRACKET_LEFT", "BRACKET_RIGHT", "RANGE",
                "DOT", "OR", "AND", "NOT", "NUMBER", "DATE", "WORD", "WS"
        };
    }

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

    @Override
    public String getGrammarFileName() {
        return "Eql.g4";
    }
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}