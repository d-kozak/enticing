import {DescribedElement} from "./DescribedElement";

export interface UserSettings {
    resultsPerPage: number
    annotationDataServer: string,
    annotationServer: string,
    servers: Array<IpAddress>
}


export type DefaultUserSettings = UserSettings & DescribedElement;

export type IpAddress = string