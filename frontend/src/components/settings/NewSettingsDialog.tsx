import React from 'react';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

import {ApplicationState} from "../../reducers/ApplicationState";
import {connect} from "react-redux";
import {getNewSearchSettings} from "../../reducers/selectors";
import {searchSettingsAddingCancelledAction} from "../../actions/SearchSettingsActions";
import SettingsForm from "./SettingsForm";


export type newSearchSettingsDialog = typeof mapDispatchToProps & ReturnType<typeof mapStateToProps>

const NewSearchSettingsDialog = (props: newSearchSettingsDialog) => {
    const {newSearchSettings, closeDialog} = props;
    return <div>
        <Dialog
            open={newSearchSettings != null}
            onClose={closeDialog}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">New Search Settings</DialogTitle>
            <DialogContent>
                {newSearchSettings && <SettingsForm settings={newSearchSettings}/>}
            </DialogContent>
        </Dialog>
    </div>
}

const mapStateToProps = (state: ApplicationState) => ({
    newSearchSettings: getNewSearchSettings(state)
});
const mapDispatchToProps = {
    closeDialog: searchSettingsAddingCancelledAction
};
export default connect(mapStateToProps, mapDispatchToProps)(NewSearchSettingsDialog);