import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import CenteredSearchBar from "../../components/searchbar/CenteredSearchBar";
import {AppState} from "../../AppState";
import {startSearchingAction} from "../../actions/QueryActions";
import {connect} from "react-redux";

import * as H from 'history';

const styles = createStyles({});


export interface MainPageProps extends WithStyles<typeof styles> {
    startSearching: (query: string, history?: H.History) => void,
    query: string,
    setQuery: (query: string) => void,
    history: H.History
}

const MainPage = (props: MainPageProps) => {
    const {startSearching, query, setQuery, history} = props;
    return <CenteredSearchBar query={query} setQuery={setQuery}
                              startSearching={(query) => startSearching(query, history)}/>
};


const mapStateToProps = (state: AppState) => ({});
const mapDispatchToProps = {
    startSearching: startSearchingAction
}

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(MainPage))