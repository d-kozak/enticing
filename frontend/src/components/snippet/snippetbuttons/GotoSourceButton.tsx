import {Snippet} from "../../../entities/Snippet";
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import LinkIcon from "@material-ui/icons/Link";
import React from "react";

interface GotoSourceButtonProps {
    searchResult: Snippet
}

export const GotoSourceButton = ({searchResult}: GotoSourceButtonProps) => <Tooltip title='Go to the source'>
    <IconButton onClick={() => document.location.href = searchResult.url}>
        <LinkIcon fontSize="small"/>
    </IconButton>
</Tooltip>