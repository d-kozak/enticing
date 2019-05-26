import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {IndexedDocument} from "../../../entities/IndexedDocument";
import AnnotatedTextComponent from "../../annotations/AnnotatedTextComponent";

const styles = createStyles({
    titleUrl: {
        margin: "10px 0px"
    }
});


export interface DialogContentProps extends WithStyles<typeof styles> {
    document: IndexedDocument
}

const DocumentDialogContent = (props: DialogContentProps) => {
    const {document, classes} = props;

    return <div>
        <Typography className={classes.titleUrl} variant="headline"><a
            href={document.url}>{document.url}</a></Typography>
        <AnnotatedTextComponent text={document.body}/>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(DocumentDialogContent)