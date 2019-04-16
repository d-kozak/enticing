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


import {AppState} from "../../../AppState";
import {connect} from "react-redux";
import ContextDialogContent from "./ContextDialogContent";
import {SearchResultContext} from "../../../entities/SearchResultContext";
import {contextDialogClosedAction} from "../../../actions/dialog/ContextDialogActions";
import Grid from "@material-ui/core/es/Grid";
import Button from "@material-ui/core/es/Button";


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
    progress: {},
    extendContextButton: {
        margin: '10px'
    }
});


export interface ContextDialogProps extends WithStyles<typeof styles> {
    context: SearchResultContext | null
    dialogClosed: () => void
}

const DialogContent = withStyles(theme => ({
    root: {
        margin: 0,
        padding: theme.spacing.unit * 2,
    },
}))(MuiDialogContent);


const ContextDialog = (props: ContextDialogProps) => {
    const {context, dialogClosed, classes} = props;

    return <Dialog
        open={context != null}
        onClose={dialogClosed}
    >
        <div className={classes.root}>
            <MuiDialogTitle disableTypography className={classes.title}>
                <Typography variant="h6">Context</Typography>
                <IconButton aria-label="Close" className={classes.closeButton} onClick={dialogClosed}>
                    <CloseIcon/>
                </IconButton>
            </MuiDialogTitle>
            {context !== null && <DialogContent>
                <ContextDialogContent context={context}/>
            </DialogContent>}
            <Grid container justify="flex-end">
                <Button variant="contained" color="primary" className={classes.extendContextButton}>Extend</Button>
            </Grid>
        </div>
    </Dialog>
};

const mapStateToProps = (state: AppState) => ({
    context: state.dialog.contextDialog.context
});

const mapDispatchToProps = {
    dialogClosed: contextDialogClosedAction
};

export default withStyles(styles, {
    withTheme: true
})(connect(mapStateToProps, mapDispatchToProps)(ContextDialog))