import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect} from 'react';
import SearchResultList from "../snippet/SnippetList";
import NoResultsFound from "../snippet/NoResultsFound";
import {AppState} from "../../reducers/RootReducer";

import {connect} from "react-redux";
import {startSearchingAction} from "../../actions/QueryActions";
import {SearchQuery} from "../../entities/SearchQuery";
import SearchInput from "../searchbar/SearchInput";

import * as H from 'history';
import {selectedSearchSettingsIndexSelector} from "../../reducers/selectors";

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
    const {snippets, startSearching, classes, selectedSettings, history, location} = props;
    const params = new URLSearchParams(location.search);
    const query = params.get('query') || '';

    useEffect(() => {
        if (snippets === null) {
            startSearching(query, selectedSettings);
        }
    }, [snippets])


    return <div>
        <SearchInput className={classes.searchInput} history={history} initialQuery={query}/>
        {snippets !== null && snippets.length > 0 ? <SearchResultList snippet={snippets}/> :
            <NoResultsFound/>}
    </div>
};

const mapStateToProps = (state: AppState) => ({
    snippets: state.searchResult.snippets,
    selectedSettings: selectedSearchSettingsIndexSelector(state)
});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: SearchQuery, selectedSettings: Number, history?: H.History) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchPage))