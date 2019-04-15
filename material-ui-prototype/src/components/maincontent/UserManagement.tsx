import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';

const styles = createStyles({});


export interface UserManagementProps extends WithStyles<typeof styles> {

}

const UserManagement = (props: UserManagementProps) => {
    return <div>
        <h1>User management</h1>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(UserManagement)