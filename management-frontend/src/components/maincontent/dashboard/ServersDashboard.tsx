import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {List, ListItem, ListItemText, Paper, Typography} from "@material-ui/core";
import {DashboardState} from "./Dashboard";


export type ServersDashboardProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { state: DashboardState }

const ServersDashboard = (props: ServersDashboardProps) => {
    const {state} = props;
    const servers = state.servers;

    return <Paper>
        <Typography variant="h4">Servers</Typography>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Total servers: ${servers.total}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Average CPU usage: ${servers.averageCpuUsage}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Average RAM usage: ${servers.averageRamUsage}`}/>
            </ListItem>
        </List>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default (connect(mapStateToProps, mapDispatchToProps)(ServersDashboard));