import {ComponentType} from "./ComponentInfo";

export interface AddComponentRequest {
    serverId: string,
    port: number,
    type: ComponentType,
    buildId: string
}