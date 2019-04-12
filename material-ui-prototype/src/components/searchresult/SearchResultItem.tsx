import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import {SearchResult} from "../../entities/SearchResult";
import {Link as LinkIcon} from "@material-ui/icons";
import IconButton from "@material-ui/core/IconButton";
import Grid from "@material-ui/core/es/Grid";
import Switch from "@material-ui/core/es/Switch";
import FormControlLabel from "@material-ui/core/es/FormControlLabel";
import Typography from "@material-ui/core/es/Typography";

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

    const [showMore, setShowMore] = useState(false);

    return <div className={classes.root}>
        <Typography variant="body1">
            {showMore ? searchResult.largerText : searchResult.snippet}
        </Typography>
        <Grid container justify="flex-end">
            <Grid item>
                <FormControlLabel
                    control={
                        <Switch
                            checked={showMore}
                            onChange={e => setShowMore(e.target.checked)}
                            value="checkedB"
                            color="primary"
                        />
                    }
                    label="With context"
                />
                <IconButton title='Source url' onClick={() => window.location.href = searchResult.url}>
                    <LinkIcon/>
                </IconButton>
            </Grid>
        </Grid>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultItem)