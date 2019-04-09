import React, {useState} from 'react';
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
    isLoggedIn: boolean,
    setLoggedIn: (isLoggedIn: boolean) => void
}

const CorprocAppBar = (props: CorprocAppBarProps) => {
    const {classes, isLoggedIn, setLoggedIn} = props;

    const [anchorEl, setAnchorEl] = useState(null);

    const isOpen = Boolean(anchorEl);

    const handleMenu = (event: any) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogin = () => {
        setLoggedIn(true);
        handleClose();
    }

    const handleLogout = () => {
        setLoggedIn(false);
        handleClose();
    }


    return <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" color="inherit" className={classes.grow}>
                    Corproc Search
                </Typography>
                {!isLoggedIn && <Button onClick={handleLogin} color="inherit">Login</Button>}
                {isLoggedIn && <div>
                    <IconButton
                        aria-owns={open ? 'menu-appbar' : undefined}
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
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        open={isOpen}
                        onClose={handleClose}
                    >
                        <MenuItem onClick={handleLogout}>Logout</MenuItem>
                        <MenuItem onClick={handleClose}>Settings</MenuItem>
                    </Menu>
                </div>}
            </Toolbar>
        </AppBar>
    </div>
};

export default withStyles(styles)(CorprocAppBar);
