import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {SearchResult} from "../../entities/SearchResult";
import Grid from "@material-ui/core/es/Grid";
import Button from "@material-ui/core/es/Button";

import {unstable_useMediaQuery as useMediaQuery} from '@material-ui/core/useMediaQuery';
import {applyAnnotations} from "../annotations/applyAnnotations";
import Tooltip from "@material-ui/core/Tooltip";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import {contextExtensionRequestAction} from "../../actions/ContextActions";


const styles = createStyles({
    root: {
        margin: '10px 0px'
    },
    showContextBtnGrid: {
        flex: 1
    }
});


export type  SearchResultItemProps = WithStyles<typeof styles> & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps> & {
    searchResult: SearchResult,
    openWholeDocument: (searchResult: SearchResult) => void
}

const SearchResultItem = (props: SearchResultItemProps) => {
    const {searchResult, requestContextExtension, openWholeDocument, classes} = props;

    const isScreenMedium = useMediaQuery('(min-width:500px)');

    const ShowContextButton = () => <React.Fragment>
        {searchResult.canExtend ? <Button
            onClick={() => requestContextExtension(searchResult)}
            color="primary"
            size="small">Context</Button> : <Tooltip title="Full length reached">
            <span>
                <Button
                    disabled={true}
                    color="primary"
                    size="small">Context
                </Button>
            </span>
        </Tooltip>}

    </React.Fragment>

    return <div className={classes.root}>
        {applyAnnotations(searchResult.snippet)}
        <Grid container justify="flex-end" alignContent="center">

            {isScreenMedium ? <ShowContextButton/> : <Grid container>
                <ShowContextButton/>
            </Grid>}

            <Button onClick={() => openWholeDocument(searchResult)} color="primary" size="small">Document</Button>

            <Button size="small">Annotations</Button>

            <Button size="small" onClick={() => document.location.href = searchResult.url}>Link</Button>

        </Grid>
    </div>
};


const mapStateToProps = (state: AppState) => ({});

const mapDispatchToProps = {
    requestContextExtension: contextExtensionRequestAction as (searchResult: SearchResult) => void
}


export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(SearchResultItem))