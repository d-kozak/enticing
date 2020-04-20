import {makeStyles} from "@material-ui/core/styles";
import {ApplicationState} from "../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "./pagination/PaginatedTable";
import {ActionColumn, PaginatedTableColumn, StringColumn} from "./pagination/PaginatedTableColumn"
import {addNewItems} from "../reducers/logReducer";
import {getRequest} from "../network/requests";
import {LogDto} from "../entities/LogDto";
import {PaginatedResult} from "../entities/pagination";
import {Button} from "@material-ui/core";


const useStyles = makeStyles({});

type LogTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const LogTable = (props: LogTableProps) => {
    const classes = useStyles();
    const {logs, addNewItems} = props;

    const requestPage = (page: number, size: number) => {
        getRequest<PaginatedResult<LogDto>>("/log", [["page", page], ["size", size]])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("logType", "Log Type"),
        StringColumn("className", "Classname"),
        StringColumn("componentAddress", "Component Address"),
        StringColumn("message", "Message"),
        StringColumn("componentType", "ComponentType"),
        StringColumn("timestamp", "Timestamp"),
        ActionColumn<LogDto, undefined>("log", "log it", (prop, item) => <Button>{item.message}</Button>)
    ];


    return <PaginatedTable
        data={logs}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState) => ({
    logs: state.logs
});
const mapDispatchToProps = {
    addNewItems
};

export default connect(mapStateToProps, mapDispatchToProps)(LogTable);


