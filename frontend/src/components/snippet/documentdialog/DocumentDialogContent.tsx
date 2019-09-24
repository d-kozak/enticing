import createStyles from "@material-ui/core/es/styles/createStyles";
import {Divider, List, ListItem, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {FullDocument} from "../../../entities/FullDocument";
import NewAnnotatedTextComponent from "../../annotations/TextUnitListComponent";
import {CorpusFormat} from "../../../entities/CorpusFormat";

const styles = createStyles({
    sectionTitle: {
        margin: '10px 0px'
    },
    divider: {
        margin: '10px 0px'
    }
});


export interface DialogContentProps extends WithStyles<typeof styles> {
    document: FullDocument
    corpusFormat: CorpusFormat
}

const DocumentDialogContent = (props: DialogContentProps) => {
    const {document, corpusFormat, classes} = props;

    return <div>
        <Typography variant="h6">Document Info</Typography>
        <List>
            <ListItem>
                <Typography variant="body1">Original source - <a href={document.url}>{document.url}</a></Typography>
            </ListItem>
            <ListItem>
                <Typography variant="body1">Server - {document.host}</Typography>
            </ListItem>
            <ListItem>
                <Typography variant="body1">Collection - {document.collection}</Typography>
            </ListItem>
        </List>
        <Divider className={classes.divider}/>
        <Typography className={classes.sectionTitle} variant="h6">Content</Typography>
        <NewAnnotatedTextComponent text={document.payload.parsedContent!} corpusFormat={corpusFormat}
                                   showParagraphs={true}/>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(DocumentDialogContent)