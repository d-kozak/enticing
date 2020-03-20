import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
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
    const {classes, hasSnippets, history, location} = props;

    // todo start search when this page is loaded somehow from an action

    return <div>
        <SearchInput className={classes.searchInput} history={history}/>
        {hasSnippets ? <SnippetList/> : <NoResultsFound/>}
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    hasSnippets: state.searchResult.snippetIds.length > 0
});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: string, user: User, searchSettings: SearchSettings, history?: H.History) => void,
    openSnackbar
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchPage))