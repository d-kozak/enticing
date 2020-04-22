import {Redirect, useHistory} from "react-router";
import React from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";
import {isLoggedIn} from "../../reducers/userDetailsReducer";
import {openSnackbarAction} from "../../reducers/snackbarReducer";
import {Paper} from "@material-ui/core";
import {Centered} from "../Centered";


type LoginProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps

const Login = (props: LoginProps) => {
    const {isLoggedIn} = props;
    const history = useHistory();

    if (isLoggedIn) {
        return <Redirect to={"/"}/>
    }

    return <Centered>
        <Paper>
            login
        </Paper>
    </Centered>
}

const mapStateToProps = (state: ApplicationState) => ({
    isLoggedIn: isLoggedIn(state)
});
const mapDispatchToProps = {
    openSnackbarAction
};


export default connect(mapStateToProps, mapDispatchToProps)(Login);