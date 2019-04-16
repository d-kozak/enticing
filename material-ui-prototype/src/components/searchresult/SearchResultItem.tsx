import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {SearchResult} from "../../entities/SearchResult";
import Grid from "@material-ui/core/es/Grid";
import Button from "@material-ui/core/es/Button";

import {unstable_useMediaQuery as useMediaQuery} from '@material-ui/core/useMediaQuery';
import {mockAddAnnotations} from "../../mocks/annotations";


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
    openContextDialog: (searchResult: SearchResult) => void
}

const SearchResultItem = (props: SearchResultItemProps) => {
    const {searchResult, openWholeDocument, openContextDialog, classes} = props;

    const isScreenMedium = useMediaQuery('(min-width:500px)');

    const ShowContext = () => <Button
        onClick={() => openContextDialog(searchResult)}
        color="primary"
        size="small">Context</Button>

    return <div className={classes.root}>
        {mockAddAnnotations(searchResult.snippet)}
        <Grid container justify="flex-end" alignContent="center">

            {isScreenMedium ? <ShowContext/> : <Grid container>
                <ShowContext/>
            </Grid>}

            <Button onClick={() => openWholeDocument(searchResult)} color="primary" size="small">Open document</Button>

            <Button size="small">Annotations</Button>

            <Button size="small" onClick={() => document.location.href = searchResult.url}>Link</Button>

        </Grid>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultItem)