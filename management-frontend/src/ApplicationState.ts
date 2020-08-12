import {User} from "./entities/user";
import {PaginatedCollection} from "./entities/pagination";
import {ServerInfo} from "./entities/ServerInfo";
import {ComponentInfo} from "./entities/ComponentInfo";
import {LogDto} from "./entities/LogDto";
import {PerfDto} from "./entities/PerfDto";
import {GeneralOperationStatistics} from "./entities/GeneralOperationStatistics";
import {CommandDto} from "./entities/CommandDto";
import {BugReport} from "./entities/BugReport";
import {Corpus} from "./entities/Corpus";

export interface ApplicationState {
    userDetails: UserState
    users: PaginatedCollection<User>
    servers: PaginatedCollection<ServerInfo>
    components: PaginatedCollection<ComponentInfo>
    commands: PaginatedCollection<CommandDto>
    builds: PaginatedCollection<CommandDto>
    bugReports: PaginatedCollection<BugReport>
    corpuses: PaginatedCollection<Corpus>
    logs: PaginatedCollection<LogDto>
    perfLogs: PaginatedCollection<PerfDto>
    operationStats: OperationsStatsState
    snackbar: SnackbarState
}

export interface OperationsStatsState {
    [id: string]: GeneralOperationStatistics
}

export interface UserState {
    currentUserLogin: string | null
}

export interface SnackbarState {
    isOpen: boolean,
    message: string
}
