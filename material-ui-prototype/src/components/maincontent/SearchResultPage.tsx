import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import SearchResultList from "../searchresult/SearchResultList";
import {SearchResult} from "../../entities/SearchResult";
import NoResultsFound from "../searchresult/NoResultsFound";
import SearchInput from "../searchbar/SearchInput";
import Typography from "@material-ui/core/es/Typography";

const styles = createStyles({
    searchDiv: {
        width: '90%',
        margin: '10px auto',
        display: 'flex'
    },
    searchTitle: {
        marginRight: '5px'
    },
    searchInput: {
        flex: 1
    }
});

export interface SearchProps extends WithStyles<typeof styles> {
    searchResults: Array<SearchResult> | null
    query: string,
    setQuery: (query: string) => void,
    startSearching: (query: string) => void;
}

const SearchResultPage = (props: SearchProps) => {
    const {searchResults, startSearching, query, setQuery, classes} = props;
    return <div>
        <div className={classes.searchDiv}>
            <Typography className={classes.searchTitle} variant="h5">Query: </Typography>
            <SearchInput className={classes.searchInput} query={query} setQuery={setQuery}
                         startSearching={startSearching}/>
        </div>
        {searchResults !== null && searchResults.length > 0 ? <SearchResultList searchResults={searchResults}/> :
            <NoResultsFound/>}
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultPage)