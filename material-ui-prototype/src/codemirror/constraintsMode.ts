import CodeMirror, {Mode} from 'codemirror';
import './tokenStyles.css';
import {readOneToken, Token} from "./SimpleLanguageMode";

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
        return readOneToken(stream, tokens);
    }
};

CodeMirror.defineMode(CONSTRAINTS, () => constraintsLanguageMode);


