import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../reducers/snackbarReducer";


export type CommandsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Commands = (props: CommandsProps) => {
    const {} = props;

    return <div>
        commands
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Commands));