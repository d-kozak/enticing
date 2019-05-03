import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {SearchResult} from "../../entities/SearchResult";
import {applyAnnotations} from "../annotations/applyAnnotations";
import Tooltip from "@material-ui/core/Tooltip";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import {contextExtensionRequestAction} from "../../actions/ContextActions";
import IconButton from '@material-ui/core/IconButton';
import LinkIcon from '@material-ui/icons/Link'
import EditIcon from '@material-ui/icons/Edit';
import AddIcon from '@material-ui/icons/Add';
import FullScreenIcon from '@material-ui/icons/Fullscreen'


const styles = createStyles({
    root: {
        margin: '10px 0px'
    }
});


export type  SearchResultItemProps = WithStyles<typeof styles> & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    searchResult: SearchResult,
    openWholeDocument: (searchResult: SearchResult) => void
}

const SearchResultItem = (props: SearchResultItemProps) => {
    const {searchResult, requestContextExtension, openWholeDocument, classes} = props;

    return <div className={classes.root}>
        <Tooltip title={searchResult.canExtend ? 'Extend the context' : 'Full length reached'}>
            <span>
                <IconButton disabled={!searchResult.canExtend}
                            onClick={() => requestContextExtension(searchResult)}
                            color="primary">
                <AddIcon fontSize="small"/>
            </IconButton>
            </span>
        </Tooltip>
        <Tooltip title='Edit annotations'>
            <IconButton onClick={() => alert('Not implemented yet')}>
                <EditIcon fontSize="small"/>
            </IconButton>
        </Tooltip>
        <Tooltip title='Show whole document'>
            <IconButton onClick={() => openWholeDocument(searchResult)}>
                <FullScreenIcon fontSize="small"/>
            </IconButton>
        </Tooltip>
        <Tooltip title='Go to the source'>
            <IconButton onClick={() => document.location.href = searchResult.url}>
                <LinkIcon fontSize="small"/>
            </IconButton>
        </Tooltip>

        {applyAnnotations(searchResult.snippet)}
    </div>
};


const mapStateToProps = (state: AppState) => ({});

const mapDispatchToProps = {
    requestContextExtension: contextExtensionRequestAction as (searchResult: SearchResult) => void
}


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchResultItem))