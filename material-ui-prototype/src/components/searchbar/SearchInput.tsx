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
import {MG4J_EQL} from "../../codemirror/LanguageMode";

const styles = (theme: Theme) => createStyles({});

interface QueryInputProps extends WithStyles<typeof styles> {
    startSearching: (query: string) => void;
}

const SearchInput = (props: QueryInputProps) => {
    const {startSearching} = props;
    const [query, setQuery] = useState('');

    const codeMirrorRef = useRef<ReactCodeMirror.ReactCodeMirror>(null);

    useEffect(() => {
        if (codeMirrorRef.current) {
            const codeMirror = codeMirrorRef.current;
            const editor: CodeMirror.Editor = codeMirror.getCodeMirror()

        } else {
            console.error('code mirror ref not set');
        }
    }, [])

    const options: EditorConfiguration = {
        extraKeys: {
            "Enter": () => {
                startSearching(query);
            }
        },
        mode: MG4J_EQL
    }

    return <React.Fragment>
        <CodeMirror
            ref={codeMirrorRef}
            value={query}
            onChange={newQuery => setQuery(newQuery)}
            autoFocus={true}
            options={options}
        />
    </React.Fragment>
};

export default withStyles(styles, {withTheme: true})(SearchInput)