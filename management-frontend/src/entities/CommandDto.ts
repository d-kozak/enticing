// todo dates have to be parsed manually

import {BasicComponentInfo} from "./ComponentInfo";

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
    arguments: string | BasicComponentInfo
}

export type CommandType = "ADD_COMPONENT" | "START_COMPONENT" | "KILL_COMPONENT" | "REMOVE_COMPONENT" | "BUILD"


export enum CommandState {
    ENQUED,
    RUNNING,
    FINISHED,
    FAILED
}