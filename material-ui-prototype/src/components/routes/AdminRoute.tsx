import {Redirect, Route, RouteComponentProps} from "react-router";
import React, {ReactNode} from 'react';


export interface AdminRouteProps {
    render: (props: RouteComponentProps<any>) => ReactNode,
    isAdmin: boolean,
    path: String,
    showSnackBarMessage?: (message: string) => void
}

const AdminRoute = (props: AdminRouteProps) => {
    const {isAdmin, render, path, showSnackBarMessage} = props;
    if (!isAdmin) {
        if (showSnackBarMessage)
            showSnackBarMessage("You don't have rights to visit this route");
        return <Redirect to="/"/>
    } else {
        return <Route to={path} render={render}/>
    }
}


export default AdminRoute;