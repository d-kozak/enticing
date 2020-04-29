import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {Route, Switch, useRouteMatch} from "react-router";
import ComponentDetails from "./ComponentDetails";
import ComponentsTable from "./ComponentsTable";
import {Paper} from "@material-ui/core";


export type ComponentsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Components = (props: ComponentsProps) => {
    const match = useRouteMatch();
    return <Paper>
        <Switch>
            <Route path={`${match.path}/:componentId`}
                   render={({match}) => <ComponentDetails componentId={match.params['componentId']}/>}/>
            <Route path={`${match.path}`}>
                <ComponentsTable/>
            </Route>
        </Switch>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Components));