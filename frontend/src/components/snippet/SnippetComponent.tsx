import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import {contextExtensionRequestAction} from "../../actions/ContextActions";
import {GotoSourceButton} from "./snippetbuttons/GotoSourceButton";
import {ShowDocumentButton} from "./snippetbuttons/ShowDocumentButton";
import {EditContextButton} from "./snippetbuttons/EditContextButton";
import {EditAnnotationsButton} from "./snippetbuttons/EditAnnotationsButton";
import {SearchResult} from "../../entities/SearchResult";
import NewAnnotatedTextComponent from "../annotations/TextUnitListComponent";
import {CorpusFormat} from "../../entities/CorpusFormat";
import Grid from "@material-ui/core/es/Grid";
import {parseSearchResultRequest} from "../../reducers/SearchResultReducer";


const styles = createStyles({
    root: {
        margin: '0px'
    }
});


export type  SnippetComponentProps = WithStyles<typeof styles> & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    snippet: SearchResult,
    corpusFormat: CorpusFormat,
    openDocumentRequest: () => void
}

const SnippetComponent = (props: SnippetComponentProps) => {
    const {snippet, corpusFormat, parseSearchResult, requestContextExtension, openDocumentRequest, classes} = props;

    if (!snippet.payload.parsedContent) {
        parseSearchResult(snippet);
        return <p>...parsing data...</p>
    }

    return <Grid container justify="center" direction="column" className={classes.root}>
        <Grid item>
            <Grid container direction="row" justify="space-between" alignItems="center" className={classes.root}>
                <Grid item>
                    <EditContextButton searchResult={snippet} requestContextExtension={requestContextExtension}/>
                    <EditAnnotationsButton/>
                    <ShowDocumentButton openDocumentRequest={openDocumentRequest}/>
                    <GotoSourceButton searchResult={snippet}/>
                </Grid>
                <Grid item>
                    <b>{snippet.documentTitle}</b>
                    <span> from '{snippet.host}'</span>
                </Grid>
            </Grid>
        </Grid>
        <Grid item>
            <NewAnnotatedTextComponent text={snippet.payload.parsedContent} corpusFormat={corpusFormat}/>
        </Grid>
    </Grid>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    requestContextExtension: contextExtensionRequestAction as (searchResult: SearchResult) => void,
    parseSearchResult: parseSearchResultRequest as (searchResult: SearchResult) => void
}


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SnippetComponent))