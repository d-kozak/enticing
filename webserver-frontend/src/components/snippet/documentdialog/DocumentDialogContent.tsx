import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {FullDocument} from "../../../entities/FullDocument";
import NewAnnotatedTextComponent from "../../annotations/TextUnitListComponent";
import {CorpusFormat} from "../../../entities/CorpusFormat";
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {getSelectedMetadataForCurrentSettings, isDebugMode} from "../../../reducers/selectors";
import DocumentDebugInfo from "../DocumentDebugInfoComponent";

const styles = createStyles({
});

export type  DialogContentProps = WithStyles<typeof styles> & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    document: FullDocument
    corpusFormat: CorpusFormat
}

const DocumentDialogContent = (props: DialogContentProps) => {
    const {document, corpusFormat, query, metadata, debug, classes} = props;

    return <div>
        {debug && <DocumentDebugInfo document={document}/>}
        <NewAnnotatedTextComponent text={document.payload.content} corpusFormat={corpusFormat}
                                   metadata={metadata}
                                   query={query}
                                   showParagraphs={true}/>
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    debug: isDebugMode(state),
    query: state.searchResult.query,
    metadata: getSelectedMetadataForCurrentSettings(state)
});

const mapDispatchToProps = {};


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(DocumentDialogContent))