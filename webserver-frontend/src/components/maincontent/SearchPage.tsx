import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect} from 'react';
import SnippetList from "../snippet/SnippetList";
import NoResultsFound from "../snippet/NoResultsFound";
import {ApplicationState} from "../../ApplicationState";

import {connect} from "react-redux";
import {startSearchingAction} from "../../actions/QueryActions";
import SearchInput from "../searchbar/SearchInput";

import * as H from 'history';
import {SearchSettings} from "../../entities/SearchSettings";
import {openSnackbar} from "../../reducers/SnackBarReducer";
import {User} from "../../entities/User";
import {getSelectedSearchSettings} from "../../reducers/selectors";

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
    const {classes, hasSnippets, history, location, loadingMore, searchStarted, user, selectedSettings, startSearching} = props;

    const params = new URLSearchParams(location.search);
    const query = params.get("query");

    useEffect(() => {
        if (query && !searchStarted && selectedSettings != null) {
            startSearching(query, user, selectedSettings);
        }
    }, [selectedSettings]);


    return <div>
        <SearchInput className={classes.searchInput} history={history}/>
        {hasSnippets ? <SnippetList history={history}/> : <NoResultsFound loadingMore={loadingMore}/>}
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    searchStarted: state.progressBar.isVisible,
    loadingMore: state.searchResult.moreResultsAvailable,
    hasSnippets: state.searchResult.snippetIds.length > 0,
    user: state.userState.user,
    selectedSettings: getSelectedSearchSettings(state)
});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: string, user: User, searchSettings: SearchSettings, history?: H.History) => void,
    openSnackbar
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchPage))