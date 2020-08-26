import {PaginatedCollection} from "./pagination";
import {ComponentInfo, Status} from "./ComponentInfo";

export interface Corpus {
    id: string,
    name: string,
    status: Status,

    // this HAS to be initialized in the frontend
    components: PaginatedCollection<ComponentInfo>
}