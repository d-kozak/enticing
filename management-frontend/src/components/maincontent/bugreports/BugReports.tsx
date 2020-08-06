import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {Route, Switch, useRouteMatch} from "react-router";

import {Paper} from "@material-ui/core";
import BugReportsTable from "./BugReportsTable";


export type BugReportsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const BugReports = (props: BugReportsProps) => {
    const match = useRouteMatch();
    return <Paper>
        <Switch>
            <Route path={`${match.path}`}>
                <BugReportsTable/>
            </Route>
        </Switch>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(BugReports));