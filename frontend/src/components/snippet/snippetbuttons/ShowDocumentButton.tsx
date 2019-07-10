import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import FullScreenIcon from "@material-ui/icons/Fullscreen";
import React from "react";
import {Match} from "../../../entities/Snippet";

interface OpenDocumentButtonProps {
    searchResult: Match,
    openDocument: (searchResult: Match) => void
}

export const ShowDocumentButton = ({openDocument, searchResult}: OpenDocumentButtonProps) => <Tooltip
    title='Show the document'>
    <IconButton onClick={() => openDocument(searchResult)}>
        <FullScreenIcon fontSize="small"/>
    </IconButton>
</Tooltip>