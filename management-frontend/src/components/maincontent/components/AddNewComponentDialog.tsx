import * as Yup from 'yup';
import React, {useCallback, useEffect, useState} from 'react';
import Button from '@material-ui/core/Button';
import {TextField} from 'formik-material-ui';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {ApplicationState} from "../../../ApplicationState";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {connect} from "react-redux";
import {Field, Form, Formik} from "formik";
import {ServerInfo} from "../../../entities/ServerInfo";
import {FormControl, InputLabel, LinearProgress, MenuItem, Select} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import {postRequest} from "../../../network/requests";
import {requestAllServers} from "../../../reducers/serversReducer";
import {CommandDto, CommandKeys, CommandRequest, CommandType} from "../../../entities/CommandDto";
import {useInterval} from "../../../utils/useInterval";

type AddNewComponentDialogProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    predefinedServer?: ServerInfo
}

const useStyles = makeStyles({
    formField: {
        display: 'block',
        margin: '10px 30px'
    },
    root: {
        padding: '10px'
    }
})

const AddNewComponentSchema = Yup.object().shape({
    'port': Yup.number()
        .min(1024)
        .required('Port is required'),
});

const AddNewComponentDialog = (props: AddNewComponentDialogProps) => {
    const {requestAllServers, servers, predefinedServer, openSnackbarAction} = props;
    const [open, setOpen] = useState(false);
    const [progress, setProgress] = useState(false);

    const classes = useStyles();

    const [commandType, setCommandType] = useState<CommandType>(CommandType.START_INDEX_SERVER);

    const [server, setServer] = useState(predefinedServer)

    const updateServers = useCallback(() => {
        if (open)
            requestAllServers();
    }, [open, requestAllServers])

    useInterval(updateServers, 1_000);

    useEffect(() => {
        if (!server) {
            const options = Object.values(servers.elements);
            if (options.length > 0)
                setServer(options[0])
        }
    }, [server, servers])

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Button color="primary" variant="contained" onClick={handleClickOpen}>
                Add new component
            </Button>
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" className={classes.root}>
                <DialogTitle id="form-dialog-title">Add new component</DialogTitle>
                {progress && <LinearProgress/>}
                <DialogContent>
                    <Formik
                        initialValues={{port: 8080}}
                        validationSchema={AddNewComponentSchema}
                        onSubmit={({port}, actions) => {
                            if (!server) {
                                openSnackbarAction("No server specified")
                                actions.setSubmitting(false)
                                return
                            }
                            setProgress(true)
                            const req: CommandRequest = {
                                type: CommandType[commandType] as CommandKeys,
                                arguments: `${server.address}:${port}`
                            }
                            postRequest<CommandDto>("/command", req)
                                .then(server => {
                                    setProgress(false)
                                    setOpen(false)
                                    actions.setSubmitting(false)
                                })
                                .catch(err => {
                                    setProgress(false)
                                    console.error(err)
                                    actions.setSubmitting(false)
                                })
                        }}
                    >
                        {({isSubmitting}) => <Form>

                            {!predefinedServer && <FormControl className={classes.formField}>
                                <InputLabel>Server</InputLabel>
                                <Select
                                    labelId="demo-simple-select-label"
                                    id="demo-simple-select"
                                    value={server && server.id}
                                    onChange={(e) => setServer(servers.elements[e.target.value as number])}
                                >
                                    {Object.values(servers.elements).map(server => <MenuItem key={server.id}
                                                                                             value={server.id}>{server.address}</MenuItem>)}
                                </Select>
                            </FormControl>
                            }

                            <FormControl className={classes.formField}>
                                <InputLabel>Component Type</InputLabel>
                                <Select
                                    labelId="demo-simple-select-label"
                                    id="demo-simple-select"
                                    value={commandType}
                                    onChange={(e) => setCommandType(e.target.value as number)}
                                >
                                    <MenuItem value={CommandType.START_INDEX_SERVER}>INDEX_SERVER</MenuItem>
                                    <MenuItem value={CommandType.START_WEBSERVER}>WEBSERVER</MenuItem>
                                </Select>
                            </FormControl>

                            <Field variant="outlined" type="number" name="port" label="Port"
                                   className={classes.formField}
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

const mapStateToProps = (state: ApplicationState) => ({
    servers: state.servers
});
const mapDispatchToProps = {
    openSnackbarAction,
    requestAllServers: requestAllServers as () => void
};

export default connect(mapStateToProps, mapDispatchToProps)(AddNewComponentDialog);