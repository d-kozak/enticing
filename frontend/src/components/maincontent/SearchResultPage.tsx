import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect} from 'react';
import SearchResultList from "../searchresult/SearchResultList";
import * as H from "history";
import NoResultsFound from "../searchresult/NoResultsFound";
import {AppState} from "../../reducers/RootReducer";

import {connect} from "react-redux";
import {startSearchingAction} from "../../actions/QueryActions";
import {SearchQuery} from "../../entities/SearchQuery";
import SearchInput from "../searchbar/SearchInput";

const styles = createStyles({
    searchInput: {
        margin: '20px auto',
        width: '90%'
    }
});

export type SearchProps = WithStyles<typeof styles> & typeof mapDispatchToProps & ReturnType<typeof mapStateToProps>

const SearchResultPage = (props: SearchProps) => {
    const {searchResults, startSearching, lastQuery, classes} = props;

    useEffect(() => {
        if (searchResults === null) {
            startSearching(lastQuery);
        }
    }, [searchResults])


    return <div>
        <SearchInput className={classes.searchInput}/>
        {searchResults !== null && searchResults.length > 0 ? <SearchResultList searchResults={searchResults}/> :
            <NoResultsFound/>}
    </div>
};

const mapStateToProps = (state: AppState) => ({
    searchResults: state.searchResults.searchResults,
    lastQuery: state.query.lastQuery
});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: SearchQuery, history?: H.History) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchResultPage))