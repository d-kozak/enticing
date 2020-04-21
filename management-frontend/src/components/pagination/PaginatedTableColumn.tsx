export type AlignOptions = 'inherit' | 'left' | 'center' | 'right' | 'justify'

export interface PaginatedTableColumn<ItemType, ColType> {
    type: "string" | "float" | "int" | "action"
    id: string,
    label: string,
    align: AlignOptions

    renderContent(property: ColType, item: ItemType): string
}

export function ActionColumn<ItemType, ColType>(id: string, label: string, renderContent: (property: ColType, item: ItemType) => any, align: AlignOptions = "right"): PaginatedTableColumn<ItemType, ColType> {
    return {
        type: "action",
        id,
        label,
        align,
        renderContent
    }
}

export function IntColumn(id: string, label: string, align: AlignOptions = "right"): PaginatedTableColumn<any, number> {
    const renderContent = (input: number) => input.toString()
    return {
        type: "int",
        id,
        label,
        align,
        renderContent
    }
}


export function FloatColumn(id: string, label: string, align: AlignOptions = "right"): PaginatedTableColumn<any, number> {
    const renderContent = (input: number) => input.toFixed(2);
    return {
        type: "float",
        id,
        label,
        align,
        renderContent
    }
}

export function StringColumn(id: string, label: string, align: AlignOptions = "right"): PaginatedTableColumn<any, string> {
    const renderContent = (input: string) => input;
    return {
        type: "string",
        id,
        label,
        align,
        renderContent
    }
}