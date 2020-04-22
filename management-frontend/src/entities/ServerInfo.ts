import {PaginatedCollection} from "./pagination";
import {ComponentInfo} from "./ComponentInfo";

export interface ServerInfo {
    id: string,
    address: string,
    availableProcessors: number,
    totalPhysicalMemorySize: number,
    lastStatus: ServerStatus,

    // this HAS to be initialized in the frontend
    components: PaginatedCollection<ComponentInfo>
}

export interface ServerStatus {
    freePhysicalMemorySize: number
    processCpuLoad: number
    systemCpuLoad: number
    timestamp: string
}