import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import {SearchResult} from "../../entities/SearchResult";
import Grid from "@material-ui/core/es/Grid";
import Switch from "@material-ui/core/es/Switch";
import FormControlLabel from "@material-ui/core/es/FormControlLabel";
import Button from "@material-ui/core/es/Button";

import {unstable_useMediaQuery as useMediaQuery} from '@material-ui/core/useMediaQuery';


const styles = createStyles({
    root: {
        margin: '10px 0px'
    },
    showContextBtnGrid: {
        flex: 1
    }
});


export interface SearchResultItemProps extends WithStyles<typeof styles> {
    searchResult: SearchResult,
    openWholeDocument: (searchResult: SearchResult) => void
}

const SearchResultItem = (props: SearchResultItemProps) => {
    const {searchResult, openWholeDocument, classes} = props;

    const [showContext, setShowContext] = useState(false);

    const isScreenMedium = useMediaQuery('(min-width:500px)');

    const ShowContext = () => <FormControlLabel
        className={classes.showContextBtnGrid}
        control={
            <Switch
                checked={showContext}
                onChange={e => setShowContext(e.target.checked)}
                value="checkedB"
                color="primary"
            />
        }
        label="Full context"
    />

    return <div className={classes.root}>
        {showContext ? searchResult.largerText : searchResult.snippet}
        <Grid container justify="flex-end" alignContent="center">

            {isScreenMedium ? <ShowContext/> : <Grid container>
                <ShowContext/>
            </Grid>}

            <Button onClick={() => openWholeDocument(searchResult)} color="primary" size="small">Whole document</Button>

            <Button size="small">Annotations</Button>

            <Button size="small" onClick={() => document.location.href = searchResult.url}>Link</Button>

        </Grid>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultItem)