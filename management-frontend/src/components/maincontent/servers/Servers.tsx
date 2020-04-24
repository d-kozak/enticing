import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {Route, Switch, useRouteMatch} from "react-router";
import ServerDetails from "./ServerDetails";
import ServersTable from "./ServersTable";
import {Paper} from "@material-ui/core";


export type ServersProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Servers = (props: ServersProps) => {
    const match = useRouteMatch();
    return <Paper>
        <Switch>
            <Route path={`${match.path}/:serverId`}>
                <ServerDetails/>
            </Route>
            <Route path={`${match.path}`}>
                <ServersTable/>
            </Route>
        </Switch>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(Servers);