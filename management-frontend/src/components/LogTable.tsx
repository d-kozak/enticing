import {makeStyles} from "@material-ui/core/styles";
import {ApplicationState} from "../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable, {Column} from "./PaginatedTable";
import {addNewItems} from "../reducers/logReducer";
import {getRequest} from "../network/requests";
import {LogDto} from "../entities/LogDto";
import {PaginatedResult} from "../entities/pagination";
import {Button, TableCell} from "@material-ui/core";


const useStyles = makeStyles({});

type LogTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const LogTable = (props: LogTableProps) => {
    const classes = useStyles();
    const {logs, addNewItems} = props;

    const moreData = (page: number, size: number) => {
        getRequest<PaginatedResult<LogDto>>("/log", [["page", page], ["size", size]])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<Column<any>> = [
        {
            id: "logType",
            label: "Log Type",
            format(input: any): string {
                return input;
            }
        },
        {
            id: "className",
            label: "Classname",
            format(input: any): string {
                return input;
            }
        },
        {
            id: "componentAddress",
            label: "Component Address",
            format(input: any): string {
                return input;
            }
        },
        {
            id: "componentType",
            label: "ComponentType",
            format(input: any): string {
                return input;
            }
        },
        {
            id: "timestamp",
            label: "Timestamp",
            format(input: any): string {
                return input;
            }
        },
        {
            id: "message",
            label: "Message",
            format(input: any): string {
                return input;
            }
        }
    ];

    // todo this wont work so well :X
    const extraColumns = (log: LogDto) => <React.Fragment>
        <TableCell key={'btn1'} align={"right"}>
            <Button>Smth with log ${log}</Button>
        </TableCell>
    </React.Fragment>


    return <PaginatedTable
        data={logs}
        columns={columns}
        requestPage={moreData}
    />
};


const mapStateToProps = (state: ApplicationState) => ({
    logs: state.logs
});
const mapDispatchToProps = {
    addNewItems
};

export default connect(mapStateToProps, mapDispatchToProps)(LogTable);


