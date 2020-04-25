import {ComponentType} from "./ComponentInfo";

export interface PerfDto {
    // todo id
    id: string,
    sourceClass: string,
    operationId: string,
    arguments: string | null,
    duration: number,
    result: string,
    componentAddress: string,
    componentType: ComponentType,
    timestamp: Date
}