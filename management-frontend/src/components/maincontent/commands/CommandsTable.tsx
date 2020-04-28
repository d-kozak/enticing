import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, DateTimeColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems, clearAll} from "../../../reducers/commandsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {Button, IconButton, Tooltip, Typography} from "@material-ui/core";
import {useHistory} from "react-router";
import {CommandDto} from "../../../entities/CommandDto";
import InfoIcon from "@material-ui/icons/Info";


type CommandsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    restrictions?: Array<[string, string | number]>
}

const CommandsTable = (props: CommandsTableProps) => {
    const {commands, clearAll, addNewItems, restrictions} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<CommandDto>>("/command", [["page", page], ["size", size], ...(restrictions || []), ...requirements])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("type", "Command Type", {
            sortId: "type",
            filterOptions: ["START_INDEX_SERVER", "KILL_INDEX_SERVER", "START_WEBSERVER", "KILL_WEBSERVER", "START_MANAGEMENT_SERVER", "KILL_MANAGEMENT_SERVER", "LOCAL_TEST"]
        }),
        StringColumn("state", "Command State", {
            sortId: "state",
            filterOptions: ["ENQUED", "RUNNING", "FINISHED", "FAILED"]
        }),
        StringColumn("arguments", "Arguments", {sortId: "arguments"}),
        DateTimeColumn("submittedAt", "Submitted at", {sortId: "submittedAt"}),
        DateTimeColumn("startAt", "Start at", {sortId: "startAt"}),
        DateTimeColumn("finishedAt", "Finished at", {sortId: "finishedAt"}),
        CustomColumn<CommandDto, undefined>("submittedBy", "Submitted by",
            (prop, command) =>
                <Button onClick={() => history.push(`/user-management/${command.submittedBy}`)}>
                    {command.submittedBy}
                </Button>
            , {sortId: "submittedBy"}),
        CustomColumn<CommandDto, undefined>("commandDetails", "Command details",
            (prop, command) => <Tooltip title="Command details">
                <IconButton onClick={() => history.push(`/command/${command.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <div>
        <Typography variant="h3">Commands</Typography>
        <PaginatedTable
            data={commands}
            clearData={clearAll}
            columns={columns}
            requestPage={requestPage}/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    commands: state.commands,
});
const mapDispatchToProps = {
    addNewItems,
    clearAll
};

export default connect(mapStateToProps, mapDispatchToProps)(CommandsTable);


