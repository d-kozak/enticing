import {SearchResult} from "./entities/SearchResult";
import {User} from "./entities/User";
import {IndexedDocument} from "./entities/IndexedDocument";
import {SearchResultContext} from "./entities/SearchResultContext";
import {SearchQuery} from "./entities/SearchQuery";

export interface AppState {
    user: UserState,
    query: QueryState,
    searchResults: SearchResultsState,
    snackBar: SnackBarState,
    progressBar: ProgressBarState,
    admin: AdminState,
    dialog: DialogState
}

export interface UserState {
    isLoggedIn: boolean,
    isAdmin: boolean
}

export interface QueryState {
    lastQuery: SearchQuery
    useConstraints: boolean
}

export interface ProgressBarState {
    isVisible: boolean
}

export interface SearchResultsState {
    searchResults: Array<SearchResult> | null
}

export interface SnackBarState {
    isOpen: boolean,
    message: string
}

export interface AdminState {
    users: Array<User>
}

export interface DialogState {
    documentDialog: DocumentDialogState;
    contextDialog: ContextDialogState;
}

export interface DocumentDialogState {
    document: IndexedDocument | null
}

export interface ContextDialogState {
    context: SearchResultContext | null
    showProgress: boolean
}