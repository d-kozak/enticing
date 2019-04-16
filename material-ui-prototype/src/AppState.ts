import {SearchResult} from "./entities/SearchResult";
import {User} from "./entities/User";

export interface AppState {
    user: UserState,
    query: QueryState,
    searchResults: SearchResultsState,
    snackBar: SnackBarState,
    progressBar: ProgressBarState,
    admin: AdminState
}

export interface UserState {
    isLoggedIn: boolean,
    isAdmin: boolean
}

export interface QueryState {
    lastQuery: string
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