import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import LinkIcon from "@material-ui/icons/Link";
import React from "react";
import {Snippet} from "../../../entities/Snippet";

interface GotoSourceButtonProps {
    searchResult: Snippet
}

export const GotoSourceButton = ({searchResult}: GotoSourceButtonProps) => <Tooltip title='Go to the source'>
    <IconButton onClick={() => window.open(searchResult.url, "_blank")}>
        <LinkIcon fontSize="small"/>
    </IconButton>
</Tooltip>