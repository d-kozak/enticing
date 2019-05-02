import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import {SearchResult} from "../../entities/SearchResult";
import SearchResultItem from "./SearchResultItem";
import Typography from "@material-ui/core/es/Typography";
import Paper from "@material-ui/core/es/Paper";
import Pagination from "../pagination/Pagination";
import Divider from "@material-ui/core/es/Divider";
import DocumentDialog from "./documentdialog/DocumentDialog";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import {documentDialogRequestedAction} from "../../actions/dialog/DocumentDialogAction";
import {contextDialogRequestedAction} from "../../actions/dialog/ContextDialogActions";
import ContextDialog from "./contextdialog/ContextDialog";

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


export type SearchResultListProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    searchResults: Array<SearchResult>;
}

const SearchResultList = (props: SearchResultListProps) => {
    const {searchResults, openContextDialog, openDocumentDialog, classes} = props;

    const [currentPage, setCurrentPage] = useState(0);


    let pageCount = Math.floor(searchResults.length / 20);
    if (searchResults.length % 20 != 0) {
        pageCount += 1
    }

    return <Paper className={classes.root}>
        {searchResults
            .slice(currentPage * 20, currentPage * 20 + 20)
            .map(
                (searchResult, index) => <React.Fragment key={index}>
                    {index > 0 && <Divider/>}
                    <SearchResultItem openWholeDocument={openDocumentDialog} openContextDialog={openContextDialog}
                                      searchResult={searchResult}/>
                </React.Fragment>)
        }
        <Typography
            variant="body1">{searchResults.length > 0 ? `Total number of snippets is ${searchResults.length}` : 'No snippets found'}</Typography>
        <Pagination currentPage={currentPage} setCurrentPage={setCurrentPage} pageCount={pageCount}/>

        <DocumentDialog/>
        <ContextDialog/>
    </Paper>
};

const mapStateToProps = (state: AppState) => ({});

const mapDispatchToProps = {
    openDocumentDialog: documentDialogRequestedAction as (searchResult: SearchResult) => void,
    openContextDialog: contextDialogRequestedAction as (searchResult: SearchResult) => void
}

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchResultList))