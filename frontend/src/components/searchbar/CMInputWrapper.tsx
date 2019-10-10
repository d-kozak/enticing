import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import * as React from "react";
import {useEffect, useRef} from "react";
import withStyles from "@material-ui/core/es/styles/withStyles";

import 'codemirror/lib/codemirror.css';
import {Theme} from "@material-ui/core/es";

import axios from "axios";

import CodeMirror from 'react-codemirror';
import {EditorConfiguration} from "codemirror";

import './CodeMirror.css';
import {EQL} from "../../codemirror/eqlmode";

import debounce from "debounce";
import {API_BASE_PATH} from "../../globals";
import {consoleDump} from "../utils/dump";

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


const analyzeQuery = debounce(async (query: string, doc: CodeMirror.Doc) => {
    try {
        const result = await axios.get<Array<{ location: { from: number, to: number }, type: string, message: string }>>(`${API_BASE_PATH}/compiler?query=${encodeURI(query)}`);
        const errors = result.data;

        for (let mark of doc.getAllMarks()) {
            mark.clear();
        }

        for (let error of errors) {
            const from = {
                line: 0,
                ch: error.location.from
            };
            const to = {
                line: 0,
                ch: error.location.to + 1
            };
            doc.markText(from, to, {
                className: error.type,
                title: error.message
            });
        }
    } catch (e) {
        console.error("could not parse error messages");
        consoleDump(e);
    }
}, 400);

const CMInputWrapper = (props: CMInputWrapperProps) => {
    const {className = '', startSearching, classes, query, setQuery} = props;

    const codeMirrorRef = useRef<ReactCodeMirror.ReactCodeMirror>(null);

    useEffect(() => {
        if (codeMirrorRef.current) {
            const codeMirror = codeMirrorRef.current;
            const editor: CodeMirror.Editor = codeMirror.getCodeMirror()
        } else {
            console.error('code mirror ref not set');
        }
    }, []);


    const queryChanged = (query: string) => {
        setQuery(query);
        if (codeMirrorRef.current) {
            const doc = codeMirrorRef.current.getCodeMirror().getDoc();
            analyzeQuery(query, doc);
        }
    };

    const options: EditorConfiguration = {
        extraKeys: {
            "Enter": () => {
                startSearching(query);
            }
        },
        mode: EQL,
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