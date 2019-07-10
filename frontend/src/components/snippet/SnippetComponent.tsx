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
import AnnotatedTextComponent from "../annotations/AnnotatedTextComponent";
import {Match} from "../../entities/Snippet";


const styles = createStyles({
    root: {
        margin: '0px'
    }
});


export type  SnippetComponentProps = WithStyles<typeof styles> & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    snippet: Match,
    openDocument: (searchResult: Match) => void
}

const SnippetComponent = (props: SnippetComponentProps) => {
    const {snippet, requestContextExtension, openDocument, classes} = props;

    return <div className={classes.root}>
        <EditContextButton searchResult={snippet} requestContextExtension={requestContextExtension}/>
        <EditAnnotationsButton/>
        <ShowDocumentButton searchResult={snippet} openDocument={openDocument}/>
        <GotoSourceButton searchResult={snippet}/>
        <AnnotatedTextComponent text={snippet.payload}/>
    </div>
};


const mapStateToProps = (state: AppState) => ({});

const mapDispatchToProps = {
    requestContextExtension: contextExtensionRequestAction as (searchResult: Match) => void
}


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SnippetComponent))