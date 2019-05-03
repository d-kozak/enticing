import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect} from 'react';
import SearchResultList from "../searchresult/SearchResultList";
import NoResultsFound from "../searchresult/NoResultsFound";
import {AppState} from "../../reducers/RootReducer";

import {connect} from "react-redux";
import {startSearchingAction} from "../../actions/QueryActions";
import {SearchQuery} from "../../entities/SearchQuery";
import SearchInput from "../searchbar/SearchInput";

import * as H from 'history';

const styles = createStyles({
    searchInput: {
        margin: '20px auto',
        width: '90%'
    }
});

export type SearchPageProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    history: H.History,
    location: H.Location
}

const SearchPage = (props: SearchPageProps) => {
    const {searchResults, startSearching, classes, history, location} = props;
    const params = new URLSearchParams(location.search);
    const query = params.get('query') || '';

    useEffect(() => {
        if (searchResults === null) {
            startSearching(query);
        }
    }, [searchResults])


    return <div>
        <SearchInput className={classes.searchInput} history={history} initialQuery={query}/>
        {searchResults !== null && searchResults.length > 0 ? <SearchResultList searchResults={searchResults}/> :
            <NoResultsFound/>}
    </div>
};

const mapStateToProps = (state: AppState) => ({
    searchResults: state.searchResults.searchResults,
});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: SearchQuery, history?: H.History) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchPage))