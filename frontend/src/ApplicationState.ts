import {User} from "./entities/User";
import {SearchResult} from "./entities/SearchResult";
import {CorpusFormat} from "./entities/CorpusFormat";
import {SearchSettings} from "./entities/SearchSettings";
import {DocumentDebugInfo, FullDocument} from "./entities/FullDocument";

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
        rawDocumentDialog: RawDocumentDialogState
    }
}

export interface UserState {
    user: User
    isLoggedIn: boolean
}

export interface AdminState {
    users: Array<User>,
    debugMode: boolean
}

export interface SearchResultsState {
    snippetIds: Array<string>,
    snippetsById: { [id: string]: SearchResult }
    corpusFormat: CorpusFormat | null,
    moreResultsAvailable: boolean,
    statistics?: SearchStatistics,
}

export interface SearchStatistics {
    backendTime: number,
    frontendTime: number
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

export interface RawDocumentDialogState {
    info: DocumentDebugInfo & { content: string, location: number, size: number } | null
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