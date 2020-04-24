import React from 'react';
import {createStyles, makeStyles, Theme} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import AccountCircle from '@material-ui/icons/AccountCircle';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import {ApplicationState} from "../ApplicationState";
import {closeSnackbarAction} from "../reducers/snackbarReducer";
import {connect} from "react-redux";
import {getCurrentUserDetails} from "../reducers/userDetailsReducer";
import {logoutRequest} from "../request/userRequests";
import {useHistory} from "react-router";
import * as H from 'history';

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        appbar: {
            zIndex: theme.zIndex.drawer + 1,
        },
        menuButton: {
            marginRight: theme.spacing(2),
        },
        title: {
            flexGrow: 1,
        },
    }),
);

export type EnticingAppBarProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const EnticingAppBar = (props: EnticingAppBarProps) => {
    const {userDetails, logoutRequest} = props;
    const classes = useStyles();
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);

    const history = useHistory();

    const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    return <AppBar className={classes.appbar} position="fixed">
        <Toolbar>
            <Typography variant="h6" className={classes.title}>
                Enticing Management
            </Typography>
            {userDetails && (
                <div>
                    <IconButton
                        aria-label="account of current user"
                        aria-controls="menu-appbar"
                        aria-haspopup="true"
                        onClick={handleMenu}
                        color="inherit"
                    >
                        <AccountCircle/>
                    </IconButton>
                    <Menu
                        id="menu-appbar"
                        anchorEl={anchorEl}
                        anchorOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        keepMounted
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        open={open}
                        onClose={handleClose}
                    >
                        <MenuItem onClick={() => {
                            history.push(`/user-details/${userDetails.login}`);
                            handleClose()
                        }}>Profile</MenuItem>
                        <MenuItem onClick={() => {
                            logoutRequest(history);
                            handleClose();
                        }}>Logout</MenuItem>
                    </Menu>
                </div>
            )}
        </Toolbar>
    </AppBar>;
}

const mapStateToProps = (state: ApplicationState) => ({
    userDetails: getCurrentUserDetails(state)
});

const mapDispatchToProps = {
    closeSnackbarAction,
    logoutRequest: logoutRequest as (history: H.History) => void
};

export default (connect(mapStateToProps, mapDispatchToProps)(EnticingAppBar));