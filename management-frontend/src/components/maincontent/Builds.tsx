import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../reducers/snackbarReducer";


export type BuildsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Builds = (props: BuildsProps) => {
    const {} = props;

    return <div>
        builds
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Builds));