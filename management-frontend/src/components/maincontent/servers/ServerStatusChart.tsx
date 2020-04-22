import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React, {useEffect, useState} from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {CartesianGrid, Line, LineChart, Tooltip, XAxis, YAxis} from 'recharts';
import {ServerStatus} from "../../../entities/ServerInfo";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {Grid} from "@material-ui/core";

type ServerStatusChartSimpleProps = {
    serverId: string
}

export type ServerStatusChartProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & ServerStatusChartSimpleProps

const ServerStatusChart = (props: ServerStatusChartProps) => {
    const {server} = props;

    const [data, setData] = useState<Array<ServerStatus>>()

    const refresh = () => {
        getRequest<PaginatedResult<ServerStatus>>(`/server/${server.id}/stats`, [["page", 0], ["size", 100]])
            .then(res => setData(res.content))
            .catch(err => console.error(err))
    };

    useEffect(() => {
        refresh()
        const interval = setInterval(refresh, 3000)
        return () => clearInterval(interval)
    }, [server.id])

    if (!server) {
        return <div>
            No server data...
        </div>
    }

    return <Grid container justify="space-around">
        <Grid item>
            <LineChart width={600} height={300} data={data}>
                <CartesianGrid stroke="#ccc"/>
                <XAxis dataKey="timestamp"/>
                <YAxis/>
                <Line type="monotone" dataKey="processCpuLoad" stroke="#82ca9d"/>
                <Line type="monotone" dataKey="systemCpuLoad" stroke="#82c99d"/>
                <Tooltip/>
            </LineChart>
        </Grid>
        <Grid item>
            <LineChart width={600} height={300} data={data}>
                <CartesianGrid stroke="#ccc"/>
                <XAxis dataKey="timestamp"/>
                <YAxis/>
                <Line type="monotone" dataKey="freePhysicalMemorySize" stroke="#8884d8"/>
                <Tooltip/>
            </LineChart>
        </Grid>
    </Grid>
};


const mapStateToProps = (state: ApplicationState, withId: ServerStatusChartSimpleProps) => ({
    server: state.servers.elements[withId.serverId]
});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(ServerStatusChart));



