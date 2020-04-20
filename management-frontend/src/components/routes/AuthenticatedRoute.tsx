import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React, {ReactNode} from 'react';
import {isLoggedIn} from "../../reducers/userReducer";

import {Redirect, Route, RouteComponentProps} from "react-router";
import {openSnackbarAction} from "../../reducers/snackbarReducer";

type AuthenticatedRouteProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    path: String,
    render: (props: RouteComponentProps<any>) => ReactNode
}

const AuthenticatedRoute = (props: AuthenticatedRouteProps) => {
    const {isLoggedIn, path, render, openSnackbarAction} = props;
    if (!isLoggedIn) {
        openSnackbarAction(`You have to be logged in to visit ${path}`);
        return <Redirect to="/login"/>
    } else {
        return <Route to={path} render={render}/>
    }
};

const mapStateToProps = (state: ApplicationState) => ({
    isLoggedIn: isLoggedIn(state)
});
const mapDispatchToProps = {
    openSnackbarAction
};

export default connect(mapStateToProps, mapDispatchToProps)(AuthenticatedRoute);