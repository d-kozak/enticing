import {SearchSettings} from "../entities/SearchSettings";
import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {
    searchSettingsLoadedAction,
    searchSettingsNewDefaultAction,
    searchSettingsRemovedAction,
    searchSettingsUpdatedAction
} from "../actions/SearchSettingsActions";
import {openSnackBar} from "../actions/SnackBarActions";

const searchSettings: Array<SearchSettings> = [
    {
        id: 0,
        default: false,
        name: 'Test index',
        annotationDataServer: "server1.com:42/test",
        annotationServer: "localhost:666",
        servers: ['10.10.10.10'],
        private: true,
    },
    {
        id: 1,
        default: true,
        name: 'Wikipedia 2018',
        annotationDataServer: "server1.com:42/foo",
        annotationServer: "localhost:666",
        servers: ['localhost:4200'],
        private: false,
    },
    {
        id: 2,
        default: false,
        name: 'CC-2017',
        annotationDataServer: "10.10.10.10:42",
        annotationServer: "192.168.0.25:666",
        servers: ['localhost:4200', 'localhost:9000', '8.8.8.8:2000'],
        private: false,
    }
];

export const mockLoadSearchSettings = (dispatch: Dispatch, isAdmin: boolean) => {
    dispatch(showProgressBarAction());
    setTimeout(() => {
        const settingsToLoad = isAdmin ? searchSettings : searchSettings.slice(1);
        dispatch(searchSettingsLoadedAction(settingsToLoad));
        dispatch(openSnackBar('Configurations loaded'));
        dispatch(hideProgressBarAction());
    }, 2000);
}

export const mockUpdateSearchSettings = (dispatch: Dispatch, newSettings: SearchSettings, onDone: () => void) => {
    setTimeout(() => {
        dispatch(searchSettingsUpdatedAction(newSettings))
        dispatch(openSnackBar(`Search settings ${newSettings.name} updated`));
        onDone();
    }, 2000);
}

export const mockRemoveSearchSettings = (dispatch: Dispatch, newSettings: SearchSettings, onDone: () => void) => {
    setTimeout(() => {
        dispatch(searchSettingsRemovedAction(newSettings))
        dispatch(openSnackBar(`Search settings ${newSettings.name} removed`));
        onDone();
    }, 2000);
}

export const mockChangeDefaultSearchSettings = (dispatch: Dispatch, settings: SearchSettings, onDone: () => void) => {
    setTimeout(() => {
        dispatch(searchSettingsNewDefaultAction(settings))
        dispatch(openSnackBar(`Search settings ${settings.name} were made default`));
        onDone();
    }, 2000);
}

let counter = 3;

export const mockSaveNewSearchSettings = (dispatch: Dispatch, searchSettings: SearchSettings, onDone: () => void) => {
    setTimeout(() => {
        dispatch(openSnackBar(`New settings ${searchSettings.name} added`));
        searchSettings.id = counter++
        dispatch(searchSettingsUpdatedAction(searchSettings));
        onDone();
    }, 2000);
}
