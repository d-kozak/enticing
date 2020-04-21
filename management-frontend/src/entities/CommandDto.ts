// todo dates have to be parsed manually
export interface CommandDto {
    id: string
    type: CommandType,
    state: CommandState,
    arguments: string,
    submittedBy: string,
    submittedAt: Date,
    startAt: Date | null,
    finishedAt: Date | null
}

export interface CommandRequest {
    type: CommandType,
    arguments: string
}

export enum CommandType {
    START_INDEX_SERVER,
    KILL_INDEX_SERVER,
    START_WEBSERVER,
    KILL_WEBSERVER,
    START_MANAGEMENT_SERVER,
    KILL_MANAGEMENT_SERVER
}

export enum CommandState {
    ENQUED,
    RUNNING,
    FINISHED,
    FAILED
}