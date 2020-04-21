import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {Route, Switch, useRouteMatch} from "react-router";
import ComponentDetails from "./ComponentDetails";
import ComponentsTable from "./ComponentsTable";


export type ComponentsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Components = (props: ComponentsProps) => {
    const {} = props;
    const match = useRouteMatch();
    return <Switch>
        <Route path={`${match.path}/:componentId`}>
            <ComponentDetails/>
        </Route>
        <Route path={`${match.path}`}>
            <ComponentsTable/>
        </Route>
    </Switch>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Components));