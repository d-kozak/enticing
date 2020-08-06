import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import {Typography} from "@material-ui/core";
import PaginatedTable from "../../pagination/PaginatedTable";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {BugReport} from "../../../entities/BugReport";
import {addNewItems, clearAll} from "../../../reducers/bugReportReducer";
import {BooleanColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn";

type BugReportsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {};

const BugReportsTable = (props: BugReportsTableProps) => {
    const {addNewItems, bugReports, clearAll} = props;

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<BugReport>>("/bug-report", [["page", page], ["size", size], ...requirements])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("query", "Query", {sortId: "query"}),
        BooleanColumn("filterOverlaps", "Filter overlaps", {sortId: "filterOverlaps"}),
        StringColumn("documentTitle", "Document", {sortId: "documentTitle"}),
        StringColumn("description", "Description", {sortId: "description"})
    ];

    return <div>
        <Typography variant="h3">Bug Reports</Typography>
        <PaginatedTable
            data={bugReports}
            clearData={clearAll}
            columns={columns}
            requestPage={requestPage}/>
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    bugReports: state.bugReports
});

const mapDispatchToProps = {
    addNewItems,
    clearAll
};

export default connect(mapStateToProps, mapDispatchToProps)(BugReportsTable)