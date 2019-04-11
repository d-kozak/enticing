export interface UserSettings {
    mappingFile: string,
    resultsPerPage: number
    annotationDataServer: string,
    annotationServer: string,
    servers: Array<IpAddress>
}

export type IpAddress = string