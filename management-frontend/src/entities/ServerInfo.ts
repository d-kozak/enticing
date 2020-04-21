import {PaginatedCollection} from "./pagination";

export interface ServerInfo {
    id: string,
    address: string,
    availableProcessors: number,
    totalPhysicalMemorySize: number,
    lastStatus: ServerStatus,

    // this HAS to be initialized in the frontend
    status: PaginatedCollection<ServerStatus>
}

export interface ServerStatus {
    // todo id
    id: string
    freePhysicalMemorySize: number
    processCpuLoad: number
    systemCpuLoad: number
}