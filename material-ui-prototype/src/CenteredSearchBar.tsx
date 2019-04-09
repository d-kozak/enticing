import {WithStyles} from "@material-ui/core";
import * as React from "react";
import withStyles from "@material-ui/core/es/styles/withStyles";
import createStyles from "@material-ui/core/es/styles/createStyles";
import Typography from "@material-ui/core/Typography";
import QueryInput from "./QueryInput";
import {Theme} from "@material-ui/core/es";


const styles = (theme: Theme) => createStyles({
    mainDiv: {
        textAlign: 'center',
        position: 'fixed',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)'
    },
});

export interface CenteredSearchBar extends WithStyles<typeof styles> {
    startSearching: (query: string) => void;
}

const CenteredSearchBar = (props: CenteredSearchBar) => {
    const {classes, startSearching} = props;
    return <div className={classes.mainDiv}>
        <Typography variant="h3">
            Corproc Search
        </Typography>
        <QueryInput startSearching={startSearching}/>
    </div>
};

export default withStyles(styles, {withTheme: true})(CenteredSearchBar);