import createStyles from "@material-ui/core/es/styles/createStyles";
import {Divider, List, ListItem, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {FullDocument} from "../../../entities/FullDocument";
import NewAnnotatedTextComponent from "../../annotations/TextUnitListComponent";
import {CorpusFormat} from "../../../entities/CorpusFormat";
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {getSelectedMetadataForCurrentSettings, isDebugMode} from "../../../reducers/selectors";

const styles = createStyles({
    sectionTitle: {
        margin: '10px 0px'
    },
    divider: {
        margin: '10px 0px'
    }
});

export type  DialogContentProps = WithStyles<typeof styles> & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    document: FullDocument
    corpusFormat: CorpusFormat
}

const DocumentDialogContent = (props: DialogContentProps) => {
    const {document, corpusFormat, metadata, debug, classes} = props;

    return <div>
        <List>
            {debug && <div>
                <Typography variant="h6">Document Info</Typography>
                <ListItem>
                    <Typography variant="body1">Server - {document.host}</Typography>
                </ListItem>
                <ListItem>
                    <Typography variant="body1">Collection - {document.collection}</Typography>
                </ListItem>
                <Divider className={classes.divider}/>
                <Typography className={classes.sectionTitle} variant="h6">Content</Typography>
            </div>}
        </List>
        <NewAnnotatedTextComponent text={document.payload.parsedContent!} corpusFormat={corpusFormat}
                                   metadata={metadata}
                                   showParagraphs={true}/>
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    debug: isDebugMode(state),
    metadata: getSelectedMetadataForCurrentSettings(state)
});

const mapDispatchToProps = {};


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(DocumentDialogContent))