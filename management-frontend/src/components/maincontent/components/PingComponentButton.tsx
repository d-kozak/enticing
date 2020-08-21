import {ComponentInfo} from "../../../entities/ComponentInfo";
import {IconButton, Tooltip} from "@material-ui/core";
import WarningIcon from "@material-ui/icons/Warning";
import React, {useState} from "react";
import {getRequest, postRequest} from "../../../network/requests";
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import ConfirmationDialog from "../../dialog/ConfirmationDialog";
import {CommandDto, CommandRequest} from "../../../entities/CommandDto";


export type PingComponentButtonProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    component: ComponentInfo
}

const PingComponentButton = ({component, openSnackbarAction}: PingComponentButtonProps) => {
    const [isRebootComponentDialogOpen, setIsRebootComponentDialogOpen] = useState(false);

    const pingComponent = () => {
        getRequest(`/server/${component.serverId}/component/${component.id}/ping`)
            .then(res => {
                if (res === "") setIsRebootComponentDialogOpen(true);
                else openSnackbarAction("Component is alive");
            })
            .catch(err => {
                openSnackbarAction("Internal error when contacting the component, this does NOT mean that it is dead");
                console.log("error");
            })
    };

    const rebootComponent = () => {

        const req: CommandRequest = {
            type: "START_COMPONENT",
            arguments: {
                id: component.id,
                port: component.port,
                serverAddress: component.serverAddress,
                type: component.type
            }
        };
        postRequest<CommandDto>("/command", req)
            .then(dto => {
                openSnackbarAction("Command submitted");
            })
            .catch(err => {
                console.error(err);
                openSnackbarAction("Failed to submit command");
            })
            .finally(() => setIsRebootComponentDialogOpen(false))
    };

    return <React.Fragment>
        <Tooltip title="Check component">
            <IconButton color="secondary" onClick={pingComponent}>
                <WarningIcon/>
            </IconButton>
        </Tooltip>
        <ConfirmationDialog open={isRebootComponentDialogOpen} setOpen={setIsRebootComponentDialogOpen}
                            showButton={false} title="Reboot component"
                            message="Component is dead, do you want the reboot it?" onConfirm={rebootComponent}/>
    </React.Fragment>
}


const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(PingComponentButton))