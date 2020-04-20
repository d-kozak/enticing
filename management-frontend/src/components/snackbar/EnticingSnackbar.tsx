import {Snackbar} from "@material-ui/core";
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {closeSnackbarAction} from "../../reducers/snackbarReducer";


export type EnticingSnackBarProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const EnticingSnackbar = (props: EnticingSnackBarProps) => {
    const {isOpen, closeSnackbarAction, message} = props;

    const handleClose = (event: React.SyntheticEvent<any>, reason: string) => {
        if (reason === 'clickaway') {
            return;
        }
        closeSnackbarAction();
    };

    return <Snackbar
        anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
        }}
        open={isOpen}
        autoHideDuration={3000}
        onClose={handleClose}
        message={message}
    />
};


const mapStateToProps = (state: ApplicationState) => {
    const {isOpen, message} = state.snackbar;
    return {
        isOpen,
        message
    }
};

const mapDispatchToProps = {
    closeSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(EnticingSnackbar));