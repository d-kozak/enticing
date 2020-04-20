import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../reducers/snackbarReducer";


export type LogsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Logs = (props: LogsProps) => {
    const {} = props;

    return <div>
        log
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Logs));