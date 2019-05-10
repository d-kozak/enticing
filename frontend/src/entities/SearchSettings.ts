/**
 * Servers are actually a set in the backend but here it is kept as array for convenience (methods like map() are not not Sets in js unfortunately)
 */
export interface SearchSettings extends SearchSettingsContent {
    id: number,
    default: boolean,
    private: boolean,
    /**
     * true only for new search settings before they are saved in the backend
     */
    isTransient?: boolean
}

export type IpAddress = string;
export type Url = string;

/**
 * Part of the search settings that can be saved into a file and loaded from a file
 */
export interface SearchSettingsContent {
    name: string,
    annotationDataServer: Url,
    annotationServer: Url,
    servers: Array<IpAddress>
}

export const isSearchSettingsContent = (obj: any): obj is SearchSettingsContent => {
    if (typeof obj.name !== 'string') return false;
    if (typeof obj.annotationDataServer !== 'string') return false;
    if (typeof obj.annotationServer !== 'string') return false;
    if (!Array.isArray(obj.servers)) return false;
    return obj.servers.every((elem: any) => typeof elem === 'string');
}