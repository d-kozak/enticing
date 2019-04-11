export interface Settings {
    mappingFile: string,
    resultsPerPage: number
    annotationDataServer: string,
    annotationServer: string,
    servers: Array<IpAddress>
}

export type IpAddress = string