export const SNACKBAR_OPEN = '[SNACKBAR] OPEN';
export const SNACKBAR_CLOSE = '[SNACKBAR] CLOSE';

interface OpenSnackBarAction {
    type: typeof SNACKBAR_OPEN,
    message: string
}

interface CloseSnackBarAction {
    type: typeof SNACKBAR_CLOSE
}

export type SnackBarAction = OpenSnackBarAction | CloseSnackBarAction

export const openSnackBar = (message: string): OpenSnackBarAction => ({
    type: SNACKBAR_OPEN,
    message
})


export const closeSnackBar = (): CloseSnackBarAction => ({
    type: SNACKBAR_CLOSE
})