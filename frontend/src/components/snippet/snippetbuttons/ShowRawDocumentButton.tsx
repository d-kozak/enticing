import {SearchResult} from "../../../entities/SearchResult";
import Tooltip from "@material-ui/core/Tooltip";
import React from "react";
import {Button} from "@material-ui/core";
import {connect} from "react-redux";
import {ApplicationState} from "../../../ApplicationState";
import {loadRawDocumentRequest} from "../../../reducers/dialog/RawDocumentDialogReducer";


type  ShowRawDocumentButtonProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    searchResult: SearchResult
}

const ShowRawDocumentButton = ({searchResult, loadRawDocument}: ShowRawDocumentButtonProps) => <Tooltip
    title="Show raw document">
    <Button onClick={() => loadRawDocument(searchResult)}>Raw</Button>
</Tooltip>;


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    loadRawDocument: loadRawDocumentRequest as (searchResult: SearchResult) => void,
};

export default (connect(mapStateToProps, mapDispatchToProps)(ShowRawDocumentButton));