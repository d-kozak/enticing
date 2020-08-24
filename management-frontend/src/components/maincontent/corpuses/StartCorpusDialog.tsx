import React from 'react';
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {CommandRequest} from "../../../entities/CommandDto";
import {Corpus} from "../../../entities/Corpus";
import SubmitCommandDialog from "../../dialog/SubmitCommandDialog";

export type StartCorpusDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & { corpus: Corpus }

const StartCorpusDialog = ({corpus}: StartCorpusDialogProps) => {
    const request: CommandRequest = {
        type: "START_CORPUS",
        arguments: corpus.id
    }
    return <SubmitCommandDialog title="Start corpus"
                                message={`Are you sure that you want to start corpus ${corpus.name}?`}
                                request={request}/>
}


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(StartCorpusDialog));