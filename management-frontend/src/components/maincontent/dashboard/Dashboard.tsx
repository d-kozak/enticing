import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React, {useEffect, useState} from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {CircularProgress, Grid} from "@material-ui/core";
import ServersDashboard from "./ServersDashboard";
import ComponentsDashboard from "./ComponentsDashboard";
import LogsDashboard from "./LogsDashboard";
import CommandsDashboard from "./CommandsDashboard";
import BuildsDashboard from "./BuildsDashboard";
import {getRequest} from "../../../network/requests";
import {ComponentInfo} from "../../../entities/ComponentInfo";
import {LogDto} from "../../../entities/LogDto";
import {CommandDto} from "../../../entities/CommandDto";
import {Centered} from "../../Centered";


export interface DashboardState {
    servers: {
        total: number,
        averageCpuUsage: number,
        averageRamUsage: number
    },
    components: {
        total: number,
        unresponsive: Array<ComponentInfo>
    },
    logs: {
        important: Array<LogDto>
    },
    commands: {
        running: Array<CommandDto>
    },
    builds: {
        running: CommandDto | null
        last: CommandDto | null
    }
}

export type DashboardProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Dashboard = (props: DashboardProps) => {
    const [dashboardState, setDashboardState] = useState<DashboardState | undefined>();

    const refreshState = () => {
        getRequest<DashboardState>("/dashboard")
            .then(state => setDashboardState(state))
            .catch(err => console.error(err))
    };

    useEffect(() => {
        refreshState()
        const interval = setInterval(refreshState, 2000)
        return () => clearInterval(interval);
    }, []);

    if (!dashboardState) {
        return <Centered>
            <CircularProgress color="inherit"/>
        </Centered>
    }

    return <Grid container spacing={3}>
        <Grid item>
            <ServersDashboard state={dashboardState}/>
        </Grid>
        <Grid item>
            <ComponentsDashboard state={dashboardState}/>
        </Grid>
        <Grid item>
            <LogsDashboard state={dashboardState}/>
        </Grid>
        <Grid item>
            <CommandsDashboard state={dashboardState}/>
        </Grid>
        <Grid item>
            <BuildsDashboard state={dashboardState}/>
        </Grid>
    </Grid>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Dashboard));