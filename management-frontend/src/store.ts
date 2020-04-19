import {createLogger} from "redux-logger";
import rootReducer from "./reducers/rootReducer";
import {configureStore, getDefaultMiddleware} from "redux-starter-kit";


const logger = createLogger({});

const middleware = [...getDefaultMiddleware(), logger];

const store = configureStore({
    reducer: rootReducer,
    middleware,
    devTools: process.env.NODE_ENV !== 'production'
});

export default store;