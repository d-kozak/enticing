import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {SearchResult} from "../../../entities/SearchResult";

const styles = createStyles({
    titleUrl: {
        margin: "10px 0px"
    }
});


export interface DialogContentProps extends WithStyles<typeof styles> {
    searchResult: SearchResult
}

const SearchResultWholeDocument = (props: DialogContentProps) => {
    const {searchResult, classes} = props;
    return <div>
        <Typography className={classes.titleUrl} variant="headline"><a
            href={searchResult.url}>{searchResult.url}</a></Typography>
        <Typography variant="body1">{searchResult.fullText}</Typography>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultWholeDocument)