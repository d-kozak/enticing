import {PaginatedCollection} from "./pagination";
import {ComponentInfo} from "./ComponentInfo";

export interface Corpus {
    id: string,
    name: string,
    running: boolean,

    // this HAS to be initialized in the frontend
    components: PaginatedCollection<ComponentInfo>
}