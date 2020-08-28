import React from 'react';
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {ComponentInfo} from "../../../entities/ComponentInfo";
import {CommandRequest} from "../../../entities/CommandDto";
import SubmitCommandDialog from "../../dialog/SubmitCommandDialog";

export type RemoveComponentDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { component: ComponentInfo }

const RemoveComponentDialog = ({component}: RemoveComponentDialogProps) => {
    const request: CommandRequest = {
        type: "REMOVE_COMPONENT",
        arguments: {
            id: component.id,
            serverAddress: component.serverAddress,
            port: component.port,
            type: component.type,
            buildId: component.buildId
        }
    }
    const componentDesc = `${component.type}:${component.serverAddress}:${component.port}`;

    return <SubmitCommandDialog title="Remove component"
                                message={`Are you sure that you want to remove component ${componentDesc}?`}
                                request={request}/>
}


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(RemoveComponentDialog));