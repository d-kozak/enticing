import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';

const styles = createStyles({});


export interface LoginProps extends WithStyles<typeof styles> {

}

const UnknownRoute = (props: LoginProps) => {
    return <React.Fragment>
        <h1>Unknown</h1>
    </React.Fragment>
};

export default withStyles(styles, {
    withTheme: true
})(UnknownRoute)