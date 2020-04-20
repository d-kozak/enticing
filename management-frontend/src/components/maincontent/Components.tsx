import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../reducers/snackbarReducer";


export type ComponentsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const Components = (props: ComponentsProps) => {
    const {} = props;

    return <div>
        components
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(Components));