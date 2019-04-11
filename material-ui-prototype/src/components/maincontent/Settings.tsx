import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';

const styles = createStyles({});


export interface SettingsProps extends WithStyles<typeof styles> {
    isLoggedIn: boolean
}

const Settings = (props: SettingsProps) => {
    return <div>
        <h1>Settings</h1>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(Settings)