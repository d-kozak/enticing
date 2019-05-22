import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import IconButton from "@material-ui/core/IconButton";
import {AccountCircle} from "@material-ui/icons";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import LinkTo from "../utils/linkTo";
import Typography from "@material-ui/core/Typography";
import {User} from "../../entities/User";

const styles = createStyles({
    userLogin: {
        margin: '0px 10px'
    }
});


export interface CommonAppBarButtonsProps extends WithStyles<typeof styles> {
    handleLogout: () => void,
    user: User
}

const CommonAppBarButtons = (props: CommonAppBarButtonsProps) => {
    const {user, handleLogout: parentHandleLogout, classes} = props;
    const isAdmin = user.roles.indexOf("ADMIN") != -1;
    const [anchorElement, setAnchorElement] = useState<HTMLElement | null>(null);
    const handleMenuOpen = (event: any) => {
        setAnchorElement(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorElement(null);
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
            <Typography className={classes.userLogin} variant="body1" color="inherit">{user.login}</Typography>
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
            anchorEl={anchorElement}
            open={Boolean(anchorElement)}
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