import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../reducers/snackbarReducer";


export type ServersProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Servers = (props: ServersProps) => {
    const {} = props;

    return <div>
        servers
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Servers));