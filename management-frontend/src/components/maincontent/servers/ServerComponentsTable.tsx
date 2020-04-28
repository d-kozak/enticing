import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {
    CustomColumn,
    DateTimeColumn,
    IntColumn,
    PaginatedTableColumn,
    StringColumn
} from "../../pagination/PaginatedTableColumn"
import {addComponentsToServer, clearComponentsFromServer} from "../../../reducers/serversReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {IconButton, Tooltip} from "@material-ui/core";
import {useHistory} from "react-router";
import InfoIcon from "@material-ui/icons/Info";
import {ComponentInfo} from "../../../entities/ComponentInfo";

type SimpleProps = {
    serverId: string
};

type ServerComponentsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & SimpleProps

const ServerComponentsTable = (props: ServerComponentsTableProps) => {
    const {server, addComponentsToServer, clearComponentsFromServer} = props;

    const history = useHistory();

    if (!server) {
        return <div>no data</div>;
    }

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<ComponentInfo>>(`/server/${server.id}/component`, [["page", page], ["size", size], ...requirements])
            .then(res => {
                addComponentsToServer({...res, serverId: server.id});
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        IntColumn("port", "Port", {sortId: "port"}),
        StringColumn("type", "Component Type", {sortId: "type"}),
        DateTimeColumn("lastHeartbeat", "Last heartbeat", {sortId: "lastHeartbeat"}),
        CustomColumn<ComponentInfo, undefined>("componentDetails", "Component Details",
            (prop, component) => <Tooltip title="Component details">
                <IconButton onClick={() => history.push(`/component/${component.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <PaginatedTable
        data={server.components}
        clearData={() => clearComponentsFromServer(server.id)}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState, props: SimpleProps) => ({
    server: state.servers.elements[props.serverId],
});
const mapDispatchToProps = {
    addComponentsToServer,
    clearComponentsFromServer
};

export default connect(mapStateToProps, mapDispatchToProps)(ServerComponentsTable);


