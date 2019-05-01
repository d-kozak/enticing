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
import {CONSTRAINTS} from "../../codemirror/constraintsMode";

const styles = (theme: Theme) => createStyles({
    reactCodeMirror: {
        width: '100%'
    }
});

interface ConstraintsInputProps extends WithStyles<typeof styles> {
    constraints: string;
    setConstraints: (newConstraints: string) => void;
    startSearching: (constraints: string) => void;
    className?: string;
}

const ConstraintsInput = (props: ConstraintsInputProps) => {
    const {className = '', startSearching, classes, setConstraints, constraints} = props;

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
                startSearching(constraints);
            }
        },
        mode: CONSTRAINTS,
        scrollbarStyle: "null"
    }

    return <div className={className}>
        <CodeMirror
            className={classes.reactCodeMirror}
            ref={codeMirrorRef}
            value={constraints}
            onChange={constraints => setConstraints(constraints)}
            autoFocus={true}
            options={options}
        />
    </div>
};

export default withStyles(styles, {withTheme: true})(ConstraintsInput);