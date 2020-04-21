import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import UsersTable from "./UsersTable";


export type UsersProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Users = (props: UsersProps) => {
    const {} = props;

    return <div>
        <UsersTable/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Users));