import createStyles from "@material-ui/core/es/styles/createStyles";
import {DialogContent, Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {connect} from "react-redux";
import React from 'react';
import {ApplicationState} from "../../../ApplicationState";
import Dialog from "@material-ui/core/es/Dialog";
import MuiDialogTitle from "@material-ui/core/DialogTitle/DialogTitle";
import Typography from "@material-ui/core/es/Typography";
import IconButton from "@material-ui/core/es/IconButton";
import CloseIcon from '@material-ui/icons/Close';
import {closeRawDocumentDialog} from "../../../reducers/dialog/RawDocumentDialogReducer";

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

type RawDocumentDialogProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {}

const RawDocumentDialog = (props: RawDocumentDialogProps) => {
    const {info, closeDialog, classes} = props;
    return <Dialog
        open={info != null}
        onClose={closeDialog}
        fullScreen={true}
    >
        {info !== null &&
        <div className={classes.root}>
            <MuiDialogTitle disableTypography className={classes.title}>
                <Typography variant="h4"><a href={info.url}>{info.title}</a></Typography>
                <IconButton aria-label="Close" className={classes.closeButton} onClick={closeDialog}>
                    <CloseIcon/>
                </IconButton>
            </MuiDialogTitle>
            <DialogContent>
                <pre>{info.document}</pre>
            </DialogContent>
        </div>
        }
    </Dialog>
};


const mapStateToProps = (state: ApplicationState) => ({
    info: state.dialog.rawDocumentDialog.info
});
const mapDispatchToProps = {
    closeDialog: closeRawDocumentDialog as () => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(RawDocumentDialog));