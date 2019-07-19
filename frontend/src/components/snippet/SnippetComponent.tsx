import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import {contextExtensionRequestAction} from "../../actions/ContextActions";
import {GotoSourceButton} from "./snippetbuttons/GotoSourceButton";
import {ShowDocumentButton} from "./snippetbuttons/ShowDocumentButton";
import {EditContextButton} from "./snippetbuttons/EditContextButton";
import {EditAnnotationsButton} from "./snippetbuttons/EditAnnotationsButton";
import {Snippet} from "../../entities/Snippet";
import NewAnnotatedTextComponent from "../annotations/new/NewAnnotatedTextComponent";
import {CorpusFormat} from "../../entities/CorpusFormat";


const styles = createStyles({
    root: {
        margin: '0px'
    }
});


export type  SnippetComponentProps = WithStyles<typeof styles> & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    snippet: Snippet,
    corpusFormat: CorpusFormat,
    openDocument: (searchResult: Snippet, corpusFormat: CorpusFormat) => void
}

const SnippetComponent = (props: SnippetComponentProps) => {
    const {snippet, corpusFormat, requestContextExtension, openDocument, classes} = props;

    return <div className={classes.root}>
        <EditContextButton searchResult={snippet} requestContextExtension={requestContextExtension}/>
        <EditAnnotationsButton/>
        <ShowDocumentButton searchResult={snippet} openDocument={openDocument} corpusFormat={corpusFormat}/>
        <GotoSourceButton searchResult={snippet}/>
        <NewAnnotatedTextComponent text={snippet.payload.content} corpusFormat={corpusFormat}/>
    </div>
};


const mapStateToProps = (state: AppState) => ({});

const mapDispatchToProps = {
    requestContextExtension: contextExtensionRequestAction as (searchResult: Snippet) => void
}


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SnippetComponent))