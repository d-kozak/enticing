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


export type UserState = Readonly<typeof initialState.userState>

export type AdminState = Readonly<typeof initialState.adminState>

export type SearchResultsState = Readonly<typeof initialState.searchResult>

export type SnackBarState = Readonly<typeof initialState.snackBar>

export type ProgressBarState = Readonly<typeof initialState.progressBar>

export type SearchSettingsState = Readonly<typeof initialState.searchSettings>;

export type DocumentDialogState = Readonly<typeof initialState.dialog.documentDialog>

export type DeleteUserDialogState = Readonly<typeof initialState.dialog.deleteUserDialog>

export type ChangePasswordDialogState = Readonly<typeof initialState.dialog.changePasswordDialog>

export type ApplicationState = typeof initialState;