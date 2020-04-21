import {makeStyles} from "@material-ui/core/styles";
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems} from "../../../reducers/usersReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {IconButton, Tooltip} from "@material-ui/core";
import {User} from "../../../entities/user";
import InfoIcon from "@material-ui/icons/Info";
import {useHistory} from "react-router";


const useStyles = makeStyles({});

type LogTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const LogTable = (props: LogTableProps) => {
    const classes = useStyles();
    const {users, addNewItems} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number) => {
        getRequest<PaginatedResult<User>>("/admin/user", [["page", page], ["size", size]])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("login", "Login"),
        StringColumn("active", "Active"),
        StringColumn("roles", "Roles"),
        CustomColumn<User, undefined>("componentDetails", "Component Details",
            (prop, user) => <Tooltip title="User details">
                <IconButton onClick={() => history.push(`/component/${user.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];


    return <PaginatedTable
        data={users}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState) => ({
    users: state.users
});
const mapDispatchToProps = {
    addNewItems
};

export default connect(mapStateToProps, mapDispatchToProps)(LogTable);


