import {User} from "./entities/user";
import {PaginatedCollection} from "./entities/pagination";
import {ServerInfo} from "./entities/ServerInfo";
import {ComponentInfo} from "./entities/ComponentInfo";
import {LogDto} from "./entities/LogDto";
import {PerfDto} from "./entities/PerfDto";
import {GeneralOperationStatistics} from "./entities/GeneralOperationStatistics";
import {CommandDto} from "./entities/CommandDto";

export interface ApplicationState {
    currentUser: User | null
    users: PaginatedCollection<User>
    servers: PaginatedCollection<ServerInfo>
    components: PaginatedCollection<ComponentInfo>
    commands: PaginatedCollection<CommandDto>
    logs: PaginatedCollection<LogDto>
    perfLogs: PaginatedCollection<PerfDto>
    operationStats: Map<string, GeneralOperationStatistics>
}

