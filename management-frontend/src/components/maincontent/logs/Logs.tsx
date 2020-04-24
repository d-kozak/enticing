import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import LogTable from "./LogTable";
import {Paper, Typography} from "@material-ui/core";


export type LogsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Logs = (props: LogsProps) => {
    return <Paper>
        <Typography variant="h3">Logs</Typography>
        <LogTable/>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Logs));