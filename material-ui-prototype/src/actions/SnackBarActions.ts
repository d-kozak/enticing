interface OpenSnackBarAction {
    type: '[SNACKBAR] OPEN',
    message: string
}

interface CloseSnackBarAction {
    type: '[SNACKBAR] CLOSE',
}

export type SnackBarAction = OpenSnackBarAction | CloseSnackBarAction

export const openSnackBar = (message: string): OpenSnackBarAction => ({
    type: "[SNACKBAR] OPEN",
    message
})


export const closeSnackBar = (message: string): CloseSnackBarAction => ({
    type: "[SNACKBAR] CLOSE"
})