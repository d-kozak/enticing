import React, {useState} from 'react';
import {connect} from "react-redux";
import {CommandRequest} from "../../entities/CommandDto";
import {postRequest} from "../../network/requests";
import ConfirmationDialog from "./ConfirmationDialog";
import {ApplicationState} from "../../ApplicationState";
import {openSnackbarAction} from "../../reducers/snackbarReducer"
import {useHistory} from "react-router";

export type SubmitCommandDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    title: string
    message: string
    request: CommandRequest,
    popHistory?: boolean
}

const SubmitCommandDialog = (props: SubmitCommandDialogProps) => {
    const {openSnackbarAction, popHistory, request, message, title} = props;
    const [open, setOpen] = useState(false);

    const history = useHistory();

    const onConfirm = () => {
        postRequest("/command", request)
            .then(() => {
                openSnackbarAction("Command submitted")
                if (popHistory)
                    history.goBack();
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