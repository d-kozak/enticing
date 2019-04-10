import withStyles, {WithStyles} from "@material-ui/core/es/styles/withStyles";
import createStyles from "@material-ui/core/es/styles/createStyles";
import {Route} from "react-router";
import React, {useState} from "react";
import {Button} from "@material-ui/core";
import IconButton from "@material-ui/core/IconButton";
import {AccountCircle, Settings} from "@material-ui/icons";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import {Link} from "react-router-dom";

const styles = createStyles({});

export interface AppBarButtonsProps extends WithStyles<typeof styles> {
    handleLogout: () => void,
    isLoggedIn: boolean
}

const AppBarMenuButtons = (props: AppBarButtonsProps) => {
    const {isLoggedIn, handleLogout: parentHandleLogout} = props;

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

    const MainPageButtons = () => <React.Fragment>
        {!isLoggedIn && <React.Fragment>
            <Button component={LoginLink} color="inherit">Login</Button>
            <IconButton
                color="inherit"
            >
                <Settings/>
            </IconButton>
        </React.Fragment>}
        {isLoggedIn && <React.Fragment>
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
                <MenuItem onClick={handleMenuClose}>Settings</MenuItem>
                <MenuItem onClick={handleLogout}>Logout</MenuItem>
            </Menu>
        </React.Fragment>}
    </React.Fragment>

    return <Route path="/" exact component={MainPageButtons}/>
};


export default withStyles(styles, {withTheme: true})(AppBarMenuButtons)