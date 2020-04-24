import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems} from "../../../reducers/buildsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {Button, IconButton, Tooltip, Typography} from "@material-ui/core";
import {useHistory} from "react-router";
import {CommandDto} from "../../../entities/CommandDto";
import InfoIcon from "@material-ui/icons/Info";


type BuildsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    restrictions?: Array<[string, string | number]>
}

const BuildsTable = (props: BuildsTableProps) => {
    let {builds, addNewItems, restrictions} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number) => {
        getRequest<PaginatedResult<CommandDto>>("/command", [["type", "LOCAL_TEST"], ["page", page], ["size", size], ...(restrictions || [])])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("state", "Command State"),
        StringColumn("submittedAt", "Submitted at"),
        StringColumn("startAt", "Start at"),
        StringColumn("finishedAt", "Finished at"),
        CustomColumn<CommandDto, undefined>("submittedBy", "Submitted by",
            (prop, command) =>
                <Button onClick={() => history.push(`/user-management/${command.submittedBy}`)}>
                    {command.submittedBy}
                </Button>
        ),
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
            columns={columns}
            requestPage={requestPage}
        />
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    builds: state.builds,
});
const mapDispatchToProps = {
    addNewItems,
};

export default connect(mapStateToProps, mapDispatchToProps)(BuildsTable);


