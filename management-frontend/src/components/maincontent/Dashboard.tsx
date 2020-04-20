import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../reducers/snackbarReducer";


export type DashboardProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Dashboard = (props: DashboardProps) => {
    const {} = props;

    return <div>
        dashboard
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Dashboard));