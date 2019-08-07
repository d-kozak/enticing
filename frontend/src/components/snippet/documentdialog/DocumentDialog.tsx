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
import {ApplicationState} from "../../../reducers/ApplicationState";
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


export type DocumentDialogProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const DialogContent = withStyles(theme => ({
    root: {
        margin: 0,
        padding: theme.spacing.unit * 2,
    },
}))(MuiDialogContent);


const DocumentDialog = (props: DocumentDialogProps) => {
    const {document, corpusFormat: corpusFormat, dialogClosed, classes} = props;

    return <Dialog
        open={document != null}
        onClose={dialogClosed}
        fullWidth={true}
        maxWidth="md"
    >
        <div className={classes.root}>
            <MuiDialogTitle disableTypography className={classes.title}>
                <Typography variant="h6">Document</Typography>
                <IconButton aria-label="Close" className={classes.closeButton} onClick={dialogClosed}>
                    <CloseIcon/>
                </IconButton>
            </MuiDialogTitle>
            {document !== null && corpusFormat !== null &&
            <DialogContent>
                <DocumentDialogContent document={document} corpusFormat={corpusFormat}/>
            </DialogContent>}
        </div>
    </Dialog>
};

const mapStateToProps = (state: ApplicationState) => ({
    document: state.dialog.documentDialog.document,
    corpusFormat: state.dialog.documentDialog.corpusFormat
});

const mapDispatchToProps = {
    dialogClosed: documentDialogClosedAction
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(DocumentDialog))