import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import {IconButton, Tooltip, Typography} from "@material-ui/core";
import PaginatedTable from "../../pagination/PaginatedTable";
import {deleteRequest, getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {BugReport} from "../../../entities/BugReport";
import {addNewItems, clearAll, removeReport} from "../../../reducers/bugReportReducer";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {BooleanColumn, CustomColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn";
import GetAppIcon from "@material-ui/icons/GetApp";
import DeleteIcon from "@material-ui/icons/Delete";
import {downloadFile} from "../../utils/downloadFile";

type BugReportsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {};


const BugReportsTable = (props: BugReportsTableProps) => {
    const {addNewItems, removeReport, bugReports, openSnackbarAction, clearAll} = props;

    const downloadDocument = (report: BugReport) => {
        getRequest<string>(`/bug-report/document/${report.id}`)
            .then(doc => downloadFile(report.documentTitle + ".mg4j", doc))
            .catch((err) => {
                    openSnackbarAction("Failed to download mg4j file")
                    console.error(err)
                }
            )
    };

    const deleteReport = (report: BugReport) => {
        deleteRequest(`/bug-report/${report.id}`)
            .then(() => {
                openSnackbarAction("Report deleted")
                removeReport(report)
            }).catch((err) => {
                openSnackbarAction("Failed to delete report")
                console.error(err)
            }
        )
    };

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
        StringColumn("description", "Description", {sortId: "description"}),
        CustomColumn<BugReport, undefined>("downloadMg4j", "Download document",
            (prop, report) => <Tooltip title="Download document">
                <IconButton onClick={() => downloadDocument(report)}>
                    <GetAppIcon/>
                </IconButton>
            </Tooltip>
        ),
        CustomColumn<BugReport, undefined>("deleteReport", "Delete report",
            (prop, report) => <Tooltip title="Delete report">
                <IconButton onClick={() => deleteReport(report)}>
                    <DeleteIcon/>
                </IconButton>
            </Tooltip>
        )
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
    clearAll,
    removeReport,
    openSnackbarAction
};

export default connect(mapStateToProps, mapDispatchToProps)(BugReportsTable)