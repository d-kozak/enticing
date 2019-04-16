import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect} from 'react';
import {AppState} from "../../AppState";
import {loadUsersAction} from "../../actions/AdminActions";
import {connect} from "react-redux";
import {User} from "../../entities/User";

const styles = createStyles({});


export interface UserManagementProps extends WithStyles<typeof styles> {
    users: Array<User>;
    loadUsers: () => void;
}

const UserManagement = (props: UserManagementProps) => {
    const {users, loadUsers} = props;

    useEffect(() => {
        loadUsers();
    }, []);

    return <div>
        <h1>User management</h1>
        {JSON.stringify(users, null, 4)}
    </div>
};

const mapStateToProps = (state: AppState) => ({
    users: state.admin.users
})
const mapDispatchToProps = {
    loadUsers: loadUsersAction
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(UserManagement))