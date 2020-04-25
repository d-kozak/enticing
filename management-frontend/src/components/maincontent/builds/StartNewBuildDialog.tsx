import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {CommandDto, CommandRequest} from "../../../entities/CommandDto";
import {postRequest} from "../../../network/requests";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {useHistory} from "react-router";

export type StartNewBuildDialogProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const StartNewBuildDialog = (props: StartNewBuildDialogProps) => {
    const [open, setOpen] = useState(false);
    const history = useHistory();

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const requestNewBuild = () => {
        const req: CommandRequest = {
            type: "LOCAL_TEST",
            arguments: ""
        }
        postRequest<CommandDto>("/command", req)
            .then(command => history.push(`/command/${command.id}`))
            .catch(err => console.error(err))
            .finally(handleClose)
    }

    return (
        <div>
            <Button variant="contained" color="primary" onClick={handleClickOpen}>
                Request new build
            </Button>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">New build request</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Are you sure that you want to request a new build.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        No
                    </Button>
                    <Button onClick={requestNewBuild} color="primary" autoFocus>
                        Yes
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(StartNewBuildDialog));