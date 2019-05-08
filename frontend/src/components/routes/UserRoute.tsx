import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import React, {ReactNode} from 'react';
import {isLoggedInSelector} from "../../reducers/selectors";
import {openSnackBar} from "../../actions/SnackBarActions";
import {Redirect, Route, RouteComponentProps} from "react-router";

type UserRouteProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    path: String,
    render: (props: RouteComponentProps<any>) => ReactNode
}

const UserRoute = (props: UserRouteProps) => {
    const {isLoggedIn, path, render, openSnackBar} = props;
    if (!isLoggedIn) {
        openSnackBar(`You have to be logged in to visit ${path}`);
        return <Redirect to="/"/>
    } else {
        return <Route to={path} render={render}/>
    }
};

const mapStateToProps = (state: AppState) => ({
    isLoggedIn: isLoggedInSelector(state)
});
const mapDispatchToProps = {
    openSnackBar: openSnackBar
};

export default connect(mapStateToProps, mapDispatchToProps)(UserRoute);