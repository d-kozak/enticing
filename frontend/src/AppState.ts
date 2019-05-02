import {AdminState} from "./reducers/AdminReducer";
import {UserState} from "./reducers/UserReducer";
import {QueryState} from "./reducers/QueryReducer";
import {ProgressBarState} from "./reducers/ProgressBarReducer";
import {SearchResultsState} from "./reducers/SearchResultReducer";
import {SnackBarState} from "./reducers/SnackBarReducer";
import {DialogState} from "./reducers/dialog/DialogReducer";

export interface AppState {
    user: UserState,
    query: QueryState,
    searchResults: SearchResultsState,
    snackBar: SnackBarState,
    progressBar: ProgressBarState,
    admin: AdminState,
    dialog: DialogState
}

