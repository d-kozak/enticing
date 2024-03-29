import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {isLoggedIn} from "../../reducers/userDetailsReducer";

import {Redirect, Route, RouteComponentProps} from "react-router";
import {withRouter} from "react-router-dom";
import {openSnackbarAction} from "../../reducers/snackbarReducer";

type AuthenticatedRouteProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & RouteComponentProps & {
    path: string,
    exact?: boolean,
    children?: React.ReactNode,
    render?: (routeProps: RouteComponentProps<any>) => React.ReactNode
}

const AuthenticatedRoute = (props: AuthenticatedRouteProps) => {
    const {isLoggedIn, path, children, render, exact, openSnackbarAction, ...rest} = props;
    if (!isLoggedIn) {
        openSnackbarAction(`You have to be logged in to visit this page`);
        return <Redirect to={"/login"}/>
    }
    if (children) {
        return <Route {...rest} exact={exact} to={path}>
            {children}
        </Route>
    } else return <Route {...rest} exact={exact} to={path} render={render}/>
};

const mapStateToProps = (state: ApplicationState) => ({
    isLoggedIn: isLoggedIn(state)
});
const mapDispatchToProps = {
    openSnackbarAction
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AuthenticatedRoute));