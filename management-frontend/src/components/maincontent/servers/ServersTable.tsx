import {makeStyles} from "@material-ui/core/styles";
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, IntColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems} from "../../../reducers/serversReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {ServerInfo} from "../../../entities/ServerInfo";
import {IconButton, Tooltip} from "@material-ui/core";
import {useHistory} from "react-router";
import InfoIcon from "@material-ui/icons/Info";
import AddNewServerDialog from "./AddNewServerDialog";

const useStyles = makeStyles({});

type ServersTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const ServersTable = (props: ServersTableProps) => {
    const classes = useStyles();
    const {servers, addNewItems} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number) => {
        getRequest<PaginatedResult<ServerInfo>>("/server", [["page", page], ["size", size]])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("address", "Address"),
        IntColumn("availableProcessors", "Component Address"),
        IntColumn("totalPhysicalMemorySize", "Ram size"),
        CustomColumn<ServerInfo, undefined>("serverDetails", "Server Details",
            (prop, server) => <Tooltip title="Server details">
                <IconButton onClick={() => history.push(`/server/${server.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <div>
        <PaginatedTable
            data={servers}
            columns={columns}
            requestPage={requestPage}/>
        <AddNewServerDialog/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    servers: state.servers
});
const mapDispatchToProps = {
    addNewItems
};

export default connect(mapStateToProps, mapDispatchToProps)(ServersTable);


