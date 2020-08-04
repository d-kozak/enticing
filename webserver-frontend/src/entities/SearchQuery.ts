export interface SearchQuery {
    query: string,
    snippetCount: number,
    metadata: TextMetadata,
    resultFormat: "SNIPPET",
    textFormat: "TEXT_UNIT_LIST",
    defaultIndex: string,
    uuid: string,
    filterOverlaps: boolean
}

export type TextMetadata = Predefined | ExactFormatDefinition

export class Predefined {
    readonly type: "predef" = "predef";

    constructor(public value: "all" | "none") {
    }
}

export class ExactFormatDefinition {
    readonly type: "exact" = "exact";

    constructor(public indexes: IndexDefinition, public entities: EntityDefinition) {
    }
}

export type EntityDefinition = Predefined | ExactEntityDefinition

export class ExactEntityDefinition {
    readonly type: "exact" = "exact";

    constructor(public entities: { [key: string]: IndexDefinition }) {
    }
}

export type IndexDefinition = Predefined | ExactIndexDefinition

export class ExactIndexDefinition {
    readonly type: "exact" = "exact";

    constructor(public names: Array<string>) {
    }
}
