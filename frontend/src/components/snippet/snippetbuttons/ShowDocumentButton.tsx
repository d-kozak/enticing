import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import FullScreenIcon from "@material-ui/icons/Fullscreen";
import React from "react";
import {Snippet} from "../../../entities/Snippet";
import {CorpusFormat} from "../../../entities/CorpusFormat";

interface OpenDocumentButtonProps {
    searchResult: Snippet,
    corpusFormat: CorpusFormat
    openDocument: (searchResult: Snippet, corpusFormat: CorpusFormat) => void
}

export const ShowDocumentButton = ({openDocument, searchResult, corpusFormat}: OpenDocumentButtonProps) => <Tooltip
    title='Show the document'>
    <IconButton onClick={() => openDocument(searchResult, corpusFormat)}>
        <FullScreenIcon fontSize="small"/>
    </IconButton>
</Tooltip>