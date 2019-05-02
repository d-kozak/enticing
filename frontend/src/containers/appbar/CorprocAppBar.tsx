import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core/es";
import AppBarMenuButtons from "../../components/appbar/AppBarButtonsRouter";
import {connect} from "react-redux";
import {AppState} from "../../AppState";
import {logoutRequestAction} from "../../actions/UserActions";
import {Link} from "react-router-dom";


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

    const linkRef = (node: HTMLAnchorElement | null) => {
        if (node) {
            node.style.textDecoration = 'none';
            node.style.color = 'white';
        }
    }

    return <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <div className={classes.grow}>
                    <Link to="/" innerRef={linkRef}>
                        <Typography variant="h6" color="inherit">
                            Enticing
                        </Typography>
                    </Link>
                </div>
                <AppBarMenuButtons isLoggedIn={isLoggedIn} isAdmin={isAdmin} handleLogout={handleLogout}/>
            </Toolbar>
        </AppBar>
    </div>
};

const mapStateToProps = (state: AppState) => {
    const {isLoggedIn, isAdmin} = state.user;
    return {
        isLoggedIn,
        isAdmin
    };
}

const mapDispatchToProps = {
    handleLogout: logoutRequestAction
};

export default withStyles(styles, {withTheme: true})((connect(mapStateToProps, mapDispatchToProps))(CorprocAppBar));
