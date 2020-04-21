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
import {ApplicationState, OperationsStatsState} from "../../../ApplicationState";
import {connect} from "react-redux";
import {getRequest} from "../../../network/requests";
import {refreshStats} from "../../../reducers/operationStatsReducer";

const useStyles = makeStyles({});

export type PaginatedTableProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>


function OperationsTable(props: PaginatedTableProps) {
    const {stats, refreshStats} = props;
    const classes = useStyles();

    const pageSizeOptions = [10, 25]

    const [pageSize, setPageSize] = useState(pageSizeOptions[0]);
    const [currentPage, setCurrentPage] = useState(0);

    const refreshOperations = () => {
        getRequest<OperationsStatsState>("/perf/stats")
            .then((stats) => refreshStats(stats))
            .catch(err => console.error(err))
    }


    useEffect(() => {
        refreshOperations();
        const interval = setInterval(refreshOperations, 5000);
        return () => clearInterval(interval);
    });

    const handleChangePage = (event: unknown, newPage: number) => {
        setCurrentPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPageSize(+event.target.value);
        setCurrentPage(0);
    };

    const items = Object.values(stats).slice(currentPage * pageSize, currentPage * pageSize + pageSize)

    return <Paper>
        <TableContainer>
            <Table stickyHeader>
                <TableHead>
                    <TableRow>
                        <TableCell align="right">
                            Operation id
                        </TableCell>
                        <TableCell align="right">
                            Average duration
                        </TableCell>
                        <TableCell align="right">
                            Average duration deviation
                        </TableCell>
                        <TableCell align="right">
                            Max duration
                        </TableCell>
                        <TableCell align="right">
                            Min duration
                        </TableCell>
                        <TableCell align="right">
                            Invocation count
                        </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {
                        items.map(item =>
                            <TableRow hover key={item.operationId}>
                                <TableCell align="right">
                                    {item.operationId}
                                </TableCell>
                                <TableCell align="right">
                                    {item.averageDuration.toFixed(2)}
                                </TableCell>
                                <TableCell align="right">
                                    {item.averageDurationDeviation.toFixed(2)}
                                </TableCell>
                                <TableCell align="right">
                                    {item.minDuration}
                                </TableCell>
                                <TableCell align="right">
                                    {item.maxDuration}
                                </TableCell>
                                <TableCell align="right">
                                    {item.invocationCount}
                                </TableCell>
                            </TableRow>
                        )
                    }
                </TableBody>
            </Table>
        </TableContainer>
        <TablePagination
            component="div"
            rowsPerPageOptions={pageSizeOptions}
            count={Object.values(stats).length}
            rowsPerPage={pageSize}
            page={currentPage}
            onChangePage={handleChangePage}
            onChangeRowsPerPage={handleChangeRowsPerPage}
        />
    </Paper>
}

const mapStateToProps = (state: ApplicationState) => ({
    stats: state.operationStats
});
const mapDispatchToProps = {
    refreshStats: refreshStats as (stats: OperationsStatsState) => void
};

export default connect(mapStateToProps, mapDispatchToProps)(OperationsTable);