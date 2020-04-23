import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {List, ListItem, ListItemText, Paper, Typography} from "@material-ui/core";
import {DashboardState} from "./Dashboard";


export type ComponentsDashboardProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { state: DashboardState }

const ComponentsDashboard = (props: ComponentsDashboardProps) => {
    const {state} = props;
    const components = state.components;

    return <Paper>
        <Typography variant="h4">Components</Typography>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`All components: ${components.total}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Unresponsive: ${components.unresponsive.length}`}/>
            </ListItem>
        </List>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default (connect(mapStateToProps, mapDispatchToProps)(ComponentsDashboard));