import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';

const styles = createStyles({});


export interface LoginProps extends WithStyles<typeof styles> {

}

const Login = (props: LoginProps) => {

    return <div>
        <h1>login</h1>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(Login)