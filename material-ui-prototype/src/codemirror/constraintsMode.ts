import CodeMirror, {Mode} from 'codemirror';
import './tokenStyles.css';
import {matches, Token} from "./mg4jmode";

export const CONSTRAINTS = 'constraints';


const tokens: Array<Token> = [
    {match: ')', token: 'mbracket'},
    {match: '(', token: 'mbracket'},
    {match: '&', token: 'and'},
    {match: 'and', token: 'and'},
    {match: '|', token: 'or'},
    {match: 'or', token: 'or'},
    {match: '=', token: 'equal'},
    {match: '!=', token: 'not-equal'},
    {match: '!', token: 'not'},
    {match: 'not', token: 'not'},
    {match: '.', token: 'dot'},
    {match: '<=', token: 'le'},
    {match: '<', token: 'lt'},
    {match: '>=', token: 'ge'},
    {match: '>', token: 'gt'},
]


export const constraintsLanguageMode: Mode<any> = {
    token(stream: CodeMirror.StringStream): string | null {
        // skip whitespace
        stream.eatWhile((c) => c == ' ' || c == '\t');

        for (let {match, token} of tokens) {
            if (matches(match, stream))
                return token;
        }

        // it is ineffective when moving by just one character in this case
        // but this strategy handles well unfinished input and
        // it is the easier thing to do until a full blown lexer is created
        stream.next();
        return null;
    }
};

CodeMirror.defineMode(CONSTRAINTS, () => constraintsLanguageMode);


