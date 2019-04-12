import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import {SearchResult} from "../../entities/SearchResult";
import Grid from "@material-ui/core/es/Grid";
import Switch from "@material-ui/core/es/Switch";
import FormControlLabel from "@material-ui/core/es/FormControlLabel";
import Typography from "@material-ui/core/es/Typography";
import Button from "@material-ui/core/es/Button";

import {unstable_useMediaQuery as useMediaQuery} from '@material-ui/core/useMediaQuery';


const styles = createStyles({
    root: {
        margin: '10px 0px'
    },
    showContextBtnGrid: {
        flex: 1
    }
});


export interface SearchResultItemProps extends WithStyles<typeof styles> {
    searchResult: SearchResult
}

const SearchResultItem = (props: SearchResultItemProps) => {
    const {searchResult, classes} = props;

    const [showMore, setShowMore] = useState(false);

    const isSmall = !useMediaQuery('(min-width:500px)');

    const ShowContext = () => <FormControlLabel
        className={classes.showContextBtnGrid}
        control={
            <Switch
                checked={showMore}
                onChange={e => setShowMore(e.target.checked)}
                value="checkedB"
                color="primary"
            />
        }
        label="Full context"
    />

    return <div className={classes.root}>
        <Typography variant="body1">
            {showMore ? searchResult.largerText : searchResult.snippet}
        </Typography>
        <Grid container justify="flex-end" alignContent="center">

            {isSmall ? <Grid container>
                <ShowContext/>
            </Grid> : <ShowContext/>}

            <Button color="primary" size="small">Details</Button>

            <Button size="small">Annotations</Button>

            <Button size="small" onClick={() => document.location.href = searchResult.url}>Link</Button>

        </Grid>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultItem)