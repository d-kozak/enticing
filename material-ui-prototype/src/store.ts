import {applyMiddleware, createStore} from "redux";
import {composeWithDevTools} from "redux-devtools-extension";
import {createLogger} from "redux-logger";
import thunk from "redux-thunk";
import rootReducer from "./reducers/RootReducer";


const logger = createLogger({});

const middleware = [logger, thunk];

const store = createStore(
    rootReducer,
    composeWithDevTools(
        applyMiddleware(...middleware)
    )
);


export default store;