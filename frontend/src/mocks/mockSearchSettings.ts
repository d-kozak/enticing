import {SearchSettings} from "../entities/SearchSettings";
import {Dispatch} from "redux";
import {hideProgressBarAction, showProgressBarAction} from "../actions/ProgressBarActions";
import {searchSettingsLoadedAction} from "../actions/SearchSettingsActions";
import {openSnackBar} from "../actions/SnackBarActions";

const searchSettings: Array<SearchSettings> = [
    {
        id: 0,
        isDefault: true,
        name: 'Test index',
        annotationDataServer: "server1.com:42/test",
        annotationServer: "localhost:666",
        servers: ['test.com:4200']
    },
    {
        id: 1,
        isDefault: false,
        name: 'Wikipedia 2018',
        annotationDataServer: "server1.com:42/foo",
        annotationServer: "localhost:666",
        servers: ['localhost:4200']
    },
    {
        id: 2,
        isDefault: false,
        name: 'CC-2017',
        annotationDataServer: "10.10.10.10:42",
        annotationServer: "192.168.0.25:666",
        servers: ['localhost:4200', 'localhost:9000', '8.8.8.8:2000']
    }
];

export const mockLoadSettings = (dispatch: Dispatch) => {
    dispatch(showProgressBarAction());
    setTimeout(() => {
        dispatch(searchSettingsLoadedAction(searchSettings));
        dispatch(openSnackBar('Configurations loaded'));
        dispatch(hideProgressBarAction());
    }, 2000);
}