import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import EditIcon from "@material-ui/icons/Edit";
import React from "react";

export const EditAnnotationsButton = () => <Tooltip title='Edit annotations'>
    <IconButton onClick={() => alert('Not implemented yet')}>
        <EditIcon fontSize="small"/>
    </IconButton>
</Tooltip>