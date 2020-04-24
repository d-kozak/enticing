import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {List, ListItem, ListItemText, ListSubheader, Paper, Typography} from "@material-ui/core";
import {DashboardState} from "./Dashboard";
import {useHistory} from "react-router";


export type BuildsDashboardProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { state: DashboardState }

const BuildsDashboard = (props: BuildsDashboardProps) => {
    const {state} = props;
    const builds = state.builds

    const history = useHistory();

    return <Paper>
        <Typography variant="h4">Builds</Typography>
        <List component="nav">
            {builds.running &&
            <ListItem button onClick={() => builds.running && history.push(`/command/${builds.running.id}`)}>
                <ListItemText primary={`One build running from ${builds.running.startAt}`}/>
            </ListItem>}
            {builds.last &&
            <ListItem button onClick={() => builds.last && history.push(`/command/${builds.last.id}`)}>
                <List component="div"
                      subheader={<ListSubheader component="div" id="nested-list-subheader">
                          Last build
                      </ListSubheader>}
                >
                    <ListItem>
                        <ListItemText primary={`Finished at: ${builds.last.finishedAt}`}/>
                    </ListItem>
                    <ListItem>
                        <ListItemText primary={`Result: ${builds.last.state}`}/>
                    </ListItem>
                </List>
            </ListItem>
            }
        </List>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default (connect(mapStateToProps, mapDispatchToProps)(BuildsDashboard));