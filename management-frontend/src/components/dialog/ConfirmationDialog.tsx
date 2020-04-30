import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

export interface ConfirmationDialogProps {
    open: boolean,
    showButton?: boolean
    setOpen: (isOpen: boolean) => void,
    title: string,
    message: string,
    onConfirm: () => void
}

const ConfirmationDialog = (props: ConfirmationDialogProps) => {
    const {open, setOpen, title, message, onConfirm} = props;
    const showButton = typeof props.showButton === "boolean" ? props.showButton : true;
    return <React.Fragment>
        {showButton && <Button variant="contained" color="primary" onClick={() => setOpen(true)}>
            {title}
        </Button>}
        <Dialog
            open={open}
            onClose={() => setOpen(false)}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    {message}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => setOpen(false)} color="primary">
                    No
                </Button>
                <Button onClick={onConfirm} color="primary" autoFocus>
                    Yes
                </Button>
            </DialogActions>
        </Dialog>
    </React.Fragment>;
}

export default ConfirmationDialog;