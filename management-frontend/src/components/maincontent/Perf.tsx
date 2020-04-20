import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../reducers/snackbarReducer";


export type PerfProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Perf = (props: PerfProps) => {
    const {} = props;

    return <div>
        perf
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Perf));