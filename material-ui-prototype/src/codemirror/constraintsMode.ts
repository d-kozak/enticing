import CodeMirror, {Mode} from 'codemirror';
import './tokenStyles.css';

export const CONSTRAINTS = 'constraints';

export const constraintsLanguageMode: Mode<any> = {
    token(stream: CodeMirror.StringStream): string | null {
        stream.next();
        return null;
    }
};

CodeMirror.defineMode(CONSTRAINTS, () => constraintsLanguageMode);


