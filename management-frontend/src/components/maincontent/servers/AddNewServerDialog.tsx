import * as Yup from 'yup';
import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import {TextField} from 'formik-material-ui';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {ApplicationState} from "../../../ApplicationState";
import {addServer} from "../../../reducers/serversReducer";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {connect} from "react-redux";
import {Field, Form, Formik} from "formik";
import {postRequest} from "../../../network/requests";
import {ServerInfo} from "../../../entities/ServerInfo";
import {LinearProgress} from "@material-ui/core";

type ServerComponentsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps

const urlRegex = /((([-\w]+\.)+[\w-]+)|((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|localhost)(:\d+)?([\w-/]*)$/;
const urlRegexFail = 'Please provide a valid url';

const AddNewsServerSchema = Yup.object().shape({
    'url': Yup.string()
        .matches(urlRegex, urlRegexFail)
        .required('URL is required'),
});

const AddNewServerDialog = (props: ServerComponentsTableProps) => {
    const {addServer, openSnackbarAction} = props;
    const [open, setOpen] = useState(false);
    const [progress, setProgress] = useState(false);
    const history = useHistory();
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Button color="primary" variant="contained" onClick={handleClickOpen}>
                Add new server
            </Button>
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Add new server</DialogTitle>
                {progress && <LinearProgress/>}
                <DialogContent>
                    <Formik
                        initialValues={{url: ''}}
                        validationSchema={AddNewsServerSchema}
                        onSubmit={({url}, actions) => {
                            setProgress(true)
                            postRequest<ServerInfo>("/server/add", {url})
                                .then(server => {
                                    setProgress(false)
                                    addServer(server)
                                    setOpen(false)
                                    actions.setSubmitting(false)
                                    openSnackbarAction("Server added")
                                    history.push(`/server/${server.id}`);
                                })
                                .catch(err => {
                                    setProgress(false)
                                    console.error(err)
                                    actions.setSubmitting(false)
                                    openSnackbarAction("Failed to add server")
                                })
                        }}
                    >
                        {({isSubmitting}) => <Form>
                            <Field variant="outlined" type="text" name="url" label="Url"
                                   component={TextField}/>
                            <DialogActions>
                                <Button onClick={handleClose} color="primary">
                                    Cancel
                                </Button>
                                <Button disabled={isSubmitting} type="submit" color="primary" variant="contained">
                                    Add
                                </Button>
                            </DialogActions>
                        </Form>}
                    </Formik>
                </DialogContent>
            </Dialog>
        </div>
    );
}

const mapStateToProps = (state: ApplicationState) => ({});
const mapDispatchToProps = {
    addServer,
    openSnackbarAction
};

export default connect(mapStateToProps, mapDispatchToProps)(AddNewServerDialog);