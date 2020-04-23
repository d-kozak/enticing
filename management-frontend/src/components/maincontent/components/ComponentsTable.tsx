import {makeStyles} from "@material-ui/core/styles";
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, IntColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems} from "../../../reducers/componentsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {Button, IconButton, Tooltip} from "@material-ui/core";
import {useHistory} from "react-router";
import InfoIcon from "@material-ui/icons/Info";
import {ComponentInfo} from "../../../entities/ComponentInfo";
import {requestServerInfo} from "../../../reducers/serversReducer";
import AddNewComponentDialog from "./AddNewComponentDialog";

const useStyles = makeStyles({});

type ComponentsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const ComponentsTable = (props: ComponentsTableProps) => {
    const classes = useStyles();
    const {components, servers, addNewItems, requestServerInfo} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number) => {
        getRequest<PaginatedResult<ComponentInfo>>("/component", [["page", page], ["size", size]])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        CustomColumn("serverId", "Server",
            (serverId: string, component) => {
                let server = servers.elements[serverId];
                let label = '';
                if (server) {
                    label = server.address;
                } else {
                    label = serverId;
                    requestServerInfo(serverId);
                }
                return <Button
                    onClick={() => history.push(`/server/${serverId}`)}>{label}</Button>
            }
        ),
        IntColumn("port", "Port"),
        StringColumn("type", "Component Type"),
        StringColumn("lastHeartbeat", "Last heartbeat"),
        CustomColumn<ComponentInfo, undefined>("componentDetails", "Component Details",
            (prop, component) => <Tooltip title="Component details">
                <IconButton onClick={() => history.push(`/component/${component.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <div>
        <PaginatedTable
            data={components}
            columns={columns}
            requestPage={requestPage}
        />
        <AddNewComponentDialog/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    servers: state.servers,
    components: state.components
});
const mapDispatchToProps = {
    addNewItems,
    requestServerInfo: requestServerInfo as (id: string) => void
};

export default connect(mapStateToProps, mapDispatchToProps)(ComponentsTable);


