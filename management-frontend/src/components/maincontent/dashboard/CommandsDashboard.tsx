import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {List, ListItem, ListItemText, Paper, Typography} from "@material-ui/core";
import {DashboardState} from "./Dashboard";


export type CommandsDashboardProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { state: DashboardState }

const CommandsDashboard = (props: CommandsDashboardProps) => {
    const {state} = props;
    const commands = state.commands;

    return <Paper>
        <Typography variant="h4">Commands</Typography>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Running: ${commands.running.length}`}/>
            </ListItem>
        </List>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default (connect(mapStateToProps, mapDispatchToProps)(CommandsDashboard));