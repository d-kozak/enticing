import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import FullScreenIcon from "@material-ui/icons/Fullscreen";
import React from "react";
import {SearchResult} from "../../../entities/SearchResult";
import {CorpusFormat} from "../../../entities/CorpusFormat";

interface OpenDocumentButtonProps {
    searchResult: SearchResult,
    corpusFormat: CorpusFormat
    openDocument: (searchResult: SearchResult, corpusFormat: CorpusFormat) => void
}

export const ShowDocumentButton = ({openDocument, searchResult, corpusFormat}: OpenDocumentButtonProps) => <Tooltip
    title='Show the document'>
    <IconButton onClick={() => openDocument(searchResult, corpusFormat)}>
        <FullScreenIcon fontSize="small"/>
    </IconButton>
</Tooltip>