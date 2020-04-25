import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {List, ListItem, ListItemIcon, ListItemText, Paper, Typography} from "@material-ui/core";
import {DashboardState} from "./Dashboard";
import {generate} from "shortid";
import {LogDto, LogType} from "../../../entities/LogDto";
import ErrorIcon from "@material-ui/icons/Error";
import NotificationImportantIcon from "@material-ui/icons/NotificationImportant";


export type LogsDashboardProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { state: DashboardState }

const displayLog = (log: LogDto) => `${log.componentAddress} : ${log.componentType} : ${log.sourceClass} : ${log.message}`

const LogsDashboard = (props: LogsDashboardProps) => {
    const {state} = props;
    const importantLogs = state.logs.important;

    return <Paper>
        <Typography variant="h4">Logs</Typography>
        <List component="nav">
            {importantLogs.map(log => <ListItem key={generate()}>
                    <ListItemIcon>
                        {log.logType.toString() === LogType[LogType.ERROR] && <ErrorIcon/>}
                        {log.logType.toString() === LogType[LogType.WARN] && <NotificationImportantIcon/>}
                    </ListItemIcon>
                <ListItemText primary={displayLog(log)}/>
                </ListItem>
            )}
        </List>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default (connect(mapStateToProps, mapDispatchToProps)(LogsDashboard));