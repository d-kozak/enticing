import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import UsersTable from "./UsersTable";
import {Route, Switch, useRouteMatch} from "react-router";
import UserDetails from "./UserDetails";


export type UsersProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Users = (props: UsersProps) => {
    const {} = props;
    const match = useRouteMatch();
    return <Switch>
        <Route path={`${match.path}/:userId`}>
            <UserDetails/>
        </Route>
        <Route path={`${match.path}`}>
            <UsersTable/>
        </Route>
    </Switch>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Users));