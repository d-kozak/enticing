import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {FullDocument} from "../../../entities/FullDocument";
import NewAnnotatedTextComponent from "../../annotations/TextUnitListComponent";
import {CorpusFormat} from "../../../entities/CorpusFormat";

const styles = createStyles({
    titleUrl: {
        margin: "10px 0px"
    }
});


export interface DialogContentProps extends WithStyles<typeof styles> {
    document: FullDocument
    corpusFormat: CorpusFormat
}

const DocumentDialogContent = (props: DialogContentProps) => {
    const {document, corpusFormat, classes} = props;

    return <div>
        <Typography className={classes.titleUrl} variant="headline"><a
            href={document.url}>{document.url}</a></Typography>
        <NewAnnotatedTextComponent text={document.payload.content} corpusFormat={corpusFormat}/>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(DocumentDialogContent)