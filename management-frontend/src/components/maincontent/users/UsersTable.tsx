import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems, clearAll} from "../../../reducers/usersReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {IconButton, Tooltip, Typography} from "@material-ui/core";
import {User} from "../../../entities/user";
import InfoIcon from "@material-ui/icons/Info";
import {useHistory} from "react-router";
import AddNewUserDialog from "./AddNewUserDialog";

type UsersTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const UsersTable = (props: UsersTableProps) => {
    const {users, addNewItems, clearAll} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<User>>("/user/all", [["page", page], ["size", size], ...requirements])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("login", "Login", {sortId: "login"}),
        CustomColumn<User, Array<string>>("roles", "Roles",
            (roles) => roles.join(", ")
        ),
        CustomColumn<User, undefined>("userDetails", "User details",
            (prop, user) => <Tooltip title="User details">
                <IconButton onClick={() => history.push(`/user-management/${user.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <div>
        <Typography variant="h3">Users</Typography>
        <PaginatedTable
            data={users}
            clearData={clearAll}
            columns={columns}
            requestPage={requestPage}
        />
        <AddNewUserDialog/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    users: state.users
});
const mapDispatchToProps = {
    addNewItems,
    clearAll
};

export default connect(mapStateToProps, mapDispatchToProps)(UsersTable);


