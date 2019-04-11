import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {SearchResult} from "../../entities/SearchResult";
import SearchResultItem from "./SearchResultItem";
import Typography from "@material-ui/core/es/Typography";
import Paper from "@material-ui/core/es/Paper";

const styles = createStyles({
    root: {
        minWidth: '275px',
        width: '90%',
        margin: '20px auto'
    },
    '@media (min-width:600)': {
        root: {
            width: '80%'
        }
    }
});


export interface SearchResultListProps extends WithStyles<typeof styles> {
    searchResults: Array<SearchResult>
}

const SearchResultList = (props: SearchResultListProps) => {
    const {searchResults, classes} = props;
    return <Paper className={classes.root}>
        {searchResults.map(
            (searchResult, index) => <SearchResultItem key={index} searchResult={searchResult}/>)
        }
        <Typography
            variant="body1">{searchResults.length > 0 ? `Total number of snippets is ${searchResults.length}` : 'No snippets found'}</Typography>
    </Paper>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultList)