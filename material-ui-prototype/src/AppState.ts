export interface AppState {
    user: UserState,
    query: QueryState,
    searchResults: SearchResultsState,
    snackBar: SnackBarState
}


export interface UserState {
    isLoggedIn: boolean,
    isAdmin: boolean
}

export interface QueryState {

}

export interface SearchResultsState {

}

export interface SnackBarState {
    isOpen: boolean,
    message: string
}