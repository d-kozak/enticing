import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core/es";
import AppBarMenuButtons from "./AppBarMenuButtons";

const styles = createStyles({
    root: {
        flexGrow: 1,
    },
    grow: {
        flexGrow: 1,
    }
});

export interface CorprocAppBarProps extends WithStyles<typeof styles> {
    isLoggedIn: boolean,
    handleLogout: () => void
}

const CorprocAppBar = (props: CorprocAppBarProps) => {
    const {classes, isLoggedIn, handleLogout} = props;


    return <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" color="inherit" className={classes.grow}>
                    Corproc Search
                </Typography>
                <AppBarMenuButtons isLoggedIn={isLoggedIn} handleLogout={handleLogout}/>
            </Toolbar>
        </AppBar>
    </div>
};

export default withStyles(styles, {withTheme: true})(CorprocAppBar);
