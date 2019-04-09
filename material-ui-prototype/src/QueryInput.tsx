import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import * as React from "react";
import {useEffect, useRef, useState} from "react";
import withStyles from "@material-ui/core/es/styles/withStyles";


const styles = (theme: any) => createStyles({
    inputStyle: {
        background: '#FFF',
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
    }
});

interface QueryInputProps extends WithStyles<typeof styles> {
    startSearching: (query: string) => void;
}

const QueryInput = (props: QueryInputProps) => {
    const {startSearching, classes} = props;
    const [query, setQuery] = useState('');

    const searchRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        setTimeout(() => {
            if (searchRef.current) {
                console.log('here');
                searchRef.current.focus();
            }
        }, 100);
    }, []);

    return <div>
        <input
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