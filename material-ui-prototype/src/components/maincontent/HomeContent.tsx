import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {SearchResult} from "../../entities/SearchResult";
import SearchBar from "../searchbar/CenteredSearchBar";
import SearchResultList from "../searchresult/SearchResultList";

const styles = createStyles({});


export interface HomeProps extends WithStyles<typeof styles> {
    searchResults: Array<SearchResult> | null
    startSearching: (query: string) => void;
}

const HomeContent = (props: HomeProps) => {
    const {searchResults, startSearching} = props;
    if (searchResults) {
        return <SearchResultList searchResults={searchResults}/>;
    } else {
        return <SearchBar startSearching={startSearching}/>;
    }
};

export default withStyles(styles, {
    withTheme: true
})(HomeContent)