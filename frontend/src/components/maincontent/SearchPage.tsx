import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect} from 'react';
import SearchResultList from "../snippet/SnippetList";
import NoResultsFound from "../snippet/NoResultsFound";
import {ApplicationState} from "../../reducers/ApplicationState";

import {connect} from "react-redux";
import {startSearchingAction} from "../../actions/QueryActions";
import {SearchQuery} from "../../entities/SearchQuery";
import SearchInput from "../searchbar/SearchInput";

import * as H from 'history';
import {selectedSearchSettingsSelector} from "../../reducers/selectors";
import {SearchSettings} from "../../entities/SearchSettings";
import {openSnackBar} from "../../actions/SnackBarActions";

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
    const {snippets, startSearching, classes, selectedSettings, history, location, openSnackbar, corpusFormat} = props;
    const params = new URLSearchParams(location.search);
    const query = params.get('query') || '';

    useEffect(() => {
        if (snippets === null) {
            if (selectedSettings !== null) {
                startSearching(query, selectedSettings);
            } else {
                openSnackBar("no search settings selected, could not execute search");
            }
        }
    }, [snippets])


    return <div>
        <SearchInput className={classes.searchInput} history={history} initialQuery={query}/>
        {snippets !== null && snippets.length > 0 ?
            <SearchResultList snippet={snippets} corpusFormat={corpusFormat!}/> :
            <NoResultsFound/>}
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    snippets: state.searchResult.snippets,
    corpusFormat: state.searchResult.corpusFormat,
    selectedSettings: selectedSearchSettingsSelector(state)
});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: SearchQuery, searchSettings: SearchSettings, history?: H.History) => void,
    openSnackbar: openSnackBar
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchPage))