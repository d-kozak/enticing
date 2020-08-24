import React, {useState} from 'react';
import {connect} from "react-redux";
import {CommandRequest} from "../../entities/CommandDto";
import {postRequest} from "../../network/requests";
import ConfirmationDialog from "./ConfirmationDialog";
import {ApplicationState} from "../../ApplicationState";
import {openSnackbarAction} from "../../reducers/snackbarReducer"

export type SubmitCommandDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    title: string
    message: string
    request: CommandRequest
}

const SubmitCommandDialog = (props: SubmitCommandDialogProps) => {
    const {openSnackbarAction, request, message, title} = props;
    const [open, setOpen] = useState(false);

    const onConfirm = () => {
        postRequest("/command", request)
            .then(() => {
                openSnackbarAction("Command submitted")
            })
            .catch(err => {
                console.error(err);
                openSnackbarAction("Failed submit command")
            })
            .finally(() => setOpen(false))
    }

    return <ConfirmationDialog open={open} setOpen={setOpen} title={title}
                               message={message}
                               onConfirm={onConfirm}/>
}


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(SubmitCommandDialog));