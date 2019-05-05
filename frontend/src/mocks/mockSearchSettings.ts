import {SearchSettings} from "../entities/SearchSettings";
import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {searchSettingsLoadedAction, searchSettingsUpdatedAction} from "../actions/SearchSettingsActions";
import {openSnackBar} from "../actions/SnackBarActions";

const searchSettings: Array<SearchSettings> = [
    {
        id: 0,
        isDefault: false,
        name: 'Test index',
        annotationDataServer: "server1.com:42/test",
        annotationServer: "localhost:666",
        servers: ['10.10.10.10'],
        isPrivate: true,
    },
    {
        id: 1,
        isDefault: true,
        name: 'Wikipedia 2018',
        annotationDataServer: "server1.com:42/foo",
        annotationServer: "localhost:666",
        servers: ['localhost:4200'],
        isPrivate: false,
    },
    {
        id: 2,
        isDefault: false,
        name: 'CC-2017',
        annotationDataServer: "10.10.10.10:42",
        annotationServer: "192.168.0.25:666",
        servers: ['localhost:4200', 'localhost:9000', '8.8.8.8:2000'],
        isPrivate: false,
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
    console.log('here');
    dispatch(showProgressBarAction());
    setTimeout(() => {
        dispatch(searchSettingsUpdatedAction(newSettings))
        dispatch(openSnackBar(`Search settings ${newSettings.name} updated`));
        dispatch(hideProgressBarAction());
        onDone();
    }, 2000);
}
