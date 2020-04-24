import React from "react";
import {IconButton} from "@material-ui/core";
import KeyboardBackspaceIcon from '@material-ui/icons/KeyboardBackspace';
import {useHistory} from "react-router";


export const BackButton = () => {
    const history = useHistory();
    return <IconButton onClick={() => history.goBack()}>
        <KeyboardBackspaceIcon/>
    </IconButton>
}