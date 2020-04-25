import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems, clearAll} from "../../../reducers/logsReducer";
import {getRequest} from "../../../network/requests";
import {LogDto} from "../../../entities/LogDto";
import {PaginatedResult} from "../../../entities/pagination";


type LogTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const LogTable = (props: LogTableProps) => {
    const {logs, addNewItems, clearAll} = props;

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<LogDto>>("/log", [["page", page], ["size", size], ...requirements])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("logType", "Log Type", {sortId: "logType"}),
        StringColumn("sourceClass", "Classname", {sortId: "sourceClass"}),
        StringColumn("componentAddress", "Component Address", {sortId: "component.server.address"}),
        StringColumn("message", "Message", {sortId: "message"}),
        StringColumn("componentType", "ComponentType", {sortId: "componentType"}),
        StringColumn("timestamp", "Timestamp", {sortId: "timestamp"})
    ];


    return <PaginatedTable
        data={logs}
        clearData={clearAll}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState) => ({
    logs: state.logs
});
const mapDispatchToProps = {
    addNewItems,
    clearAll
};

export default connect(mapStateToProps, mapDispatchToProps)(LogTable);


