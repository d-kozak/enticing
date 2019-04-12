import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {SearchResult} from "../../entities/SearchResult";
import {Link as LinkIcon} from "@material-ui/icons";
import IconButton from "@material-ui/core/IconButton";
import Grid from "@material-ui/core/es/Grid";

const styles = createStyles({
    root: {
        margin: '10px 0px'
    }
});


export interface SearchResultItemProps extends WithStyles<typeof styles> {
    searchResult: SearchResult
}

const SearchResultItem = (props: SearchResultItemProps) => {
    const {searchResult, classes} = props;
    return <div className={classes.root}>
        {searchResult.snippet}
        <Grid container justify="flex-end">
            <Grid item>
                <IconButton onClick={() => window.location.href = searchResult.url}>
                    <LinkIcon/>
                </IconButton>
            </Grid>
        </Grid>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultItem)