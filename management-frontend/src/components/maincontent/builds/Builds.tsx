import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {Route, Switch, useRouteMatch} from "react-router";
import CommandDetails from "../commands/CommandDetails";
import BuildsTable from "./BuildsTable";
import {Paper} from "@material-ui/core";


export type BuildsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Builds = (props: BuildsProps) => {
    const {} = props;

    const match = useRouteMatch();
    return <Paper>
        <Switch>
            <Route path={`${match.path}/:commandId`}>
                <CommandDetails/>
            </Route>
            <Route path={`${match.path}`}>
                <BuildsTable/>
            </Route>
        </Switch>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Builds));