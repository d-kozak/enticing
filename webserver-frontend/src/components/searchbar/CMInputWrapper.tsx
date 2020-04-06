import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import * as React from "react";
import {useEffect, useRef, useState} from "react";
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
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import {getSelectedSearchSettings, isDebugMode} from "../../reducers/selectors";
import {SearchSettings} from "../../entities/SearchSettings";
import TextField from '@material-ui/core/TextField';


const styles = (theme: Theme) => createStyles({
    reactCodeMirror: {
        width: '100%'
    },
    mg4jOutput: {}
});

type CMInputWrapperProps = & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & WithStyles<typeof styles> & {
    query: string;
    setQuery: (query: string) => void;
    startSearching: (query: string) => void;
    className?: string;
}

interface QueryValidationRequest {
    query: string,
    settingsId: number
}

interface QueryValidationReply {
    errors: Array<{ location: { from: number, to: number }, type: string, message: string }>,
    mg4jQuery: string
}


const analyzeQuery = debounce(async (query: string, settings: SearchSettings, setMg4jQuery: (query: string) => void, doc: CodeMirror.Doc) => {
    const request: QueryValidationRequest = {
        query,
        settingsId: Number(settings.id)
    };
    try {
        const result = await axios.post<QueryValidationReply>(`${API_BASE_PATH}/compiler`, request, {withCredentials: true});

        if (result.data.mg4jQuery) {
            setMg4jQuery(result.data.mg4jQuery);
        }

        const errors = result.data.errors;

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
    const {className = '', isDebug, startSearching, settings, classes, query, setQuery} = props;

    const [mg4jQuery, setMg4jQuery] = useState('');

    const codeMirrorRef = useRef<ReactCodeMirror.ReactCodeMirror>(null);

    useEffect(() => {
        if (codeMirrorRef.current) {
            const codeMirror = codeMirrorRef.current;
            const editor: CodeMirror.Editor = codeMirror.getCodeMirror();
            const doc = editor.getDoc();
            if (query.length > 0 && settings)
                analyzeQuery(query, settings, setMg4jQuery, doc)
        } else {
            console.error('code mirror ref not set');
        }
    }, [settings]);

    const queryChanged = (query: string) => {
        setQuery(query);
        if (!settings) {
            console.error("cannot analyze query, no settings selected");
            return;
        }
        if (codeMirrorRef.current) {
            const doc = codeMirrorRef.current.getCodeMirror().getDoc();
            analyzeQuery(query, settings, setMg4jQuery, doc);
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
        {isDebug &&
        <TextField
            fullWidth
            disabled
            id="standard-disabled"
            label="Mg4j query"
            value={mg4jQuery}
            className={classes.mg4jOutput}
            margin="normal"
        />
        }
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    isDebug: isDebugMode(state),
    settings: getSelectedSearchSettings(state)
});

const mapDispatchToProps = {};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(CMInputWrapper));