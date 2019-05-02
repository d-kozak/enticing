import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import MuiDialogContent from '@material-ui/core/DialogContent';
import Dialog from "@material-ui/core/es/Dialog";
import Typography from "@material-ui/core/es/Typography";
import IconButton from "@material-ui/core/es/IconButton";
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import CloseIcon from '@material-ui/icons/Close';
import {Theme} from "@material-ui/core/es";


import DocumentDialogContent from './DocumentDialogContent';
import {AppState} from "../../../reducers/RootReducer";
import {IndexedDocument} from "../../../entities/IndexedDocument";
import {documentDialogClosedAction} from "../../../actions/dialog/DocumentDialogAction";
import {connect} from "react-redux";


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
    root: {},
    progress: {}
});


export interface DocumentDialogProps extends WithStyles<typeof styles> {
    document: IndexedDocument | null
    dialogClosed: () => void
}

const DialogContent = withStyles(theme => ({
    root: {
        margin: 0,
        padding: theme.spacing.unit * 2,
    },
}))(MuiDialogContent);


const DocumentDialog = (props: DocumentDialogProps) => {
    const {document, dialogClosed, classes} = props;

    return <Dialog
        open={document != null}
        onClose={dialogClosed}
    >
        <div className={classes.root}>
            <MuiDialogTitle disableTypography className={classes.title}>
                <Typography variant="h6">Document</Typography>
                <IconButton aria-label="Close" className={classes.closeButton} onClick={dialogClosed}>
                    <CloseIcon/>
                </IconButton>
            </MuiDialogTitle>
            {document !== null && <DialogContent>
                <DocumentDialogContent document={document}/>
            </DialogContent>}
        </div>
    </Dialog>
};

const mapStateToProps = (state: AppState) => ({
    document: state.dialog.documentDialog.document
});

const mapDispatchToProps = {
    dialogClosed: documentDialogClosedAction
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(DocumentDialog))