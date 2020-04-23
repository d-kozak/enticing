import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React, {useState} from 'react';
import {closeSnackbarAction} from "../../../reducers/snackbarReducer";
import {Paper, Tab, Tabs, Typography} from "@material-ui/core";
import OperationsTable from "./OperationsTable";
import PerfTable from "./PerfTable";


export type PerfProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Perf = (props: PerfProps) => {
    const {} = props;

    const [tab, setTab] = useState(0);

    return <Paper>
        <Typography variant="h3">Performance</Typography>
        <Tabs
            value={tab}
            onChange={(e, v) => setTab(v)}
            indicatorColor="primary"
            textColor="primary"
            // centered
        >
            <Tab label="Operations"/>
            <Tab label="All logs"/>
        </Tabs>
        {tab === 0 && <OperationsTable/>}
        {tab === 1 && <PerfTable/>}
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Perf));