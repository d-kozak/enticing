import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import EditableSettings from "../settings/EditableSettings";

const styles = createStyles({});


export interface SettingsProps extends WithStyles<typeof styles> {
    isLoggedIn: boolean
}

const Settings = (props: SettingsProps) => {
    const {isLoggedIn} = props;
    if (isLoggedIn) {
        return <EditableSettings/>
    } else {
        //return <DefaultSettingsPicker/>
        return <EditableSettings/>
    }
};

export default withStyles(styles, {
    withTheme: true
})(Settings)