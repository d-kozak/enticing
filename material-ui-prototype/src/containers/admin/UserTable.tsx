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
import {AppState} from "../../AppState";
import {loadUsersAction, updateUserAction} from "../../actions/AdminActions";
import {deleteUserDialogOpenAction} from "../../actions/dialog/DeleteUserDialogActions";


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


export interface UserTableProps extends WithStyles<typeof styles> {
    users: Array<User>;
    loadUsers: () => void;
    updateUser: (user: User) => void;
    deleteUser: (user: User) => void;
}

const UserTable = (props: UserTableProps) => {
    const {users, updateUser, deleteUser, loadUsers, classes} = props;

    useEffect(() => {
        loadUsers();
    }, []);

    const toggleIsAdmin = (user: User) => (event: React.ChangeEvent<HTMLInputElement>) => {
        updateUser({
            ...user,
            isAdmin: event.target.checked
        });
    };

    const toggleIsActive = (user: User) => (event: React.ChangeEvent<HTMLInputElement>) => {
        updateUser({
            ...user,
            isActive: event.target.checked
        });
    };

    const handleChangePassword = (user: User) => {
        console.log('change');
        console.log(user);
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
                            checked={user.isActive}
                            onChange={toggleIsActive(user)}
                            value="checkedA"
                            color="primary"
                        />
                    </TableCell>
                    <TableCell align="right">
                        <Checkbox
                            checked={user.isAdmin}
                            onChange={toggleIsAdmin(user)}
                            value="checkedA"
                            color="secondary"
                        />
                    </TableCell>
                    <TableCell align="right">
                        <Button color="primary" onClick={() => handleChangePassword(user)}>Change</Button>
                    </TableCell>
                    <TableCell align="right">
                        <Button onClick={() => deleteUser(user)} color="secondary" variant="contained">Delete</Button>
                    </TableCell>
                </TableRow>)}
            </TableBody>
        </Table>
        <DeleteUserDialog/>
    </React.Fragment>
};


const mapStateToProps = (state: AppState) => ({
    users: state.admin.users
})
const mapDispatchToProps = {
    loadUsers: loadUsersAction,
    updateUser: updateUserAction,
    deleteUser: deleteUserDialogOpenAction
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(UserTable))