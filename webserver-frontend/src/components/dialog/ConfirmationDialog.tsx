import createStyles from "@material-ui/core/es/styles/createStyles";
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Theme,
    WithStyles
} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import React from 'react';


const styles = (theme: Theme) => createStyles({});

type ConfirmationDialogProps = WithStyles<typeof styles> & {
    open: boolean
    title?: string
    text?: string
    onConfirm: () => void
    onClose?: () => void
}

const ConfirmationDialog = (props: ConfirmationDialogProps) => {
    const {open, title = "", text = "", onConfirm, onClose} = props;
    return <Dialog
        open={open}
        onClose={onClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
    >
        <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
        <DialogContent>
            <DialogContentText id="alert-dialog-description">
                {text}
            </DialogContentText>
        </DialogContent>
        <DialogActions>
            <Button onClick={onClose} color="primary">
                Cancel
            </Button>
            <Button onClick={onConfirm} color="primary" autoFocus>
                Confirm
            </Button>
        </DialogActions>
    </Dialog>
};


export default withStyles(styles, {withTheme: true})(ConfirmationDialog);