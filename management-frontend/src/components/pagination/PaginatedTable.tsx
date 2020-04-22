import React, {useEffect, useState} from 'react';
import {makeStyles} from "@material-ui/core/styles";
import {
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow
} from "@material-ui/core";
import {PaginatedCollection, WithId} from "../../entities/pagination";
import {PaginatedTableColumn} from "./PaginatedTableColumn";


export interface TableData<ContentType extends WithId> {
    columns: Array<PaginatedTableColumn<any, any>>
    data: PaginatedCollection<ContentType>
    pageSizeOptions?: Array<number>
}

const useStyles = makeStyles({});

export type PaginatedTableProps = TableData<any> & {
    requestPage(page: number, size: number): void
}


export default function PaginatedTable(props: PaginatedTableProps) {
    const {columns, data, requestPage} = props;
    const classes = useStyles();

    const pageSizeOptions = props.pageSizeOptions || [10, 25, 100]

    const [pageSize, setPageSize] = useState(pageSizeOptions[0]);
    const [currentPage, setCurrentPage] = useState(0);

    const anyDataMissing = (page: number, size: number): boolean => {
        for (let i = page * size; i < Math.min(data.totalElements, page * size + size); i++)
            if (!data.index[i]) return true;
        return false;
    }

    useEffect(() => {
        if (anyDataMissing(currentPage, pageSize)) {
            requestPage(currentPage, pageSize);
        }
    });

    const handleChangePage = (event: unknown, newPage: number) => {
        setCurrentPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPageSize(+event.target.value);
        setCurrentPage(0);
    };

    const items = [];
    for (let i = currentPage * pageSize; i < currentPage * pageSize + pageSize; i++) {
        let item = data.elements[data.index[i]];
        if (item) items.push(item);
    }

    return <Paper>
        <TableContainer>
            <Table stickyHeader>
                <TableHead>
                    <TableRow>
                        {columns.map(col =>
                            <TableCell
                                key={col.id}
                                align={col.align}
                            >
                                {col.label}
                            </TableCell>
                        )}
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
    </Paper>
}