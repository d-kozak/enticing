import {Redirect, Route, RouteComponentProps, withRouter} from "react-router";
import React, {FunctionComponent} from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";
import {isAdmin} from "../../reducers/userDetailsReducer";
import {openSnackbarAction} from "../../reducers/snackbarReducer";


type AdminRouteProps = ReturnType<typeof mapStateToProps> & RouteComponentProps & typeof mapDispatchToProps & {
    exact?: boolean,
    path: String
}

const AdminRoute: FunctionComponent<AdminRouteProps> = (props) => {
    const {isAdmin, children, path, openSnackbarAction, ...rest} = props;
    const exact = props.exact || false;
    if (!isAdmin) {
        openSnackbarAction("You don't have rights to visit this route");
    }
    return <Route {...rest} to={path} exact={exact} render={() => isAdmin ? children : <Redirect to="/login"/>}/>
}

const mapStateToProps = (state: ApplicationState) => ({
    isAdmin: isAdmin(state)
});
const mapDispatchToProps = {
    openSnackbarAction
};


export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AdminRoute));