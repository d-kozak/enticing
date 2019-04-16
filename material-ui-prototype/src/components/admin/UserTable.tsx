import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {User} from "../../entities/User";
import Table from "@material-ui/core/es/Table";
import TableHead from "@material-ui/core/es/TableHead";
import TableRow from "@material-ui/core/es/TableRow";
import TableCell from "@material-ui/core/es/TableCell";
import TableBody from "@material-ui/core/es/TableBody";
import Checkbox from "@material-ui/core/es/Checkbox";


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
    updateUser: (user: User) => void;
}

const UserTable = (props: UserTableProps) => {
    const {users, updateUser, classes} = props;

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

    return <Table>
        <TableHead>
            <TableRow>
                <TableCell>Login</TableCell>
                <TableCell align="right">Is active</TableCell>
                <TableCell align="right">Is admin</TableCell>
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
                    />
                </TableCell>
                <TableCell align="right">
                    <Checkbox
                        checked={user.isAdmin}
                        onChange={toggleIsAdmin(user)}
                        value="checkedA"
                    />
                </TableCell>
            </TableRow>)}
        </TableBody>
    </Table>
};

export default withStyles(styles, {
    withTheme: true
})(UserTable)