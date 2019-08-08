import {User} from "../entities/User";
import {Snippet} from "../entities/Snippet";
import {CorpusFormat} from "../entities/CorpusFormat";
import {SearchSettings} from "../entities/SearchSettings";
import {FullDocument} from "../entities/FullDocument";
import {SelectedMetadata} from "../entities/SelectedMetadata";

export const initialState = {
    userState: {
        user: null as User | null,
        selectedSettings: null as string | null,
        selectedMetadata: null as SelectedMetadata | null
    },
    searchResult: {
        snippets: null as Array<Snippet> | null,
        corpusFormat: null as CorpusFormat | null
    },
    snackBar: {
        isOpen: false,
        message: ''
    },
    progressBar: {
        isVisible: false
    },
    adminState: {
        users: [] as Array<User>
    },
    searchSettings: {
        settings: {} as { [key: string]: SearchSettings }
    },
    dialog: {
        documentDialog: {
            document: null as FullDocument | null,
            corpusFormat: null as CorpusFormat | null
        },
        deleteUserDialog: {
            userToDelete: null as User | null,
            showProgress: false
        },
        changePasswordDialog: {
            user: null as User | null,
            showProgress: false
        }
    }
};

interface NewState {
    snackBar: SnackbarState
    progressBar: ProgressBarState,
    adminState: AdminState
    dialog: {
        deleteUserDialog: DeleteUserDialogState,
        changePasswordDialog: ChangePasswordDialogState,
        documentDialog: DocumentDialogState
    }
}


export type UserState = Readonly<typeof initialState.userState>

export interface AdminState {
    users: Array<User>
}

export interface SearchResultsState {
    snippets: Array<Snippet> | null,
    corpusFormat: CorpusFormat | null
}

export interface SnackbarState {
    isOpen: boolean,
    message: string
}

export interface ProgressBarState {
    isVisible: boolean
}

export type SearchSettingsState = Readonly<typeof initialState.searchSettings>;

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

export type ApplicationState = typeof initialState;