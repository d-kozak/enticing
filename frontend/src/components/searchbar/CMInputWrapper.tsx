import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import * as React from "react";
import {useEffect, useRef} from "react";
import withStyles from "@material-ui/core/es/styles/withStyles";

import 'codemirror/lib/codemirror.css';
import {Theme} from "@material-ui/core/es";

import CodeMirror from 'react-codemirror';
import {EditorConfiguration} from "codemirror";

import './CodeMirror.css';
import {MG4J_EQL} from "../../codemirror/mg4jmode";

import debounce from "debounce";

const styles = (theme: Theme) => createStyles({
    reactCodeMirror: {
        width: '100%'
    }
});

interface CMInputWrapperProps extends WithStyles<typeof styles> {
    query: string;
    setQuery: (query: string) => void;
    startSearching: (query: string) => void;
    className?: string;
}

const CMInputWrapper = (props: CMInputWrapperProps) => {
    const {className = '', startSearching, classes, query, setQuery} = props;


    const codeMirrorRef = useRef<ReactCodeMirror.ReactCodeMirror>(null);

    useEffect(() => {
        if (codeMirrorRef.current) {
            const codeMirror = codeMirrorRef.current;
            const editor: CodeMirror.Editor = codeMirror.getCodeMirror()

            editor.getDoc()
                .markText({ch: 1, line: 0}, {ch: 4, line: 0}, {
                    className: 'syntaxError'
                })

        } else {
            console.error('code mirror ref not set');
        }
    }, [])


    const syntaxAnalysis = debounce((query: string) => {
        console.log("syntax analysis");
    }, 400)


    const queryChanged = (query: string) => {
        setQuery(query)
        syntaxAnalysis(query);
    }



    const options: EditorConfiguration = {
        extraKeys: {
            "Enter": () => {
                startSearching(query);
            }
        },
        mode: MG4J_EQL,
        scrollbarStyle: "null"
    }

    return <div className={className}>
        <CodeMirror
            className={classes.reactCodeMirror}
            ref={codeMirrorRef}
            value={query}
            onChange={newQuery => queryChanged(newQuery)}
            autoFocus={true}
            options={options}
        />
    </div>
};

export default withStyles(styles, {withTheme: true})(CMInputWrapper);