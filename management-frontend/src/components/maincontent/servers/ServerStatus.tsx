import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {CartesianGrid, Line, LineChart, Tooltip, XAxis, YAxis} from 'recharts';

export type ServerStatusProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const ServerStatus = (props: ServerStatusProps) => {
    const {} = props;

    const data = [{name: 'Page A', uv: 400, pv: 2400, amt: 2400}, {
        name: 'Page B',
        uv: 200,
        pv: 2300,
        amt: 2400
    }, {name: 'Page C', uv: 700, pv: 2300, amt: 2400}];

    return <div>
        <LineChart width={600} height={300} data={data}>
            <Line type="monotone" dataKey="uv" stroke="#8884d8"/>
            <CartesianGrid stroke="#ccc"/>
            <XAxis dataKey="name"/>
            <YAxis/>
            <Line type="monotone" dataKey="pv" stroke="#8884d8"/>
            <Line type="monotone" dataKey="uv" stroke="#82ca9d"/>
            <Tooltip/>
        </LineChart>

    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(ServerStatus));



