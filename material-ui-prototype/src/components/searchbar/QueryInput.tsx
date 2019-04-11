import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import * as React from "react";
import {useEffect, useRef, useState} from "react";
import withStyles from "@material-ui/core/es/styles/withStyles";

import 'codemirror/lib/codemirror.css';
import {Theme} from "@material-ui/core/es";

import CodeMirror from 'react-codemirror';
import {EditorConfiguration} from "codemirror";

import './CodeMirror.css';

const styles = (theme: Theme) => createStyles({
    inputStyle: {
        minWidth: '250px',
        fontSize: theme.typography.caption.fontSize,
        border: 'solid 1px #bdbdbd',
        background: '#FFF',
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        borderRadius: '15px',
        padding: '0px 10px'
    }
});

interface QueryInputProps extends WithStyles<typeof styles> {
    startSearching: (query: string) => void;
}

const QueryInput = (props: QueryInputProps) => {
    const {startSearching, classes} = props;
    const [query, setQuery] = useState('');


    const codeMirrorRef = useRef<ReactCodeMirror.ReactCodeMirror>(null);

    useEffect(() => {
        if (codeMirrorRef.current) {

            setTimeout(() => {
                // @ts-ignore
                const textArea = codeMirrorRef.current.textareaNode;
                textArea.value = 'asfdhjksadjfkdslajfkldsa';
                console.log('here');
                console.log(textArea);
            }, 100);

            // @ts-ignore
            const tmp = codeMirrorRef.current.getCodeMirror().getTextArea();
            console.log(tmp);

            const editor: CodeMirror.Editor = codeMirrorRef.current.getCodeMirror()
            const enterHandler = (instance: any, event: any) => {
                if (event.keyCode === 13) {
                    // startSearching(query);
                    editor.setValue(query.replace('\n', ''));
                }
            };
            editor.on("keyup", enterHandler);
            return () => {
                editor.off("keyup", enterHandler);
            };
        } else {
            console.error('code mirror ref not set');
        }
    }, [])

    const options: EditorConfiguration = {}

    return <React.Fragment>
        <CodeMirror
            // className={classes.inputStyle}
            ref={codeMirrorRef}
            value={query}
            onChange={newQuery => setQuery(newQuery)}
            autoFocus={true}
            options={options}
        />
    </React.Fragment>
};

export default withStyles(styles, {withTheme: true})(QueryInput)