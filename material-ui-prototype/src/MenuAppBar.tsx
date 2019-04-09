import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import AccountCircle from '@material-ui/icons/AccountCircle';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core/es";
import {Button} from "@material-ui/core";

const styles = createStyles({
    root: {
        flexGrow: 1,
    },
    grow: {
        flexGrow: 1,
    },
    menuButton: {
        marginLeft: -12,
        marginRight: 20,
    },
});

export interface CorprocAppBarProps extends WithStyles<typeof styles> {
    showTitle: boolean,
    isLoggedIn: boolean,
    setLoggedIn: (isLoggedIn: boolean) => void
}


class CorprocAppBar extends React.Component<CorprocAppBarProps> {
    state = {
        anchorEl: null,
    };

    handleMenu = (event: any) => {
        this.setState({anchorEl: event.currentTarget});
    };

    handleClose = () => {
        this.setState({anchorEl: null});
    };

    handleLogin = () => {
        this.props.setLoggedIn(true);
        this.handleClose();
    }

    handleLogout = () => {
        this.props.setLoggedIn(false);
        this.handleClose();
    }


    render() {
        const {classes, isLoggedIn} = this.props;
        const {anchorEl} = this.state;
        const open = Boolean(anchorEl);

        return (
            <div className={classes.root}>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6" color="inherit" className={classes.grow}>
                            Corproc Search
                        </Typography>

                        {!isLoggedIn && <Button onClick={this.handleLogin} color="inherit">Login</Button>}
                        {isLoggedIn && <div>
                            <IconButton
                                aria-owns={open ? 'menu-appbar' : undefined}
                                aria-haspopup="true"
                                onClick={this.handleMenu}
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
                                transformOrigin={{
                                    vertical: 'top',
                                    horizontal: 'right',
                                }}
                                open={open}
                                onClose={this.handleClose}
                            >
                                <MenuItem onClick={this.handleLogout}>Logout</MenuItem>
                                <MenuItem onClick={this.handleClose}>Settings</MenuItem>
                            </Menu>
                        </div>}
                    </Toolbar>
                </AppBar>
            </div>
        );
    }
}

export default withStyles(styles)(CorprocAppBar);
