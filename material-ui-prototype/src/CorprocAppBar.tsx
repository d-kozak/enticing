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
import {Link, Route} from "react-router-dom";

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
    handleLogout: () => void
}

const CorprocAppBar = (props: CorprocAppBarProps) => {
    const {classes, isLoggedIn, handleLogout: parentHandleLogout} = props;

    const [isMenuOpen, setMenuOpen] = useState(false);

    const handleMenuOpen = () => {
        setMenuOpen(true);
    };

    const handleMenuClose = () => {
        setMenuOpen(false);
    };

    const handleLogout = () => {
        parentHandleLogout();
        handleMenuClose();
    }

    const LoginLink = (props: any) => <Link to="/login" {...props} />

    const MainPageButtons = () => <div>
        {!isLoggedIn && <Button component={LoginLink} color="inherit">Login</Button>}
        {isLoggedIn && <div>
            <IconButton
                aria-owns={open ? 'menu-appbar' : undefined}
                aria-haspopup="true"
                onClick={handleMenuOpen}
                color="inherit"
            >
                <AccountCircle/>
            </IconButton>
            <Menu
                id="menu-appbar"
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                open={isMenuOpen}
                onClose={handleMenuClose}
            >
                <MenuItem onClick={handleLogout}>Logout</MenuItem>
                <MenuItem onClick={handleMenuClose}>Settings</MenuItem>
            </Menu>
        </div>}
    </div>

    return <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" color="inherit" className={classes.grow}>
                    Corproc Search
                </Typography>
                <Route path="/" exact component={MainPageButtons}/>
            </Toolbar>
        </AppBar>
    </div>
};

export default withStyles(styles)(CorprocAppBar);
