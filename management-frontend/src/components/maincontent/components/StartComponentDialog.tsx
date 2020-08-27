import React from 'react';
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {ComponentInfo} from "../../../entities/ComponentInfo";
import {CommandRequest} from "../../../entities/CommandDto";
import SubmitCommandDialog from "../../dialog/SubmitCommandDialog";

export type StartComponentDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { component: ComponentInfo }

const StartComponentDialog = ({component}: StartComponentDialogProps) => {
    const request: CommandRequest = {
        type: "START_COMPONENT",
        arguments: {
            id: component.id,
            serverAddress: component.serverAddress,
            port: component.port,
            type: component.type,
            buildId: component.buildId
        }
    }
    const componentDesc = `${component.type}:${component.serverAddress}:${component.port}`;

    return <SubmitCommandDialog title="Start component"
                                message={`Are you sure that you want to start component ${componentDesc}?`}
                                request={request}/>
}


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(StartComponentDialog));