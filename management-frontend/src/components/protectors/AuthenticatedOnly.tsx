import React, {FunctionComponent} from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";
import {isLoggedIn} from "../../reducers/userDetailsReducer";


type AuthenticatedOnlyProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const AuthenticatedOnly: FunctionComponent<AuthenticatedOnlyProps> = (props) => {
    const {isLoggedIn, children} = props;
    return <React.Fragment>
        {isLoggedIn && children}
    </React.Fragment>
}

const mapStateToProps = (state: ApplicationState) => ({
    isLoggedIn: isLoggedIn(state)
});
const mapDispatchToProps = {};


export default connect(mapStateToProps, mapDispatchToProps)(AuthenticatedOnly);