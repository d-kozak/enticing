import React, {useState} from 'react';
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {CommandDto, CommandRequest} from "../../../entities/CommandDto";
import {postRequest} from "../../../network/requests";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {useHistory} from "react-router";
import ConfirmationDialog from "../../dialog/ConfirmationDialog";

export type StartNewBuildDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const StartNewBuildDialog = (props: StartNewBuildDialogProps) => {
    const {openSnackbarAction} = props;
    const [open, setOpen] = useState(false);
    const history = useHistory();

    const requestNewBuild = () => {
        const req: CommandRequest = {
            type: "BUILD",
            arguments: ""
        }
        postRequest<CommandDto>("/command", req)
            .then(command => history.push(`/command/${command.id}`))
            .catch(err => {
                console.error(err);
                openSnackbarAction("Failed to start new build")
            })
            .finally(() => setOpen(false))
    }

    return <ConfirmationDialog open={open} setOpen={setOpen} title="Request new build"
                               message="Are you sure that you want to request a new build."
                               onConfirm={requestNewBuild}/>
}


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(StartNewBuildDialog));