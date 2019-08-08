import {
    USER_LOGIN_SUCCESS,
    USER_LOGOUT,
    USER_SEARCH_SETTINGS_SELECTED_SUCCESS,
    USER_SETTINGS_UPDATED,
    UserAction
} from "../actions/UserActions";
import {initialState, UserState} from "./ApplicationState";

type UserReducer = (state: UserState | undefined, action: UserAction) => UserState

const userReducer: UserReducer = (state = initialState.userState, action) => {
    switch (action.type) {
        case USER_LOGIN_SUCCESS:
            return {
                user: action.user,
                selectedSettings: null,
                selectedMetadata: null
            }
        case USER_LOGOUT:
            return {user: null, selectedSettings: null, selectedMetadata: null}
        case USER_SETTINGS_UPDATED: {
            if (!state.user) {
                throw new Error("Cannot update settings when no user is logged in")
            }
            return {
                ...state,
                user: {
                    ...state.user,
                    userSettings: action.userSettings
                }
            }
        }
        case USER_SEARCH_SETTINGS_SELECTED_SUCCESS:
            const selectedSettingsId = typeof action.settings === 'string' ? action.settings : action.settings.id;
            const newState = {
                ...state,
                selectedSettings: selectedSettingsId
            };
            if (newState.user !== null) {
                newState.user.selectedSettings = selectedSettingsId;
            }
            return newState;
    }
    return state
}


export default userReducer;