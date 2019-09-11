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
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import {openDocumentDialogRequest} from "../../reducers/dialog/DocumentDialogReducer";
import {SearchResult} from "../../entities/SearchResult";
import {CorpusFormat} from "../../entities/CorpusFormat";
import {getMoreResults} from "../../actions/PaginationActions";

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
    allSnippets: Array<SearchResult>,
    corpusFormat: CorpusFormat
}

const SnippetList = (props: SnippetListProps) => {
    const {allSnippets, corpusFormat, openDocumentDialog, classes, resultsPerPage, hasMore, getMoreResults} = props;

    const [currentPage, setCurrentPage] = useState(0);

    let pageCount = Math.floor(allSnippets.length / resultsPerPage);
    if (allSnippets.length % resultsPerPage != 0) {
        pageCount += 1
    }

    const setPageAndAskForMoreIfNecessary = (i: number) => {
        if (i >= pageCount - 1 && hasMore)
            getMoreResults(allSnippets.length);
        setCurrentPage(i % pageCount);
    };

    const snippetSlice = allSnippets
        .slice(currentPage * resultsPerPage, currentPage * resultsPerPage + resultsPerPage);

    return <Paper className={classes.root}>
        {snippetSlice
            .map(
                (searchResult, index) => <React.Fragment key={index}>
                    {index > 0 && <Divider/>}
                    <SearchResultItem openDocumentRequest={() => openDocumentDialog(searchResult, corpusFormat)}
                                      snippet={searchResult} corpusFormat={corpusFormat}/>
                </React.Fragment>)
        }
        <Typography
            variant="body1">{allSnippets.length > 0 ? `Total number of snippets is ${allSnippets.length}` : 'No snippets found'}</Typography>
        <Pagination currentPage={currentPage} setCurrentPage={setPageAndAskForMoreIfNecessary} pageCount={pageCount}
                    hasMore={hasMore}/>

        <DocumentDialog/>
    </Paper>
};

const mapStateToProps = (state: ApplicationState) => ({
    resultsPerPage: state.userState.user.userSettings.resultsPerPage,
    hasMore: state.searchResult.moreResultsAvailable
});

const mapDispatchToProps = {
    getMoreResults: getMoreResults as (currentResultSize: number) => void,
    openDocumentDialog: openDocumentDialogRequest as (searchResult: SearchResult, corpusFormat: CorpusFormat) => void
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SnippetList))