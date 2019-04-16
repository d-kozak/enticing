import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect} from 'react';
import {AppState} from "../../AppState";
import {loadUsersAction, updateUserAction} from "../../actions/AdminActions";
import {connect} from "react-redux";
import {User} from "../../entities/User";
import Typography from "@material-ui/core/es/Typography";
import UserTable from "../../components/admin/UserTable";
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
    users: Array<User>;
    loadUsers: () => void;
    updateUser: (user: User) => void;
}

const UserManagement = (props: UserManagementProps) => {
    const {users, loadUsers, updateUser, classes} = props;

    useEffect(() => {
        loadUsers();
    }, []);

    return <Paper className={classes.root}>
        <Typography align="center" variant="h2">User management</Typography>
        <Divider className={classes.divider}/>
        <UserTable users={users} updateUser={updateUser}/>
    </Paper>
};

const mapStateToProps = (state: AppState) => ({
    users: state.admin.users
})
const mapDispatchToProps = {
    loadUsers: loadUsersAction,
    updateUser: updateUserAction
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(UserManagement))