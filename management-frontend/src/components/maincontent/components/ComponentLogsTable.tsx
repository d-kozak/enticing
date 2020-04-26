import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addLogsToComponent, clearComponentLogs} from "../../../reducers/componentsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {LogDto} from "../../../entities/LogDto";

type ComponentLogsTableSimpleProps = {
    componentId: string
};

type ComponentLogsTableProps =
    ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & ComponentLogsTableSimpleProps

const ComponentLogsTable = (props: ComponentLogsTableProps) => {
    const {component, addLogsToComponent, clearComponentLogs} = props;

    if (!component) {
        return <div>no data</div>;
    }

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<LogDto>>(`/log/${component.id}`, [["page", page], ["size", size], ...requirements])
            .then(res => {
                addLogsToComponent({...res, componentId: component.id});
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("logType", "Log Type", {
            sortId: "logType",
            filterOptions: ["DEBUG", "INFO", "PERF", "WARN", "ERROR"]
        }),
        StringColumn("className", "Classname", {sortId: "className"}),
        StringColumn("message", "Message", {sortId: "message"}),
        StringColumn("timestamp", "Timestamp", {sortId: "timestamp"})
    ];

    return <PaginatedTable
        data={component.logs}
        clearData={() => clearComponentLogs(component.id)}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState, props: ComponentLogsTableSimpleProps) => ({
    component: state.components.elements[props.componentId],
});
const mapDispatchToProps = {
    addLogsToComponent,
    clearComponentLogs
};

export default connect(mapStateToProps, mapDispatchToProps)(ComponentLogsTable);


