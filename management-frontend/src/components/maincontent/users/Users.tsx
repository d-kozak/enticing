import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import UsersTable from "./UsersTable";
import {Route, Switch, useRouteMatch} from "react-router";
import UserDetails from "./UserDetails";
import {Paper} from "@material-ui/core";


export type UsersProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Users = (props: UsersProps) => {
    const match = useRouteMatch();
    return <Paper>
        <Switch>
            <Route path={`${match.path}/:userId`} render={({match}) => <UserDetails userId={match.params['userId']}/>}/>
            <Route path={`${match.path}`}>
                <UsersTable/>
            </Route>
        </Switch>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Users));