import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Paper from "@material-ui/core/es/Paper";
import Typography from "@material-ui/core/es/Typography";

const styles = createStyles({
    root: {
        minWidth: '250px',
        position: 'fixed',
        top: '40%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        textAlign: 'center'
    },
    '@media (min-width:700px)': {
        root: {
            padding: '20px'
        }
    }
});


export interface NoResultsFoundProps extends WithStyles<typeof styles> {

}

const NoResultsFound = (props: NoResultsFoundProps) => {
    const {classes} = props;
    return <Paper className={classes.root}>
        <Typography variant="h4">No results were found</Typography>
    </Paper>
};

export default withStyles(styles, {
    withTheme: true
})(NoResultsFound)