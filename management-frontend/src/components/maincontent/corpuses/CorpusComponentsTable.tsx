import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, IntColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {deleteRequest, getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {IconButton, Tooltip} from "@material-ui/core";
import {useHistory} from "react-router";
import InfoIcon from "@material-ui/icons/Info";
import DeleteIcon from "@material-ui/icons/Delete";
import {ComponentInfo} from "../../../entities/ComponentInfo";
import {LastHeartbeatColumn} from "../components/LastHeartbeatColumn";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {addComponentsToCorpus, clearComponentsFromCorpus} from "../../../reducers/corpusesReducer";

type SimpleProps = {
    corpusId: string
};

type CorpusComponentsTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & SimpleProps

const CorpusComponentsTable = (props: CorpusComponentsTableProps) => {
    const {corpus, openSnackbarAction, addComponentsToCorpus, clearComponentsFromCorpus, corpusId} = props;

    const history = useHistory();

    if (!corpus) {
        return <div>no data</div>;
    }

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<ComponentInfo>>(`/component`, [["corpusId", corpusId], ["page", page], ["size", size], ...requirements])
            .then(res => {
                addComponentsToCorpus({...res, corpusId: corpus.id});
            })
            .catch(err => {
                console.error(err);
            })
    }

    const removeComponentFromCorpus = (component: ComponentInfo) => {
        deleteRequest(`/corpus/${corpus.id}/${component.id}`)
            .then(() => {
                openSnackbarAction("Component removed");
            })
            .catch(err => {
                console.error(err);
                openSnackbarAction("Failed to delete component from corpus");
            })
    };

    const columns: Array<PaginatedTableColumn<any, any>> = [
        IntColumn("port", "Port", {sortId: "port"}),
        StringColumn("type", "Component Type", {sortId: "type"}),
        StringColumn("buildId", "Build", {
            sortId: "buildId"
        }),
        StringColumn("status", "Status", {
            sortId: "status"
        }),
        LastHeartbeatColumn("lastHeartbeat", "Last heartbeat", {sortId: "lastHeartbeat"}),
        CustomColumn<ComponentInfo, undefined>("componentDetails", "Component Details",
            (prop, component) => <Tooltip title="Component details">
                <IconButton onClick={() => history.push(`/component/${component.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        ),
        CustomColumn<ComponentInfo, undefined>("removeComponent", "Remove Component from Corpus",
            (prop, component) => <Tooltip title="Remove Component from Corpus">
                <IconButton onClick={() => removeComponentFromCorpus(component)}>
                    <DeleteIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <PaginatedTable
        data={corpus.components}
        clearData={() => clearComponentsFromCorpus(corpus.id)}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState, props: SimpleProps) => ({
    corpus: state.corpuses.elements[props.corpusId],
});

const mapDispatchToProps = {
    openSnackbarAction,
    addComponentsToCorpus,
    clearComponentsFromCorpus
};

export default connect(mapStateToProps, mapDispatchToProps)(CorpusComponentsTable);


