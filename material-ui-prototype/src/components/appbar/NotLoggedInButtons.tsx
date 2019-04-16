import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Button from "@material-ui/core/es/Button";
import IconButton from "@material-ui/core/es/IconButton";
import {Settings} from "@material-ui/icons";
import LinkTo from "../utils/linkTo";

const styles = createStyles({});


export interface NotLoggedInButtonsProps extends WithStyles<typeof styles> {

}

/**
 * TODO find a better name :)
 */
const NotLoggedInButtons = (props: NotLoggedInButtonsProps) => {
    const LoginLink = LinkTo("/login");
    const SettingsLink = LinkTo("/settings");
    return <React.Fragment>
        <Button component={LoginLink} color="inherit">Login</Button>
        <IconButton
            color="inherit"
            component={SettingsLink}
        >
            <Settings/>
        </IconButton>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(NotLoggedInButtons)