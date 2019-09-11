import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import FullScreenIcon from "@material-ui/icons/Fullscreen";
import React from "react";

interface OpenDocumentButtonProps {
    openDocumentRequest: () => void
}

export const ShowDocumentButton = ({openDocumentRequest}: OpenDocumentButtonProps) => <Tooltip
    title='Show the document'>
    <IconButton onClick={openDocumentRequest}>
        <FullScreenIcon fontSize="small"/>
    </IconButton>
</Tooltip>;