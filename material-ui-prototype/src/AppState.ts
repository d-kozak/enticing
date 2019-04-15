export interface AppState {
    user: UserState,
    query: QueryState,
    searchResults: SearchResultsState
}


export interface UserState {
    isLoggedIn: boolean,
    isAdmin: boolean
}

export interface QueryState {

}

export interface SearchResultsState {

}