import {ComponentType} from "./ComponentInfo";

export interface LogDto {
    id: string,
    logType: LogType
    className: string,
    message: string,
    componentAddress: string,
    componentType: ComponentType,
    timestamp: Date
}

enum LogType {
    /**
     * low level information tracing the execution
     */
    DEBUG,
    /**
     * information about important events
     */
    INFO,
    /**
     * performance related information
     * how much time each operation took
     */
    PERF,
    /**
     * something has gone wrong, but it is not critical
     */
    WARN,
    /**
     * something has gone seriously wrong
     */
    ERROR,
}