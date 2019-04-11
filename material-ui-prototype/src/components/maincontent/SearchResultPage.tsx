import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import SearchResultList from "../searchresult/SearchResultList";
import {SearchResult} from "../../entities/SearchResult";
import {Redirect} from "react-router";

const styles = createStyles({});

export interface SearchProps extends WithStyles<typeof styles> {
    searchResults: Array<SearchResult> | null
    showProgressBar: () => void
}

const SearchResultPage = (props: SearchProps) => {
    const {searchResults, showProgressBar} = props;
    if (searchResults === null) {
        return <Redirect to="/"/>
    }
    return <SearchResultList searchResults={searchResults}/>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultPage)