import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";


export type ServerStatusProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const ServerStatus = (props: ServerStatusProps) => {
    const {} = props;
    return <div>
        status
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(ServerStatus));