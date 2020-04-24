import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {Route, Switch, useRouteMatch} from "react-router";
import CommandDetails from "./CommandDetails";
import CommandsTable from "./CommandsTable";
import {Paper} from "@material-ui/core";


export type CommandsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Commands = (props: CommandsProps) => {
    const match = useRouteMatch();
    return <Paper>
        <Switch>
            <Route path={`${match.path}/:commandId`}>
                <CommandDetails/>
            </Route>
            <Route path={`${match.path}`}>
                <CommandsTable/>
            </Route>
        </Switch>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Commands));