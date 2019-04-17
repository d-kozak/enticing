import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import UserTable from "../admin/UserTable";
import Paper from "@material-ui/core/es/Paper";
import Divider from "@material-ui/core/es/Divider";

const styles = createStyles({
    root: {
        width: '90%',
        margin: '20px auto',
        paddingTop: '15px'
    },
    divider: {
        margin: '15px 0px'
    },
    '@media (min-width:1000px)': {
        root: {
            width: '80%',
        }
    }
});

export interface UserManagementProps extends WithStyles<typeof styles> {
}

const UserManagement = (props: UserManagementProps) => {
    const {classes} = props;
    return <Paper className={classes.root}>
        <Typography align="center" variant="h2">User management</Typography>
        <Divider className={classes.divider}/>
        <UserTable/>
    </Paper>
};



export default withStyles(styles, {
    withTheme: true
})(UserManagement)