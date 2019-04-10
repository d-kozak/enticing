import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {SearchResult} from "../../entities/SearchResult";
import SearchResultItem from "./SearchResultItem";
import Typography from "@material-ui/core/es/Typography";

const styles = createStyles({});


export interface SearchResultListProps extends WithStyles<typeof styles> {
    searchResults: Array<SearchResult>
}

const SearchResultList = (props: SearchResultListProps) => {
    const {searchResults} = props;
    return <div>
        {searchResults.map(
            (searchResult, index) => <SearchResultItem key={index} searchResult={searchResult}/>)
        }
        <Typography variant="body1">Total number of results is {searchResults.length}</Typography>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultList)