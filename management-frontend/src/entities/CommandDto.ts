// todo dates have to be parsed manually
import {ComponentKeys} from "./ComponentInfo";

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
    type: CommandKeys,
    arguments: string
}

export enum CommandType {
    START_INDEX_SERVER,
    KILL_INDEX_SERVER,
    START_WEBSERVER,
    KILL_WEBSERVER,
    START_MANAGEMENT_SERVER,
    KILL_MANAGEMENT_SERVER,
    BUILD
}

export type CommandKeys = keyof typeof CommandType;

export function startCommandFor(type: ComponentKeys): CommandKeys {
    switch (type) {
        case "WEBSERVER":
            return CommandType[CommandType.START_WEBSERVER] as CommandKeys;
        case "INDEX_SERVER":
            return CommandType[CommandType.START_INDEX_SERVER] as CommandKeys;
        default:
            throw new Error(`wrong component type ${type}`);
    }
}


export enum CommandState {
    ENQUED,
    RUNNING,
    FINISHED,
    FAILED
}