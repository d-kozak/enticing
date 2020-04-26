import React from "react";
import {TableCell, TableSortLabel} from "@material-ui/core";
import {Sort} from "./PaginatedTable";

export type AlignOptions = 'inherit' | 'left' | 'center' | 'right' | 'justify'

export interface PaginatedTableColumn<ItemType, ColType> {
    type: "string" | "float" | "int" | "custom"
    id: string,
    label: string,
    align: AlignOptions,
    sortId?: string,
    filterOptions?: Array<string>,

    renderContent(property: ColType, item: ItemType): string
}

interface ExtraParams {
    align: AlignOptions
    sortId: string
    filterOptions: Array<string>
}

const defaults = {
    align: "right" as AlignOptions
}


export function CustomColumn<ItemType, ColType>(id: string, label: string, renderContent: (property: ColType, item: ItemType) => any, extra?: Partial<ExtraParams>): PaginatedTableColumn<ItemType, ColType> {
    return {
        type: "custom",
        id,
        label,
        renderContent,
        ...defaults,
        ...extra,
    }
}

export function IntColumn(id: string, label: string, extra?: Partial<ExtraParams>): PaginatedTableColumn<any, number> {
    const renderContent = (input: number) => input.toString()
    return {
        type: "int",
        id,
        label,
        renderContent,
        ...defaults,
        ...extra,
    }
}


export function FloatColumn(id: string, label: string, extra?: Partial<ExtraParams>): PaginatedTableColumn<any, number> {
    const renderContent = (input: number) => input.toFixed(2);
    return {
        type: "float",
        id,
        label,
        renderContent,
        ...defaults,
        ...extra,
    }
}

export function StringColumn(id: string, label: string, extra?: Partial<ExtraParams>): PaginatedTableColumn<any, string> {
    const renderContent = (input: string) => input;
    return {
        type: "string",
        id,
        label,
        renderContent,
        ...defaults,
        ...extra,
    }
}

interface SimpleColumnProps {
    column: PaginatedTableColumn<any, any>
}

export function SimpleColumnHeader({column}: SimpleColumnProps) {
    return <TableCell
        align={column.align}
    >
        {column.label}
    </TableCell>
}

type SortableColumnProps = SimpleColumnProps & {
    sort: Sort | false
    sortRequested: (col: PaginatedTableColumn<any, any>, sort: Sort | false) => void
}

export function SortableColumnHeader({column, sort, sortRequested}: SortableColumnProps) {
    return <TableCell
        align={column.align}
        sortDirection={sort}
    >
        <TableSortLabel
            active={sort !== false}
            direction={sort || 'asc'}
            onClick={() => sortRequested(column, sort)}
        >
            {column.label}
        </TableSortLabel>
    </TableCell>
}