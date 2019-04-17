import CodeMirror from "codemirror";

export interface Token {
    match: string | RegExp
    token: string
}

export const readOneToken = (stream: CodeMirror.StringStream, tokens: Array<Token>): string | null => {
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


const matches = (match: string | RegExp, stream: CodeMirror.StringStream): boolean => {
    if (typeof match === 'string') {
        return stream.match(match) != undefined
    }
    return stream.match(match) != null;
}
