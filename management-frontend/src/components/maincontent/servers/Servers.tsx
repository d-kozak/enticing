import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {Route, Switch, useRouteMatch} from "react-router";
import ServerDetails from "./ServerDetails";
import ServersTable from "./ServersTable";


export type ServersProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Servers = (props: ServersProps) => {
    const {} = props;
    const match = useRouteMatch();
    return <Switch>
        <Route path={`${match.path}/:serverId`}>
            <ServerDetails/>
        </Route>
        <Route path={`${match.path}`}>
            <ServersTable/>
        </Route>
    </Switch>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default (connect(mapStateToProps, mapDispatchToProps)(Servers));