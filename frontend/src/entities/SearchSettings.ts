/**
 * Servers are actually a set in the backend but here it is kept as array for convenience (methods like map() are not not Sets in js unfortunately)
 */
export interface SearchSettings {
    id: number,
    name: string,
    default: boolean,
    private: boolean,
    annotationDataServer: Url,
    annotationServer: Url,
    servers: Array<IpAddress>
}

export type IpAddress = string;
export type Url = string;