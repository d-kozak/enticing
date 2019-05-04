export interface SearchSettings {
    id: number,
    name: string,
    isDefault: boolean,
    annotationDataServer: Url,
    annotationServer: Url,
    servers: Array<IpAddress>
}

export type IpAddress = string;
export type Url = string;