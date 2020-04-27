import React, {useState} from 'react';
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {deleteRequest} from "../../../network/requests";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {useHistory} from "react-router";
import ConfirmationDialog from "../../dialog/ConfirmationDialog";
import {ServerInfo} from "../../../entities/ServerInfo";

export type RemoveServerDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { server: ServerInfo }

const RemoveServerDialog = (props: RemoveServerDialogProps) => {
    const {openSnackbarAction, server} = props;
    const [open, setOpen] = useState(false);
    const history = useHistory();

    const removeServerRequest = () => {
        deleteRequest(`/server/${server.id}`)
            .then(() => {
                openSnackbarAction(`Request submitted`)
                history.goBack()
            })
            .catch(err => {
                console.error(err);
                openSnackbarAction("Failed remove server")
            })
            .finally(() => setOpen(false))
    }

    return <ConfirmationDialog open={open} setOpen={setOpen} title="Remove server"
                               message={`Are you sure that you want to remove server ${server.address}?`}
                               onConfirm={removeServerRequest}/>
}


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(RemoveServerDialog));