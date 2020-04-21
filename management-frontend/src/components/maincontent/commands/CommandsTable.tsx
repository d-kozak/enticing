import {makeStyles} from "@material-ui/core/styles";
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems} from "../../../reducers/commandsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {Button} from "@material-ui/core";
import {useHistory} from "react-router";
import {CommandDto} from "../../../entities/CommandDto";

const useStyles = makeStyles({});

type CommandsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const CommandsTable = (props: CommandsTableProps) => {
    const classes = useStyles();
    const {commands, addNewItems} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number) => {
        getRequest<PaginatedResult<CommandDto>>("/command", [["page", page], ["size", size]])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("type", "Command Type"),
        StringColumn("state", "Command State"),
        StringColumn("arguments", "Arguments"),
        StringColumn("submittedAt", "Submitted at"),
        StringColumn("startAt", "Start at"),
        StringColumn("finishedAt", "Finished at"),
        CustomColumn<CommandDto, undefined>("submittedBy", "Submitted by",
            (prop, command) =>
                <Button onClick={() => history.push(`/user-management/${command.submittedBy}`)}>
                    {command.submittedBy}
                </Button>
        )
    ];

    return <PaginatedTable
        data={commands}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState) => ({
    commands: state.commands,
});
const mapDispatchToProps = {
    addNewItems,
};

export default connect(mapStateToProps, mapDispatchToProps)(CommandsTable);


