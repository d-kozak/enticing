import {PaginatedCollection} from "./pagination";
import {LogDto} from "./LogDto";

export interface ComponentInfo {
    id: string,
    serverId: string,
    serverAddress: string,
    port: number,
    type: ComponentType,
    lastHeartbeat: Date

    // this HAS to be initialized in the frontend
    logs: PaginatedCollection<LogDto>
}

export enum ComponentType {
    WEBSERVER,
    INDEX_SERVER,
    INDEX_BUILDER,
    CONSOLE_CLIENT
}