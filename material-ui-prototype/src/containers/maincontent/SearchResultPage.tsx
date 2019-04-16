import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect, useState} from 'react';
import SearchResultList from "../../components/searchresult/SearchResultList";
import {SearchResult} from "../../entities/SearchResult";
import NoResultsFound from "../../components/searchresult/NoResultsFound";
import SearchInput from "../../components/searchbar/SearchInput";
import Typography from "@material-ui/core/es/Typography";
import {AppState} from "../../AppState";
import {startSearchingAction} from "../../actions/QueryActions";
import {connect} from "react-redux";

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
    searchResults: Array<SearchResult> | null;
    startSearching: (query: string) => void;
    lastQuery: string;
}

const SearchResultPage = (props: SearchProps) => {
    const {searchResults, startSearching, classes, lastQuery} = props;

    const [query, setQuery] = useState(lastQuery);

    useEffect(() => {
        if (searchResults === null) {
            startSearching(query);
        }
    }, [searchResults])


    return <div>
        <div className={classes.searchDiv}>
            <Typography className={classes.searchTitle} variant="h5">Query: </Typography>
            <SearchInput query={query} setQuery={setQuery} startSearching={startSearching}
                         className={classes.searchInput}/>
        </div>
        {searchResults !== null && searchResults.length > 0 ? <SearchResultList searchResults={searchResults}/> :
            <NoResultsFound/>}
    </div>
};

const mapStateToProps = (state: AppState) => ({
    searchResults: state.searchResults.searchResults,
    lastQuery: state.query.lastQuery
});
const mapDispatchToProps = {
    startSearching: startSearchingAction
}


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchResultPage))