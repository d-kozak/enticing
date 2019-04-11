import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';

const styles = createStyles({});


export interface DefaultSettingsPicker extends WithStyles<typeof styles> {
}

const DefaultSettingsPicker = (props: DefaultSettingsPicker) => {
    return <div>
        <h1>Default settings picker</h1>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(DefaultSettingsPicker)