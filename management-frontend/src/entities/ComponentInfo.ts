import {PaginatedCollection} from "./pagination";
import {LogDto} from "./LogDto";

export interface BasicComponentInfo {
    id: string,
    serverAddress: string,
    port: number,
    type: ComponentType
}

export interface ComponentInfo {
    id: string,
    serverId: string,
    serverAddress: string,
    port: number,
    type: ComponentType,
    lastHeartbeat: Date,
    status: Status,

    // this HAS to be initialized in the frontend
    logs: PaginatedCollection<LogDto>
}

export type Status = "STARTING" | "RUNNING" | "DEAD" ;

export type ComponentType = "WEBSERVER" | "INDEX_SERVER" | "INDEX_BUILDER" | "CONSOLE_CLIENT"

export function isComponentProbablyDead(component: ComponentInfo, limitSecond: number = 30): boolean {
    const prev = new Date(Date.now() - limitSecond * 1000);
    return new Date(component.lastHeartbeat) < prev;
}