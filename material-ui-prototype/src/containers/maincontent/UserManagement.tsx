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

const styles = createStyles({});

export interface UserManagementProps extends WithStyles<typeof styles> {
    users: Array<User>;
    loadUsers: () => void;
    updateUser: (user: User) => void;
}

const UserManagement = (props: UserManagementProps) => {
    const {users, loadUsers, updateUser} = props;

    useEffect(() => {
        loadUsers();
    }, []);

    return <div>
        <Typography variant="h2">User management</Typography>
        <UserTable users={users} updateUser={updateUser}/>
    </div>
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