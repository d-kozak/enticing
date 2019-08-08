import {ApplicationState} from "../../reducers/ApplicationState";
import {connect} from "react-redux";
import React, {ReactNode} from 'react';
import {isLoggedIn} from "../../reducers/selectors";

import {Redirect, Route, RouteComponentProps} from "react-router";
import {snackbarActions} from "../../reducers/SnackBarReducer";

type UserRouteProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    path: String,
    render: (props: RouteComponentProps<any>) => ReactNode
}

const UserRoute = (props: UserRouteProps) => {
    const {isLoggedIn, path, render, openSnackbar} = props;
    if (!isLoggedIn) {
        openSnackbar(`You have to be logged in to visit ${path}`);
        return <Redirect to="/"/>
    } else {
        return <Route to={path} render={render}/>
    }
};

const mapStateToProps = (state: ApplicationState) => ({
    isLoggedIn: isLoggedIn(state)
});
const mapDispatchToProps = {
    openSnackbar: snackbarActions.openSnackbar
};

export default connect(mapStateToProps, mapDispatchToProps)(UserRoute);