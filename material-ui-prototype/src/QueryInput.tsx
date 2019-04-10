import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import * as React from "react";
import {useEffect, useRef, useState} from "react";
import withStyles from "@material-ui/core/es/styles/withStyles";

import './QueryInput.css';
import {Theme} from "@material-ui/core/es";

import {unstable_useMediaQuery as useMediaQuery} from '@material-ui/core/useMediaQuery';

const styles = (theme: Theme) => createStyles({
    inputStyle: {
        border: 'solid 1px #bdbdbd',
        background: '#FFF',
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        fontSize: 'calc(0.5vh + 1.3em)',
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

    const searchRef = useRef<HTMLInputElement>(null);

    const isScreenVeryBig = useMediaQuery('(min-width:1000px)');
    const isScreenLittleBigger = useMediaQuery('(min-width:700px)');
    const isScreenMedium = useMediaQuery('(min-width:400px)');

    let inputSize = 15;
    if (isScreenVeryBig) {
        inputSize = 60;
    } else if (isScreenLittleBigger) {
        inputSize = 40;
    } else if (isScreenMedium) {
        inputSize = 30;
    }

    useEffect(() => {
        setTimeout(() => {
            if (searchRef.current) {
                searchRef.current.focus();
            }
        }, 100);
    }, []);


    return <div>
        <input
            size={inputSize}
            ref={searchRef}
            className={classes.inputStyle}
            value={query}
            onChange={e => setQuery(e.target.value)}
            onKeyDown={e => {
                if (e.keyCode === 13) {
                    startSearching(query);
                }
            }}
        />
    </div>
};

export default withStyles(styles, {withTheme: true})(QueryInput)