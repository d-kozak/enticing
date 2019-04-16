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
import SearchResultWholeDocumentDialog from "./wholedocumentdialog/SearchResultWholeDocumentDialog";
import {AppState} from "../../AppState";
import {hideProgressBarAction, showProgressBarAction} from "../../actions/ProgressBarActions";
import {connect} from "react-redux";

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


export interface SearchResultListProps extends WithStyles<typeof styles> {
    searchResults: Array<SearchResult>;

    showProgress: () => void;
    hideProgress: () => void;
}

const SearchResultList = (props: SearchResultListProps) => {
    const {searchResults, classes, showProgress, hideProgress} = props;

    const [currentPage, setCurrentPage] = useState(0);

    const [selectedSearchItem, setSelectedSearchItem] = useState<SearchResult | null>(null);

    const openWholeDocument = (searchResult: SearchResult) => {
        showProgress();
        setTimeout(() => {
            hideProgress();
            setSelectedSearchItem(searchResult);
        }, 2000);
    };

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
                    <SearchResultItem openWholeDocument={openWholeDocument} searchResult={searchResult}/>
                </React.Fragment>)
        }
        <Typography
            variant="body1">{searchResults.length > 0 ? `Total number of snippets is ${searchResults.length}` : 'No snippets found'}</Typography>
        <Pagination currentPage={currentPage} setCurrentPage={setCurrentPage} pageCount={pageCount}/>

        <SearchResultWholeDocumentDialog searchResult={selectedSearchItem}
                                         dialogClosed={() => setSelectedSearchItem(null)}/>

    </Paper>
};

const mapStateToProps = (state: AppState) => ({});

const mapDispatchToProps = {
    showProgress: showProgressBarAction,
    hideProgress: hideProgressBarAction
}

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchResultList))