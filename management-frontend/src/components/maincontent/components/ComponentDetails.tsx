import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {useHistory, useParams} from "react-router";
import {CircularProgress, Divider, List, ListItem, ListItemText, Typography} from "@material-ui/core";
import {requestComponentInfo} from "../../../reducers/componentsReducer";
import {requestServerInfo} from "../../../reducers/serversReducer";
import ComponentLogsTable from "./ComponentLogsTable";
import {BackButton} from "../../button/BackButton";
import {Centered} from "../../Centered";


export type ComponentDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const ComponentDetails = (props: ComponentDetailsProps) => {
    const {components, servers, requestComponentInfo, requestServerInfo} = props;
    const {componentId} = useParams();
    const history = useHistory();
    if (!componentId) {
        return <div> No component id </div>
    }

    const component = components.elements[componentId];
    if (!component) {
        requestComponentInfo(componentId);
        return <div>
            <CircularProgress color="inherit"/>
        </div>
    }
    const server = servers.elements[component.serverId];
    if (!server) {
        requestServerInfo(component.serverId);
        return <Centered>
            <CircularProgress color="inherit"/>
        </Centered>
    }

    return <div>
        <BackButton/>
        <Typography variant="h3">Component details</Typography>
        <Divider/>
        <List component="nav">
            <ListItem button onClick={() => history.push(`/server/${server.id}`)}>
                <ListItemText primary={`Server: ${server.address}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Port: ${component.port}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Type: ${component.type}`}/>
            </ListItem>
        </List>
        <Divider/>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Last heartbeat: ${component.lastHeartbeat}`}/>
            </ListItem>
        </List>
        <Divider/>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Logs`}/>
            </ListItem>
        </List>
        <ComponentLogsTable componentId={componentId}/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    components: state.components,
    servers: state.servers
});

const mapDispatchToProps = {
    requestComponentInfo: requestComponentInfo as (id: string) => void,
    requestServerInfo: requestServerInfo as (id: string) => void
};

export default (connect(mapStateToProps, mapDispatchToProps)(ComponentDetails));