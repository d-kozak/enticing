import CodeMirror, {Mode} from 'codemirror';
import './tokenStyles.css';
import {readOneToken, Token} from "./SimpleLanguageMode";

export const EQL = 'eql';

// example input :()&and|or!not-_PAR__SENT_not^.~"dafs"sadfsad &&
const tokens: Array<Token> = [
    {match: /'.*'/, token: 'raw'},
    {match: '&&', token: 'contraints'},
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
    {match: '<=', token: 'le'},
    {match: '<', token: 'lt'},
    {match: '>=', token: 'ge'},
    {match: '>', token: 'gt'},
    {match: '=', token: 'equal'},
    {match: '!=', token: 'not-equal'},
    {match: /^".*?"/, token: 'sequence'}
]

export const eqlLanguageMode: Mode<any> = {
    token(stream: CodeMirror.StringStream): string | null {
        return readOneToken(stream, tokens);
    }
};

CodeMirror.defineMode(EQL, () => eqlLanguageMode);


