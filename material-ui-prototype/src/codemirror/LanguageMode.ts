import CodeMirror, {Mode} from 'codemirror';
import './LanguageMode.css';

export const MG4J_EQL = 'mg4j-eql';


interface Token {
    match: string | RegExp
    token: string
}

function matches(match: string | RegExp, stream: CodeMirror.StringStream): boolean {
    if (typeof match === 'string') {
        return stream.match(match) != undefined
    }
    return stream.match(match) != null;
}

// example input :()&and|or!not-_PAR__SENT_not^.~"dafs"sadfsad
const tokens: Array<Token> = [
    {match: ':', token: 'colon'},
    {match: ')', token: 'mbracket'},
    {match: '(', token: 'mbracket'},
    {match: '&', token: 'and'},
    {match: 'and', token: 'and'},
    {match: '|', token: 'or'},
    {match: 'or', token: 'or'},
    {match: '!', token: 'not'},
    {match: 'not', token: 'not'},
    {match: '-', token: 'minus'},
    {match: '_PAR_', token: 'par'},
    {match: '_SENT_', token: 'sent'},
    {match: 'not', token: 'not'},
    {match: '^', token: 'caret'},
    {match: '.', token: 'dot'},
    {match: '~', token: 'tilde'},
    {match: /^".*?"/, token: 'sequence'}
]

export const mg4jLanguageMode: Mode<any> = {
    token(stream: CodeMirror.StringStream): string | null {
        // skip whitespace
        stream.eatWhile((c) => c == ' ' || c == '\t');

        for (let {match, token} of tokens) {
            if (matches(match, stream))
                return token;
        }

        // it is ineffective when moving by just one character in this case
        // but this strategy handles well unfinisshed input and
        // it is the easier thing to do until a full blown lexer is created
        stream.next();
        return null;
    }
};

CodeMirror.defineMode(MG4J_EQL, () => mg4jLanguageMode);


