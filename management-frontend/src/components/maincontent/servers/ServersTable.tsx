import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, IntColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems, clearAll} from "../../../reducers/serversReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {ServerInfo} from "../../../entities/ServerInfo";
import {IconButton, Tooltip, Typography} from "@material-ui/core";
import {useHistory} from "react-router";
import InfoIcon from "@material-ui/icons/Info";
import AddNewServerDialog from "./AddNewServerDialog";


type ServersTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const ServersTable = (props: ServersTableProps) => {
    const {servers, addNewItems, clearAll} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<ServerInfo>>("/server", [["page", page], ["size", size], ...requirements])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("address", "Address", {sortId: "address"}),
        IntColumn("availableProcessors", "Available processors", {sortId: "availableProcessors"}),
        IntColumn("totalPhysicalMemorySize", "Ram size", {sortId: "totalPhysicalMemorySize"}),
        CustomColumn<ServerInfo, undefined>("serverDetails", "Server Details",
            (prop, server) => <Tooltip title="Server details">
                <IconButton onClick={() => history.push(`/server/${server.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <div>
        <Typography variant="h3">Servers</Typography>
        <PaginatedTable
            data={servers}
            clearData={clearAll}
            columns={columns}
            requestPage={requestPage}/>
        <AddNewServerDialog/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    servers: state.servers
});
const mapDispatchToProps = {
    addNewItems,
    clearAll
};

export default connect(mapStateToProps, mapDispatchToProps)(ServersTable);


