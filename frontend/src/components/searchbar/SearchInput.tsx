import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import {connect} from "react-redux";
import {AppState} from "../../reducers/RootReducer";

import CMInputWrapper from "./CMInputWrapper";
import {SearchQuery} from "../../entities/SearchQuery";
import {startSearchingAction} from "../../actions/QueryActions";
import * as H from "history";

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
    const {className = '', initialQuery = '', history, startSearching: parentStartSearching} = props;

    const [query, setQuery] = useState<string>(initialQuery);

    const startSearching = () => {
        if (query.length > 0) {
            parentStartSearching(query, history);
        }
    };


    return <div className={className}>
        <CMInputWrapper query={query} setQuery={setQuery} startSearching={startSearching}/>
    </div>
};

const mapStateToProps = (state: AppState) => ({});

const mapDispatchToProps = {
    startSearching: startSearchingAction as (query: SearchQuery, history?: H.History) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchInput))