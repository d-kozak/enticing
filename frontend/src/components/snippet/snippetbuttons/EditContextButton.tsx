import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import AddIcon from "@material-ui/icons/Add";
import React from "react";
import {SearchResult} from "../../../entities/SearchResult";

interface EditContextButtonProps {
    searchResult: SearchResult;
    requestContextExtension: (searchResult: SearchResult) => void
}

export const EditContextButton = ({searchResult, requestContextExtension}: EditContextButtonProps) => <Tooltip
    title={searchResult.payload.canExtend ? 'Extend the context' : 'Full length reached'}>
            <span>
                <IconButton disabled={!searchResult.payload.canExtend}
                            onClick={() => requestContextExtension(searchResult)}
                            color="primary">
                <AddIcon fontSize="small"/>
            </IconButton>
            </span>
</Tooltip>;