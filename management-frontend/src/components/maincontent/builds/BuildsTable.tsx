import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, DateTimeColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems, clearAll} from "../../../reducers/buildsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {Button, IconButton, Tooltip, Typography} from "@material-ui/core";
import {useHistory} from "react-router";
import {CommandDto} from "../../../entities/CommandDto";
import InfoIcon from "@material-ui/icons/Info";
import MaintainerOnly from "../../protectors/MaintainerOnly";
import RequestNewBuildDialog from "./RequestNewBuildDialog";


type BuildsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    restrictions?: Array<[string, string | number]>
}

const BuildsTable = (props: BuildsTableProps) => {
    let {builds, clearAll, addNewItems, restrictions} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<CommandDto>>("/command", [["type", "BUILD"], ["page", page], ["size", size], ...(restrictions || []), ...requirements])
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
        DateTimeColumn("submittedAt", "Submitted at", {sortId: "submittedAt"}),
        DateTimeColumn("startAt", "Start at", {sortId: "startAt"}),
        DateTimeColumn("finishedAt", "Finished at", {sortId: "finishedAt"}),
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
        <MaintainerOnly>
            <RequestNewBuildDialog/>
        </MaintainerOnly>
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


