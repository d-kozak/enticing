import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React, {useCallback} from 'react';
import {CircularProgress, Divider, List, ListItem, ListItemText, Typography} from "@material-ui/core";
import {requestServerInfo} from "../../../reducers/serversReducer";
import ServerComponentsTable from "./ServerComponentsTable";
import ServerStatusChart from "./ServerStatusChart";
import {BackButton} from "../../button/BackButton";
import AddNewComponentDialog from "../components/AddNewComponentDialog";
import {Centered} from "../../Centered";
import MaintainerOnly from "../../protectors/MaintainerOnly";
import RemoveServerDialog from "./RemoveServerDialog";
import {useInterval} from "../../../utils/useInterval";

export type ServerDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { serverId: string }

const ServerDetails = (props: ServerDetailsProps) => {
    const {serverId, servers, requestServerInfo} = props;
    const refresh = useCallback(() => requestServerInfo(serverId), [requestServerInfo, serverId])
    useInterval(refresh, 2_000)
    const server = servers.elements[serverId]
    if (!server) {
        refresh();
        return <Centered>
            <CircularProgress color="inherit"/>
        </Centered>
    }
    return <div>
        <BackButton/>
        <Typography variant="h3">Server details</Typography>
        <Divider/>
        <List component="nav">
            <ListItem button onClick={() => window.open(`http://${server.address}`, "_blank")}>
                <ListItemText primary={`Address: ${server.address}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Processors: ${server.availableProcessors}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Ram size: ${server.totalPhysicalMemorySize}`}/>
            </ListItem>
        </List>
        <Divider/>
        <MaintainerOnly>
            <List component="nav">
                <ListItem>
                    <RemoveServerDialog server={server}/>
                </ListItem>
            </List>
            <Divider/>
        </MaintainerOnly>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Status`}/>
            </ListItem>
        </List>
        <ServerStatusChart serverId={serverId}/>
        <Divider/>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Components`}/>
            </ListItem>
        </List>
        <ServerComponentsTable serverId={serverId}/>
        <AddNewComponentDialog predefinedServer={server}/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    servers: state.servers
});

const mapDispatchToProps = {
    requestServerInfo: requestServerInfo as (id: string) => void
};

export default connect(mapStateToProps, mapDispatchToProps)(ServerDetails);