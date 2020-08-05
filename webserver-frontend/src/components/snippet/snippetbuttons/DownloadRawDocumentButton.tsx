import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import GetAppIcon from "@material-ui/icons/GetApp";
import React from "react";
import {SearchResult} from "../../../entities/SearchResult";
import {ApplicationState} from "../../../ApplicationState";
import {downloadRawDocumentRequest} from "../../../reducers/dialog/RawDocumentDialogReducer";
import {connect} from "react-redux";

type DownloadRawDocumentButtonProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    searchResult: SearchResult
}

export const DownloadRawDocumentButton = ({searchResult, downloadRawDocument}: DownloadRawDocumentButtonProps) =>
    <Tooltip
        title="Download raw mg4j document">
            <span>
                <IconButton disabled={!searchResult.payload.canExtend}
                            onClick={() => downloadRawDocument(searchResult)}
                            color="primary">
                <GetAppIcon fontSize="small"/>
            </IconButton>
            </span>
    </Tooltip>;


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    downloadRawDocument: downloadRawDocumentRequest as (searchResult: SearchResult) => void,
};

export default (connect(mapStateToProps, mapDispatchToProps)(DownloadRawDocumentButton));