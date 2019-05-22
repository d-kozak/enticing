import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {SearchResult} from "../../entities/SearchResult";
import {enrichText} from "../annotations/applyAnnotations";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import {contextExtensionRequestAction} from "../../actions/ContextActions";
import {GotoSourceButton} from "./searchresultitembuttons/GotoSourceButton";
import {ShowDocumentButton} from "./searchresultitembuttons/ShowDocumentButton";
import {EditContextButton} from "./searchresultitembuttons/EditContextButton";
import {EditAnnotationsButton} from "./searchresultitembuttons/EditAnnotationsButton";


const styles = createStyles({
    root: {
        margin: '0px'
    }
});


export type  SearchResultItemProps = WithStyles<typeof styles> & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    searchResult: SearchResult,
    openDocument: (searchResult: SearchResult) => void
}

const SearchResultItem = (props: SearchResultItemProps) => {
    const {searchResult, requestContextExtension, openDocument, classes} = props;

    return <div className={classes.root}>
        <EditContextButton searchResult={searchResult} requestContextExtension={requestContextExtension}/>
        <EditAnnotationsButton/>
        <ShowDocumentButton searchResult={searchResult} openDocument={openDocument}/>
        <GotoSourceButton searchResult={searchResult}/>

        {enrichText(searchResult.snippet)}
    </div>
};


const mapStateToProps = (state: AppState) => ({});

const mapDispatchToProps = {
    requestContextExtension: contextExtensionRequestAction as (searchResult: SearchResult) => void
}


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchResultItem))