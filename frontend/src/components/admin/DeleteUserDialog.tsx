import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import {User} from "../../entities/User";
import {AppState} from "../../AppState";
import {connect} from "react-redux";
import {LinearProgress} from "@material-ui/core";
import {deleteUserAction} from "../../actions/AdminActions";
import {deleteUserDialogClosedAction} from "../../actions/dialog/DeleteUserDialogActions";

interface DeleteUserDialogProps {
    user: User | null;
    showProgress: boolean
    deleteUser: (user: User) => void;
    onClose: () => void
}

const DeleteUserDialog = (props: DeleteUserDialogProps) => {
    const {user, deleteUser, onClose, showProgress} = props;
    return <div>
        <Dialog
            open={user != null}
            onClose={onClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            {showProgress && <LinearProgress/>}
            <DialogTitle id="alert-dialog-title">Delete user</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    Delete user with login {user ? user.login : ''}? All his information will be lost.
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>
                    Cancel
                </Button>
                <Button onClick={() => deleteUser(user!)} color="secondary" variant="contained" autoFocus>
                    Delete
                </Button>
            </DialogActions>
        </Dialog>
    </div>
}

const mapStateToProps = (state: AppState) => ({
    showProgress: state.dialog.deleteUserDialog.showProgress,
    user: state.dialog.deleteUserDialog.userToDelete
});
const mapDispatchToProps = {
    deleteUser: deleteUserAction,
    onClose: deleteUserDialogClosedAction
};
export default connect(mapStateToProps, mapDispatchToProps)(DeleteUserDialog);