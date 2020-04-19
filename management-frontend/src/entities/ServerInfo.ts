import {PaginatedCollection} from "./pagination";

export interface ServerInfo {
    id: number,
    address: string,
    availableProcessors: number,
    totalPhysicalMemorySize: number,
    lastStatus: ServerStatus,

    // this HAS to be initialized in the frontend
    status: PaginatedCollection<ServerStatus>
}

export interface ServerStatus {
    freePhysicalMemorySize: number
    processCpuLoad: number
    systemCpuLoad: number
}