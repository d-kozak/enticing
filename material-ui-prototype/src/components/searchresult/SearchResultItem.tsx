import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {SearchResult} from "../../entities/SearchResult";

const styles = createStyles({});


export interface SearchResultItemProps extends WithStyles<typeof styles> {
    searchResult: SearchResult
}

const SearchResultItem = (props: SearchResultItemProps) => {
    const {searchResult} = props;
    return <div>
        {searchResult.snippet}
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultItem)