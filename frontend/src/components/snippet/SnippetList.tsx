import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React, {useState} from 'react';
import SearchResultItem from "./SnippetComponent";
import Paper from "@material-ui/core/es/Paper";
import Pagination from "../pagination/Pagination";
import Divider from "@material-ui/core/es/Divider";
import DocumentDialog from "./documentdialog/DocumentDialog";
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import {openDocumentDialogRequest} from "../../reducers/dialog/DocumentDialogReducer";
import {CorpusFormat} from "../../entities/CorpusFormat";
import {getMoreResults} from "../../actions/PaginationActions";
import SearchStatistics from "./SearchStatistics";

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
    & {}

const SnippetList = (props: SnippetListProps) => {
    const {snippetIds, corpusFormat, openDocumentDialog, classes, resultsPerPage, hasMore, getMoreResults} = props;

    if (!corpusFormat) {
        return <p>Corpus format not set</p>
    }

    const [currentPage, setCurrentPage] = useState(0);

    let pageCount = Math.floor(snippetIds.length / resultsPerPage);
    if (snippetIds.length % resultsPerPage != 0) {
        pageCount += 1
    }

    const setPageAndAskForMoreIfNecessary = (i: number) => {
        if (i >= pageCount - 1 && hasMore)
            getMoreResults(snippetIds.length);
        setCurrentPage(i % pageCount);
    };

    const snippetSlice = snippetIds
        .slice(currentPage * resultsPerPage, currentPage * resultsPerPage + resultsPerPage);

    return <Paper className={classes.root}>
        {snippetSlice
            .map(
                (snippetId, index) => <React.Fragment key={index}>
                    {index > 0 && <Divider/>}
                    <SearchResultItem openDocumentRequest={() => openDocumentDialog(snippetId, corpusFormat)}
                                      snippetId={snippetId} corpusFormat={corpusFormat}/>
                </React.Fragment>)
        }
        <Pagination currentPage={currentPage} setCurrentPage={setPageAndAskForMoreIfNecessary} pageCount={pageCount}
                    hasMore={hasMore}/>
        <SearchStatistics/>
        <DocumentDialog/>
    </Paper>
};

const mapStateToProps = (state: ApplicationState) => ({
    corpusFormat: state.searchResult.corpusFormat,
    snippetIds: state.searchResult.snippetIds,
    resultsPerPage: state.userState.user.userSettings.resultsPerPage,
    hasMore: state.searchResult.moreResultsAvailable
});

const mapDispatchToProps = {
    getMoreResults: getMoreResults as (currentResultSize: number) => void,
    openDocumentDialog: openDocumentDialogRequest as (searchResultId: string, corpusFormat: CorpusFormat) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SnippetList))