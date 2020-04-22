import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {useParams} from "react-router";
import {CircularProgress, Divider, List, ListItem, ListItemText, Paper, Typography} from "@material-ui/core";
import {requestServerInfo} from "../../../reducers/serversReducer";
import ServerComponentsTable from "./ServerComponentsTable";
import ServerStatusChart from "./ServerStatusChart";
import {BackButton} from "../../button/BackButton";

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
        return <div>
            <CircularProgress color="inherit"/>
        </div>
    }
    return <Paper>
        <BackButton/>
        <Typography variant="h3">Server {server.address}</Typography>
        <Divider/>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Processors: ${server.availableProcessors}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Ram size: ${server.totalPhysicalMemorySize}`}/>
            </ListItem>
        </List>
        <Divider/>
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
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({
    servers: state.servers
});

const mapDispatchToProps = {
    requestServerInfo: requestServerInfo as (id: string) => void
};

export default (connect(mapStateToProps, mapDispatchToProps)(ServerDetails));