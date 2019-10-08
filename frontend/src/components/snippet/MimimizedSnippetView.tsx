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
import Grid from "@material-ui/core/es/Grid";
import {parseSearchResultRequest} from "../../reducers/SearchResultReducer";
import {getSelectedMetadataForCurrentSettings, isDebugMode} from "../../reducers/selectors";
import {ShowRawDocumentButton} from "./snippetbuttons/ShowRawDocumentButton";


const styles = createStyles({
    root: {
        margin: '0px'
    },
    text: {
        flex: '1',
        margin: '5px 0px'
    }
});

export interface MinimizedSnippetViewProps {
    snippetId: string,
    openDocumentRequest: () => void
}

export type  MinimizedSnippetViewEnhancedProps = WithStyles<typeof styles> & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & MinimizedSnippetViewProps

const MinimizedSnippetView = (props: MinimizedSnippetViewEnhancedProps) => {
    const {snippet, snippetId, corpusFormat, metadata, debugMode, parseSearchResult, requestContextExtension, openDocumentRequest, classes} = props;
    if (!snippet) {
        return <p>snippet with id {snippetId} not found</p>
    }
    if (!snippet.payload.parsedContent) {
        parseSearchResult(snippet);
        return <p>...parsing data...</p>
    }
    if (!corpusFormat) {
        return <p>Corpus format not loaded</p>;
    }

    return <Grid container direction="row" justify="flex-start" alignItems="center" className={classes.root}>
        {debugMode && <ShowRawDocumentButton searchResult={snippet}/>}
        <EditContextButton searchResult={snippet} requestContextExtension={requestContextExtension}/>
        <EditAnnotationsButton/>
        <ShowDocumentButton openDocumentRequest={openDocumentRequest}/>
        <GotoSourceButton searchResult={snippet}/>
        <Grid className={classes.text}>
            <NewAnnotatedTextComponent text={snippet.payload.parsedContent} corpusFormat={corpusFormat}
                                       metadata={metadata}
                                       showParagraphs={false}/>
        </Grid>
    </Grid>
};


const mapStateToProps = (state: ApplicationState, props: MinimizedSnippetViewProps) => ({
    debugMode: isDebugMode(state),
    metadata: getSelectedMetadataForCurrentSettings(state),
    snippet: state.searchResult.snippetsById[props.snippetId],
    corpusFormat: state.searchResult.corpusFormat
});

const mapDispatchToProps = {
    requestContextExtension: contextExtensionRequestAction as (searchResult: SearchResult) => void,
    parseSearchResult: parseSearchResultRequest as (searchResult: SearchResult) => void
};


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(MinimizedSnippetView))