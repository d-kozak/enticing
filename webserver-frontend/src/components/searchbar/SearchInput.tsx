import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect, useState} from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";

import CMInputWrapper from "./CMInputWrapper";
import {startSearchingAction} from "../../actions/QueryActions";
import * as H from "history";
import {getSelectedSearchSettings} from "../../reducers/selectors";
import {SearchSettings} from "../../entities/SearchSettings";
import {openSnackbar} from "../../reducers/SnackBarReducer";
import {User} from "../../entities/User";

const styles = createStyles({});

export type SearchInputProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    history?: H.History;
    className?: string;
}

const SearchInput = (props: SearchInputProps) => {
    const {className = '', initialQuery, user, history, selectedSettings, startSearching: parentStartSearching, openSnackbar} = props;

    const params = new URLSearchParams(location.search);

    let urlQuery = params.get('query');
    if (urlQuery) urlQuery = urlQuery.replace("||", "&&");

    const [query, setQuery] = useState<string>(urlQuery || initialQuery);

    const [cnt, setCnt] = useState(0);

    useEffect(() => {
        setQuery(initialQuery);
        setCnt(cnt + 1);
    }, [initialQuery]);

    const startSearching = () => {
        if (query.length == 0) return;
        if (selectedSettings === null) {
            openSnackbar("no search settings selected");
            return
        }
        if (query.length > 0) {
            parentStartSearching(query, user, selectedSettings, history);
        }
    };


    return <div className={className}>
        <CMInputWrapper key={cnt} query={query} setQuery={setQuery} startSearching={startSearching}/>
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    initialQuery: state.searchResult.query,
    user: state.userState.user,
    selectedSettings: getSelectedSearchSettings(state)
});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: string, user: User, searchSettings: SearchSettings, history?: H.History) => void,
    openSnackbar
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchInput))