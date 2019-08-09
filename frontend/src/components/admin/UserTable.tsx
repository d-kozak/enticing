import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useEffect} from 'react';
import {User} from "../../entities/User";
import Table from "@material-ui/core/es/Table";
import TableHead from "@material-ui/core/es/TableHead";
import TableRow from "@material-ui/core/es/TableRow";
import TableCell from "@material-ui/core/es/TableCell";
import TableBody from "@material-ui/core/es/TableBody";
import Checkbox from "@material-ui/core/es/Checkbox";
import Button from "@material-ui/core/es/Button";
import DeleteUserDialog from "./DeleteUserDialog";
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";
import {openDeleteUserDialog} from "../../reducers/dialog/DeleteUserDialogReducer";
import ChangePasswordDialog from "../changepassworddialog/ChangePasswordDialog";
import {
    adminChangePasswordRequest,
    adminLoadUserRequest,
    adminUpdateUserRequest,
    getUsers
} from "../../reducers/AdminReducer";
import {openChangePasswordDialog} from "../../reducers/dialog/ChangePasswordDialogReducer";


const styles = createStyles({
    root: {
        width: '90%',
        margin: '20px auto'
    },
    '@media (min-width:1000px)': {
        root: {
            width: '70%',
        }
    }
});

export type UserTableProps = WithStyles<typeof styles> & typeof mapDispatchToProps & ReturnType<typeof mapStateToProps>

const UserTable = (props: UserTableProps) => {
    const {users, updateUser, deleteUser, loadUsers, classes, changePassword, openChangePasswordDialog} = props;

    useEffect(() => {
        loadUsers();
    }, []);

    const toggleIsAdmin = (user: User) => (event: React.ChangeEvent<HTMLInputElement>) => {
        updateUser({
            ...user,
            roles: event.target.checked ? ["ADMIN"] : []
        });
    };

    const toggleIsActive = (user: User) => (event: React.ChangeEvent<HTMLInputElement>) => {
        updateUser({
            ...user,
            active: event.target.checked
        });
    };

    return <React.Fragment>
        <Table>
            <TableHead>
                <TableRow>
                    <TableCell>Login</TableCell>
                    <TableCell align="right">Is active</TableCell>
                    <TableCell align="right">Is admin</TableCell>
                    <TableCell align="right">Password</TableCell>
                    <TableCell align="right">Delete user</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {users.map((user, index) => <TableRow key={index}>
                    <TableCell>{user.login}</TableCell>
                    <TableCell align="right">
                        <Checkbox
                            checked={user.active}
                            onChange={toggleIsActive(user)}
                            value="checkedA"
                            color="primary"
                        />
                    </TableCell>
                    <TableCell align="right">
                        <Checkbox
                            checked={user.roles.indexOf("ADMIN") != -1}
                            onChange={toggleIsAdmin(user)}
                            value="checkedA"
                            color="secondary"
                        />
                    </TableCell>
                    <TableCell align="right">
                        <Button color="primary" onClick={() => openChangePasswordDialog(user)}>Change</Button>
                    </TableCell>
                    <TableCell align="right">
                        <Button onClick={() => deleteUser(user)} color="secondary" variant="contained">Delete</Button>
                    </TableCell>
                </TableRow>)}
            </TableBody>
        </Table>
        <DeleteUserDialog/>
        <ChangePasswordDialog changePassword={(user, _, newPassword) => changePassword(user, newPassword)}
                              askForOldPassword={false}/>
    </React.Fragment>
};


const mapStateToProps = (state: ApplicationState) => ({
    users: getUsers(state)
});

const mapDispatchToProps = {
    loadUsers: adminLoadUserRequest as () => void,
    updateUser: adminUpdateUserRequest as (user: User) => void,
    deleteUser: openDeleteUserDialog,
    openChangePasswordDialog: openChangePasswordDialog,
    changePassword: adminChangePasswordRequest as (user: User, newPassword: string) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(UserTable))