import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {CustomColumn, PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addNewItems, clearAll} from "../../../reducers/corpusesReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {IconButton, Tooltip, Typography} from "@material-ui/core";
import {useHistory} from "react-router";
import {Corpus} from "../../../entities/Corpus";
import InfoIcon from "@material-ui/icons/Info";
import MaintainerOnly from "../../protectors/MaintainerOnly";
import AddNewCorpusDialog from "./AddOrEditCorpusDialog";


type CorpusesTableProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const CorpusesTable = (props: CorpusesTableProps) => {
    const {corpuses, clearAll, addNewItems} = props;

    const history = useHistory();

    const requestPage = (page: number, size: number, requirements: Array<[string, string | number]>) => {
        getRequest<PaginatedResult<Corpus>>("/corpus", [["page", page], ["size", size], ...requirements])
            .then(res => {
                addNewItems(res)
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("name", "Corpus name", {
            sortId: "name",
        }),
        CustomColumn<Corpus, undefined>("components", "Components",
            (prop, corpus) => corpus.components.length
            , {sortId: "components"}),
        CustomColumn<Corpus, undefined>("corpusDetails", "Corpus details",
            (prop, corpus) => <Tooltip title="Corpus details">
                <IconButton onClick={() => history.push(`/corpus/${corpus.id}`)}>
                    <InfoIcon/>
                </IconButton>
            </Tooltip>
        )
    ];

    return <div>
        <Typography variant="h3">Corpuses</Typography>
        <PaginatedTable
            data={corpuses}
            clearData={clearAll}
            columns={columns}
            requestPage={requestPage}/>
        <MaintainerOnly>
            <AddNewCorpusDialog/>
        </MaintainerOnly>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    corpuses: state.corpuses,
});
const mapDispatchToProps = {
    addNewItems,
    clearAll
};

export default connect(mapStateToProps, mapDispatchToProps)(CorpusesTable);


