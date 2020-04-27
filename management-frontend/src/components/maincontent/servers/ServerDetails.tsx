import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {useParams} from "react-router";
import {CircularProgress, Divider, List, ListItem, ListItemText, Typography} from "@material-ui/core";
import {requestServerInfo} from "../../../reducers/serversReducer";
import ServerComponentsTable from "./ServerComponentsTable";
import ServerStatusChart from "./ServerStatusChart";
import {BackButton} from "../../button/BackButton";
import AddNewComponentDialog from "../components/AddNewComponentDialog";
import {Centered} from "../../Centered";
import MaintainerOnly from "../../protectors/MaintainerOnly";
import RemoveServerDialog from "./RemoveServerDialog";

export type ServerDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const ServerDetails = (props: ServerDetailsProps) => {
    const {servers, requestServerInfo} = props;
    const {serverId} = useParams();
    if (!serverId) {
        return <div> No serverId </div>
    }
    const server = servers.elements[serverId]
    if (!server) {
        requestServerInfo(serverId);
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