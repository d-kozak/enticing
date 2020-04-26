import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems, clearAll} from "../../../reducers/buildsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {Button, IconButton, Tooltip, Typography} from "@material-ui/core";
import {useHistory} from "react-router";
import {CommandDto} from "../../../entities/CommandDto";
import InfoIcon from "@material-ui/icons/Info";
import StartNewBuildDialog from "./StartNewBuildDialog";


type BuildsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    restrictions?: Array<[string, string | number]>
}

const BuildsTable = (props: BuildsTableProps) => {
    let {builds, clearAll, addNewItems, restrictions} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<CommandDto>>("/command", [["type", "LOCAL_TEST"], ["page", page], ["size", size], ...(restrictions || []), ...requirements])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("state", "Command State", {
            sortId: "state",
            filterOptions: ["ENQUED", "RUNNING", "FINISHED", "FAILED"]
        }),
        StringColumn("submittedAt", "Submitted at", {sortId: "submittedAt"}),
        StringColumn("startAt", "Start at", {sortId: "startAt"}),
        StringColumn("finishedAt", "Finished at", {sortId: "finishedAt"}),
        CustomColumn<CommandDto, undefined>("submittedBy", "Submitted by",
            (prop, command) =>
                <Button onClick={() => history.push(`/user-management/${command.submittedBy}`)}>
                    {command.submittedBy}
                </Button>
            , {sortId: "submittedBy.login"}),
        CustomColumn<CommandDto, undefined>("commandDetails", "Command details",
            (prop, command) => <Tooltip title="Command details">
                <IconButton onClick={() => history.push(`/command/${command.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <div>
        <Typography variant="h3">Builds</Typography>
        <PaginatedTable
            data={builds}
            clearData={clearAll}
            columns={columns}
            requestPage={requestPage}/>
        <StartNewBuildDialog/>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    builds: state.builds,
});
const mapDispatchToProps = {
    addNewItems,
    clearAll
};

export default connect(mapStateToProps, mapDispatchToProps)(BuildsTable);


