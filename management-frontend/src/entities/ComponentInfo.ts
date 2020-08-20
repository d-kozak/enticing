import {PaginatedCollection} from "./pagination";
import {LogDto} from "./LogDto";

export interface BasicComponentInfo {
    id: number,
    serverAddress: string,
    port: number,
    type: ComponentKeys
}

export interface ComponentInfo {
    id: string,
    serverId: string,
    serverAddress: string,
    port: number,
    type: ComponentKeys,
    lastHeartbeat: Date,
    status: ComponentStatus,

    // this HAS to be initialized in the frontend
    logs: PaginatedCollection<LogDto>
}

export type ComponentStatus = "RUNNING" | "DEAD";

export enum ComponentType {
    WEBSERVER,
    INDEX_SERVER,
    INDEX_BUILDER,
    CONSOLE_CLIENT
}

export type ComponentKeys = keyof typeof ComponentType;

export function isComponentProbablyDead(component: ComponentInfo, limitSecond: number = 30): boolean {
    const prev = new Date(Date.now() - limitSecond * 1000);
    return new Date(component.lastHeartbeat) < prev;
}