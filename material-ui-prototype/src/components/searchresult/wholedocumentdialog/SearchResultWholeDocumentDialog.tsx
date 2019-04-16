import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import MuiDialogContent from '@material-ui/core/DialogContent';
import {SearchResult} from "../../../entities/SearchResult";
import Dialog from "@material-ui/core/es/Dialog";
import Typography from "@material-ui/core/es/Typography";
import IconButton from "@material-ui/core/es/IconButton";
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import CloseIcon from '@material-ui/icons/Close';
import {Theme} from "@material-ui/core/es";


import SearchResultWholeDocument from './SearchResultWholeDocument';


const styles = (theme: Theme) => createStyles({
    title: {
        borderBottom: `1px solid ${theme.palette.divider}`,
        margin: 0,
        padding: theme.spacing.unit * 2,
    },
    closeButton: {
        position: 'absolute',
        right: theme.spacing.unit,
        top: theme.spacing.unit,
        color: theme.palette.grey[500],
    },
    root: {
        minWidth: '250px',
        minHeight: '500px'
    },
    progress: {}
});


export interface SearchResultDetailsDialogProps extends WithStyles<typeof styles> {
    searchResult: SearchResult | null
    dialogClosed: () => void
}

const DialogContent = withStyles(theme => ({
    root: {
        margin: 0,
        padding: theme.spacing.unit * 2,
    },
}))(MuiDialogContent);


const SearchResultWholeDocumentDialog = (props: SearchResultDetailsDialogProps) => {
    const {searchResult, dialogClosed, classes} = props;

    return <Dialog
        open={searchResult != null}
        onClose={dialogClosed}
    >
        <div className={classes.root}>
            <MuiDialogTitle disableTypography className={classes.title}>
                <Typography variant="h6">Whole document</Typography>
                <IconButton aria-label="Close" className={classes.closeButton} onClick={dialogClosed}>
                    <CloseIcon/>
                </IconButton>
            </MuiDialogTitle>
            {searchResult !== null && <DialogContent>
                <SearchResultWholeDocument searchResult={searchResult}/>
            </DialogContent>}
        </div>
    </Dialog>
};

export default withStyles(styles, {
    withTheme: true
})(SearchResultWholeDocumentDialog)