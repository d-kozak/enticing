import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect} from 'react';
import SearchResultList from "../searchresult/SearchResultList";
import {SearchResult} from "../../entities/SearchResult";
import NoResultsFound from "../../components/searchresult/NoResultsFound";
import {AppState} from "../../AppState";

import {connect} from "react-redux";
import {startSearchingAction} from "../../actions/QueryActions";
import {SearchQuery} from "../../entities/SearchQuery";
import SearchInput from "../searchbar/SearchInput";

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
    startSearching: (query: SearchQuery) => void;
    lastQuery: SearchQuery;
}

const SearchResultPage = (props: SearchProps) => {
    const {searchResults, startSearching, classes, lastQuery} = props;

    useEffect(() => {
        if (searchResults === null) {
            startSearching(lastQuery);
        }
    }, [searchResults])


    return <div>
        <div className={classes.searchDiv}>
            <SearchInput/>
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
    startSearching: startSearchingAction,
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchResultPage))