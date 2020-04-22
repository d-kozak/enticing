import {makeStyles} from "@material-ui/core/styles";
import React from "react";
import {IconButton} from "@material-ui/core";
import KeyboardBackspaceIcon from '@material-ui/icons/KeyboardBackspace';
import {useHistory} from "react-router";

const useStyles = makeStyles({});

export const BackButton = () => {
    const classes = useStyles();
    const history = useHistory();

    return <IconButton onClick={() => history.goBack()}>
        <KeyboardBackspaceIcon/>
    </IconButton>
}