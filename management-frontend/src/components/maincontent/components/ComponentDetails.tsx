import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React, {useCallback} from 'react';
import {useHistory} from "react-router";
import {CircularProgress, Divider, List, ListItem, ListItemText, ListSubheader, Typography} from "@material-ui/core";
import {requestComponentInfo} from "../../../reducers/componentsReducer";
import {requestServerInfo} from "../../../reducers/serversReducer";
import ComponentLogsTable from "./ComponentLogsTable";
import {BackButton} from "../../button/BackButton";
import {Centered} from "../../Centered";
import KillComponentDialog from "./KillComponentDialog";
import MaintainerOnly from "../../protectors/MaintainerOnly";
import {useInterval} from "../../../utils/useInterval";
import LastheartbeatInfo from "./LastheartbeatInfo";
import StartComponentDialog from "./StartComponentDialog";


export type ComponentDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { componentId: string }

const ComponentDetails = (props: ComponentDetailsProps) => {
    const {components, componentId, servers, requestComponentInfo, requestServerInfo} = props;
    const history = useHistory();
    const refresh = useCallback(() => requestComponentInfo(componentId), [requestComponentInfo, componentId]);
    useInterval(refresh, 1_000);
    const component = components.elements[componentId];
    if (!component) {
        refresh();
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
        <List component="nav" subheader={<ListSubheader component="div">General info</ListSubheader>}>
            <ListItem button onClick={() => history.push(`/server/${server.id}`)}>
                <ListItemText primary={`Server: ${server.address}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Port: ${component.port}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Type: ${component.type}`}/>
            </ListItem>
            <ListItem button onClick={() => history.push(`/build/${component.buildId}`)}>
                <ListItemText primary={`Build: ${component.buildId}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Status: ${component.status}`}/>
            </ListItem>
        </List>
        <Divider/>
        <List component="nav" subheader={<ListSubheader component="div">Last heartbeat</ListSubheader>}>
            <ListItem>
                <LastheartbeatInfo component={component}/>
            </ListItem>
        </List>
        <Divider/>
        <MaintainerOnly>
            <List component="nav" subheader={<ListSubheader component="div">Actions</ListSubheader>}>
                <ListItem>
                    {component.status === "RUNNING" ? <KillComponentDialog component={component}/> :
                        <StartComponentDialog component={component}/>}
                </ListItem>
            </List>
            <Divider/>
        </MaintainerOnly>
        <List component="nav" subheader={<ListSubheader component="div">Logs</ListSubheader>}>

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