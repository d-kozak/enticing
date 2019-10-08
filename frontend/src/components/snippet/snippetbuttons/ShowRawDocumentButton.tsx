import {SearchResult} from "../../../entities/SearchResult";
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import LinkIcon from "@material-ui/core/SvgIcon/SvgIcon";
import React from "react";
import axios from "axios";
import {API_BASE_PATH} from "../../../globals";
import {consoleDump} from "../../utils/dump";

interface ShowRawDocumentButtonProps {
    searchResult: SearchResult
}

interface RawDocumentRequest {
    server: string,
    collection: string,
    documentId: number
}


async function loadRawDocument(searchResult: SearchResult) {
    const request: RawDocumentRequest = {
        server: searchResult.host,
        collection: searchResult.collection,
        documentId: searchResult.documentId
    };
    try {
        const response = await axios.post(`${API_BASE_PATH}/query/raw-document/`, request, {withCredentials: true});
        consoleDump(response.data);
    } catch (e) {
        consoleDump(e);
    }
    // window.open(searchResult.url, "_blank")
}

export const ShowRawDocumentButton = ({searchResult}: ShowRawDocumentButtonProps) => <Tooltip title="Show raw document">
    <IconButton onClick={() => loadRawDocument(searchResult)}>
        <LinkIcon fontSize="small"/>
    </IconButton>
</Tooltip>;