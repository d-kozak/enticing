import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../reducers/snackbarReducer";


export type UsersProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Users = (props: UsersProps) => {
    const {} = props;

    return <div>
        users
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Users));