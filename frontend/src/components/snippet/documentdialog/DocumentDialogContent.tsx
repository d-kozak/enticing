import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {FullDocument} from "../../../entities/FullDocument";
import NewAnnotatedTextComponent from "../../annotations/new/NewAnnotatedTextComponent";

const styles = createStyles({
    titleUrl: {
        margin: "10px 0px"
    }
});


export interface DialogContentProps extends WithStyles<typeof styles> {
    document: FullDocument
}

const DocumentDialogContent = (props: DialogContentProps) => {
    const {document, classes} = props;

    return <div>
        <Typography className={classes.titleUrl} variant="headline"><a
            href={document.url}>{document.url}</a></Typography>
        <NewAnnotatedTextComponent text={document.payload.content}/>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(DocumentDialogContent)