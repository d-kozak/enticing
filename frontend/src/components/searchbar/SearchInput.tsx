import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";

import CMInputWrapper from "./CMInputWrapper";
import {SearchQuery} from "../../entities/SearchQuery";
import {startSearchingAction} from "../../actions/QueryActions";
import * as H from "history";
import {getSelectedSearchSettings} from "../../reducers/selectors";
import {SearchSettings} from "../../entities/SearchSettings";
import {openSnackbar} from "../../reducers/SnackBarReducer";

const styles = createStyles({});

export type SearchInputProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    history?: H.History;
    className?: string;
    initialQuery?: string;
}

const SearchInput = (props: SearchInputProps) => {
    const {className = '', initialQuery = '', history, selectedSettings, startSearching: parentStartSearching, openSnackbar} = props;

    const [query, setQuery] = useState<string>(initialQuery);

    const startSearching = () => {
        if (query.length == 0) return
        if (selectedSettings === null) {
            openSnackbar("no search settings selected");
            return
        }
        if (query.length > 0) {
            parentStartSearching(query, selectedSettings, history);
        }
    };


    return <div className={className}>
        <CMInputWrapper query={query} setQuery={setQuery} startSearching={startSearching}/>
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    selectedSettings: getSelectedSearchSettings(state)
});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: SearchQuery, searchSettings: SearchSettings, history?: H.History) => void,
    openSnackbar
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchInput))