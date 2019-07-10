import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import AddIcon from "@material-ui/icons/Add";
import React from "react";
import {Match} from "../../../entities/Snippet";

interface EditContextButtonProps {
    searchResult: Match;
    requestContextExtension: (searchResult: Match) => void
}

export const EditContextButton = ({searchResult, requestContextExtension}: EditContextButtonProps) => <Tooltip
    title={searchResult.canExtend ? 'Extend the context' : 'Full length reached'}>
            <span>
                <IconButton disabled={!searchResult.canExtend}
                            onClick={() => requestContextExtension(searchResult)}
                            color="primary">
                <AddIcon fontSize="small"/>
            </IconButton>
            </span>
</Tooltip>;