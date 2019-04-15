import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core/es";
import AppBarMenuButtons from "../../components/appbar/AppBarButtons";
import {connect} from "react-redux";
import {AppState} from "../../AppState";
import {logoutAction} from "../../actions/UserActions";

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
    isAdmin: boolean,
    handleLogout: () => void
}

const CorprocAppBar = (props: CorprocAppBarProps) => {
    const {classes, isAdmin, isLoggedIn, handleLogout} = props;


    return <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" color="inherit" className={classes.grow}>
                    Corproc Search
                </Typography>
                <AppBarMenuButtons isLoggedIn={isLoggedIn} isAdmin={isAdmin} handleLogout={handleLogout}/>
            </Toolbar>
        </AppBar>
    </div>
};

const mapStateToProps = (state: AppState, ownProps: CorprocAppBarProps): CorprocAppBarProps => {
    const {isLoggedIn, isAdmin} = state.user;
    return {
        ...ownProps,
        isLoggedIn,
        isAdmin
    }
}

const mapDispatchToProps = {
    handleLogout: logoutAction
};

const CorprocAppbarConnected = withStyles(styles, {withTheme: true})((connect(mapStateToProps, mapDispatchToProps))(CorprocAppBar));

export default CorprocAppbarConnected;
