import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import IconButton from "@material-ui/core/IconButton";
import {AccountCircle} from "@material-ui/icons";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import LinkTo from "../utils/linkTo";

const styles = createStyles({});


export interface CommonAppBarButtonsProps extends WithStyles<typeof styles> {
    handleLogout: () => void,
    isAdmin: boolean
}

const CommonAppBarButtons = (props: CommonAppBarButtonsProps) => {
    const {isAdmin, handleLogout: parentHandleLogout} = props;
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

    const SettingsLink = LinkTo("/settings");

    return <React.Fragment>
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
            <MenuItem onClick={handleMenuClose} component={SettingsLink}>Settings</MenuItem>
            {isAdmin && <MenuItem onClick={handleMenuClose} component={LinkTo("/users")}>User management</MenuItem>}
            {isAdmin && <MenuItem onClick={handleMenuClose} component={LinkTo("/search-settings")}>Search settings
                management</MenuItem>}
            <MenuItem onClick={handleLogout}>Logout</MenuItem>
        </Menu>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(CommonAppBarButtons)