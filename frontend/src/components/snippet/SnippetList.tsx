import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import SearchResultItem from "./SnippetComponent";
import Typography from "@material-ui/core/es/Typography";
import Paper from "@material-ui/core/es/Paper";
import Pagination from "../pagination/Pagination";
import Divider from "@material-ui/core/es/Divider";
import DocumentDialog from "./documentdialog/DocumentDialog";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import {documentDialogRequestedAction} from "../../actions/dialog/DocumentDialogAction";
import {Match} from "../../entities/Snippet";

const styles = createStyles({
    root: {
        minWidth: '275px',
        width: '90%',
        margin: '20px auto',
        padding: '10px'
    },
    '@media (min-width:600)': {
        root: {
            width: '80%'
        }
    }
});


export type SnippetListProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    snippet: Array<Match>;
}

const SnippetList = (props: SnippetListProps) => {
    const {snippet, openDocumentDialog, classes} = props;

    const [currentPage, setCurrentPage] = useState(0);


    let pageCount = Math.floor(snippet.length / 20);
    if (snippet.length % 20 != 0) {
        pageCount += 1
    }

    return <Paper className={classes.root}>
        {snippet
            .slice(currentPage * 20, currentPage * 20 + 20)
            .map(
                (searchResult, index) => <React.Fragment key={index}>
                    {index > 0 && <Divider/>}
                    <SearchResultItem openDocument={openDocumentDialog}
                                      snippet={searchResult}/>
                </React.Fragment>)
        }
        <Typography
            variant="body1">{snippet.length > 0 ? `Total number of snippets is ${snippet.length}` : 'No snippets found'}</Typography>
        <Pagination currentPage={currentPage} setCurrentPage={setCurrentPage} pageCount={pageCount}/>

        <DocumentDialog/>
    </Paper>
};

const mapStateToProps = (state: AppState) => ({});

const mapDispatchToProps = {
    openDocumentDialog: documentDialogRequestedAction as (searchResult: Match) => void
}

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SnippetList))