import React, {FunctionComponent} from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";
import {isAdmin} from "../../reducers/userDetailsReducer";


type AdminOnlyProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const AdminOnly: FunctionComponent<AdminOnlyProps> = (props) => {
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