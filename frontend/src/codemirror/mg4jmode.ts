import CodeMirror, {Mode} from 'codemirror';
import './tokenStyles.css';
import {readOneToken, Token} from "./SimpleLanguageMode";

export const MG4J_EQL = 'mg4j-eql';

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
        return readOneToken(stream, tokens);
    }
};

CodeMirror.defineMode(MG4J_EQL, () => mg4jLanguageMode);

