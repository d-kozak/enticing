import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {IntColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems, clearAll} from "../../../reducers/perfsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {PerfDto} from "../../../entities/PerfDto";


type PerfTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const PerfTable = (props: PerfTableProps) => {
    const {perfLogs, clearAll, addNewItems} = props;

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<PerfDto>>("/perf", [["page", page], ["size", size], ...requirements])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("operationId", "OperationId", {sortId: "operationId"}),
        StringColumn("arguments", "Arguments", {sortId: "arguments"}),
        StringColumn("sourceClass", "Classname", {sortId: "sourceClass"}),
        IntColumn("duration", "Duration", {sortId: "duration"}),
        StringColumn("result", "Result", {sortId: "result"}),
        StringColumn("componentAddress", "Component Address", {sortId: "component.server.address"}),
        StringColumn("componentType", "ComponentType", {sortId: "componentType"}),
        StringColumn("timestamp", "Timestamp", {sortId: "timestamp"})
    ];


    return <PaginatedTable
        data={perfLogs}
        clearData={clearAll}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState) => ({
    perfLogs: state.perfLogs
});
const mapDispatchToProps = {
    addNewItems,
    clearAll
};

export default connect(mapStateToProps, mapDispatchToProps)(PerfTable);


