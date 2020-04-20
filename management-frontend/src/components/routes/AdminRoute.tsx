import {Redirect, Route, RouteComponentProps} from "react-router";
import React, {ReactNode} from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";
import {isAdmin} from "../../reducers/userReducer";
import {openSnackbarAction} from "../../reducers/snackbarReducer";


type AdminRouteProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    render: (props: RouteComponentProps<any>) => ReactNode,
    path: String
}

const AdminRoute = (props: AdminRouteProps) => {
    const {isAdmin, render, path, openSnackbarAction} = props;
    if (!isAdmin) {
        openSnackbarAction("You don't have rights to visit this route");
        return <Redirect to="/"/>
    } else {
        return <Route to={path} render={render}/>
    }
}

const mapStateToProps = (state: ApplicationState) => ({
    isAdmin: isAdmin(state)
});
const mapDispatchToProps = {
    openSnackbarAction
};


export default connect(mapStateToProps, mapDispatchToProps)(AdminRoute);