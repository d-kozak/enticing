import {makeStyles} from "@material-ui/core/styles";
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {IntColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems} from "../../../reducers/perfsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {PerfDto} from "../../../entities/PerfDto";


const useStyles = makeStyles({});

type PerfTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const PerfTable = (props: PerfTableProps) => {
    const classes = useStyles();
    const {perfLogs, addNewItems} = props;

    const requestPage = (page: number, size: number) => {
        getRequest<PaginatedResult<PerfDto>>("/perf", [["page", page], ["size", size]])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("operationId", "OperationId"),
        StringColumn("arguments", "Arguments"),
        StringColumn("className", "Classname"),
        IntColumn("duration", "Duration"),
        StringColumn("result", "Result"),
        StringColumn("componentAddress", "Component Address"),
        StringColumn("componentType", "ComponentType"),
        StringColumn("timestamp", "Timestamp")
    ];


    return <PaginatedTable
        data={perfLogs}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState) => ({
    perfLogs: state.perfLogs
});
const mapDispatchToProps = {
    addNewItems
};

export default connect(mapStateToProps, mapDispatchToProps)(PerfTable);


