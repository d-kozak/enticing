import CodeMirror from 'codemirror';
import './LanguageMode.css';

export const MG4J_EQL = 'mg4j-eql';

CodeMirror.defineMode(MG4J_EQL, () => {
    return {
        token(stream: CodeMirror.StringStream) {
            const currentChar = stream.next();
            switch (currentChar) {
                case ':':
                    return 'colon';
                case ')':
                case '(':
                    return 'bracket';
            }
            return null;
        }
    }
});