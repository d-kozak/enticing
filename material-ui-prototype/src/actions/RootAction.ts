import {QueryAction} from "./QueryActions";
import {SearchResultAction} from "./SearchResultActions";
import {UserAction} from "./UserActions";

export type RootAction = QueryAction | SearchResultAction | UserAction