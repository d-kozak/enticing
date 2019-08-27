import {User} from "./entities/User";
import {SearchResult} from "./entities/SearchResult";
import {CorpusFormat} from "./entities/CorpusFormat";
import {SearchSettings} from "./entities/SearchSettings";
import {FullDocument} from "./entities/FullDocument";

export interface ApplicationState {
    userState: UserState,
    searchResult: SearchResultsState,
    snackBar: SnackbarState
    progressBar: ProgressBarState,
    adminState: AdminState,
    searchSettings: SearchSettingsState,
    dialog: {
        documentDialog: DocumentDialogState,
        deleteUserDialog: DeleteUserDialogState,
        changePasswordDialog: ChangePasswordDialogState,
    }
}

export interface UserState {
    user: User
    isLoggedIn: boolean
}

export interface AdminState {
    users: Array<User>
}

export interface SearchResultsState {
    snippets: Array<SearchResult> | null,
    corpusFormat: CorpusFormat | null,
    moreResultsAvailable: boolean
}

export interface SnackbarState {
    isOpen: boolean,
    message: string
}

export interface ProgressBarState {
    isVisible: boolean
}

export interface SearchSettingsState {
    settings: { [key: string]: SearchSettings }
}

export interface DocumentDialogState {
    document: FullDocument | null,
    corpusFormat: CorpusFormat | null
}

export interface DeleteUserDialogState {
    userToDelete: User | null,
    showProgress: boolean
}

export interface ChangePasswordDialogState {
    user: User | null,
    showProgress: boolean
}