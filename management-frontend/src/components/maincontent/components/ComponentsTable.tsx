import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, IntColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems, clearAll} from "../../../reducers/componentsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {Button, IconButton, Tooltip, Typography} from "@material-ui/core";
import {useHistory} from "react-router";
import InfoIcon from "@material-ui/icons/Info";
import {ComponentInfo} from "../../../entities/ComponentInfo";
import AddNewComponentDialog from "./AddNewComponentDialog";
import MaintainerOnly from "../../protectors/MaintainerOnly";
import {LastHeartbeatColumn} from "./LastHeartbeatColumn";

type ComponentsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const ComponentsTable = (props: ComponentsTableProps) => {
    const {components, addNewItems, clearAll} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<ComponentInfo>>("/component", [["page", page], ["size", size], ...requirements])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        CustomColumn<ComponentInfo, string>("serverAddress", "Server",
            (address, component) => {
                return <Button
                    onClick={() => history.push(`/server/${component.serverId}`)}>{address}</Button>
            }
            , {sortId: "server.address"}),
        IntColumn("port", "Port", {sortId: "port"}),
        StringColumn("type", "Component Type", {
            sortId: "type",
            filterOptions: ["WEBSERVER", "INDEX_SERVER", "INDEX_BUILDER", "CONSOLE_CLIENT"]
        }),
        StringColumn("status", "Status", {
            sortId: "status"
        }),
        LastHeartbeatColumn("lastHeartbeat", "Last heartbeat", {sortId: "lastHeartbeat"}),
        CustomColumn<ComponentInfo, undefined>("componentDetails", "Component Details",
            (prop, component) => <Tooltip title="Component details">
                <IconButton onClick={() => history.push(`/component/${component.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <div>
        <Typography variant="h3">Components</Typography>
        <PaginatedTable
            data={components}
            clearData={clearAll}
            columns={columns}
            requestPage={requestPage}
        />
        <MaintainerOnly>
            <AddNewComponentDialog/>
        </MaintainerOnly>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    components: state.components
});
const mapDispatchToProps = {
    addNewItems,
    clearAll
};

export default connect(mapStateToProps, mapDispatchToProps)(ComponentsTable);


