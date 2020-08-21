import React, {useState} from 'react';
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {postRequest} from "../../../network/requests";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {useHistory} from "react-router";
import ConfirmationDialog from "../../dialog/ConfirmationDialog";
import {ComponentInfo} from "../../../entities/ComponentInfo";
import {CommandRequest} from "../../../entities/CommandDto";

export type KillComponentDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { component: ComponentInfo }

const KillComponentDialog = (props: KillComponentDialogProps) => {
    const {openSnackbarAction, component} = props;
    const [open, setOpen] = useState(false);
    const history = useHistory();
    const componentDesc = `${component.type}:${component.serverAddress}:${component.port}`;

    const killComponentRequest = () => {
        const req: CommandRequest = {
            type: "KILL_COMPONENT",
            arguments: {
                id: component.id,
                serverAddress: component.serverAddress,
                port: component.port,
                type: component.type
            }
        }
        postRequest("/command", req)
            .then(() => {
                openSnackbarAction(`Request submitted`)
                history.goBack()
            })
            .catch(err => {
                console.error(err);
                openSnackbarAction("Failed remove component")
            })
            .finally(() => setOpen(false))
    }

    return <ConfirmationDialog open={open} setOpen={setOpen} title="Kill component"
                               message={`Are you sure that you want to kill component ${componentDesc}?`}
                               onConfirm={killComponentRequest}/>
}


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(KillComponentDialog));