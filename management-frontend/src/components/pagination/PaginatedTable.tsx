import React, {useEffect, useState} from 'react';
import {Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow} from "@material-ui/core";
import {PaginatedCollection, WithId} from "../../entities/pagination";
import {PaginatedTableColumn, SimpleColumnHeader, SortableColumnHeader} from "./PaginatedTableColumn";


export interface TableData<ContentType extends WithId> {
    columns: Array<PaginatedTableColumn<any, any>>
    data: PaginatedCollection<ContentType>
    pageSizeOptions?: Array<number>
}

export type PaginatedTableProps = TableData<any> & {
    requestPage(page: number, size: number, requirements: Array<[string, string | number]>): void
    clearData: () => void
}

export type Sort = "asc" | "desc"

interface TableSortState {
    [columnId: string]: Sort
}

function sortRequirements(tableSort: TableSortState): Array<[string, string]> {
    return Object.entries(tableSort)
        .map(([id, sort]) => ["sort", `${id},${sort}`] as [string, string])
        .reverse()
}

export default function PaginatedTable(props: PaginatedTableProps) {
    const {columns, data, clearData, requestPage} = props;

    const pageSizeOptions = props.pageSizeOptions || [10, 25, 100]

    const [pageSize, setPageSize] = useState(pageSizeOptions[0]);
    const [currentPage, setCurrentPage] = useState(0);

    const [tableSort, setTableSort] = useState<TableSortState>({});

    const anyDataMissing = (page: number, size: number): boolean => {
        for (let i = page * size; i < Math.min(data.totalElements, page * size + size); i++)
            if (!data.index[i]) return true;
        return false;
    }

    useEffect(() => {
        if (anyDataMissing(currentPage, pageSize)) {
            requestPage(currentPage, pageSize, sortRequirements(tableSort));
        }
    });

    const handleChangePage = (event: unknown, newPage: number) => {
        setCurrentPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPageSize(+event.target.value);
        setCurrentPage(0);
    };

    const sortRequested = (col: PaginatedTableColumn<any, any>, sort: Sort | false) => {
        if (!col.sortId) {
            console.error("unknown sort id, should not happen")
            return
        }
        const next = {...tableSort};
        delete next[col.sortId];
        if (sort === "asc") next[col.sortId] = "desc";
        else if (sort === false) next[col.sortId] = "asc";
        setTableSort(next);
        clearData();
        requestPage(currentPage, pageSize, sortRequirements(next))
    };

    const items = [];
    for (let i = currentPage * pageSize; i < currentPage * pageSize + pageSize; i++) {
        let item = data.elements[data.index[i]];
        if (item) items.push(item);
    }

    return <div>
        <TableContainer>
            <Table stickyHeader>
                <TableHead>
                    <TableRow>
                        {columns.map(col => col.sortId ?
                            <SortableColumnHeader key={col.id} column={col} sort={tableSort[col.sortId] || false}
                                                  sortRequested={sortRequested}/>
                            : <SimpleColumnHeader key={col.id} column={col}/>)
                        }
                    </TableRow>
                </TableHead>
                <TableBody>
                    {
                        items.map(item =>
                            <TableRow hover key={item.id}>
                                {columns.map(col =>
                                    <TableCell key={col.id} align={col.align}>
                                        {col.renderContent(item[col.id], item)}
                                    </TableCell>
                                )}
                            </TableRow>
                        )
                    }
                </TableBody>
            </Table>
        </TableContainer>
        <TablePagination
            component="div"
            rowsPerPageOptions={pageSizeOptions}
            count={data.totalElements}
            rowsPerPage={pageSize}
            page={currentPage}
            onChangePage={handleChangePage}
            onChangeRowsPerPage={handleChangeRowsPerPage}
        />
    </div>
}