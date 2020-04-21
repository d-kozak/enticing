import {RouteComponentProps} from "react-router";
import React, {FunctionComponent, ReactNode} from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";
import {isAdmin} from "../../reducers/userDetailsReducer";


type AdminRouteProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    render: (props: RouteComponentProps<any>) => ReactNode,
}

const AdminOnly: FunctionComponent<AdminRouteProps> = (props) => {
    const {isAdmin, children} = props;
    return <React.Fragment>
        {isAdmin && children}
    </React.Fragment>
}

const mapStateToProps = (state: ApplicationState) => ({
    isAdmin: isAdmin(state)
});
const mapDispatchToProps = {};


export default connect(mapStateToProps, mapDispatchToProps)(AdminOnly);