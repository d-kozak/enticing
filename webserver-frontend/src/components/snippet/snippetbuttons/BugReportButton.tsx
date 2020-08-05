import {SearchResult} from "../../../entities/SearchResult";
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import BugReportIcon from "@material-ui/icons/BugReport";
import React, {useState} from "react";
import BugReportDialog from "../../dialog/BugReportDialog";

type BugReportButtonProps = {
    searchResult: SearchResult
}

const BugReportButton = ({searchResult}: BugReportButtonProps) => {
    const [isOpen, setOpen] = useState(false);

    const openDialog = () => setOpen(true);
    const closeDialog = () => setOpen(false);

    return <React.Fragment>
        <Tooltip
            title="Report bug">
            <span>
                <IconButton onClick={openDialog}
                            color="primary">
                <BugReportIcon fontSize="small"/>
            </IconButton>
            </span>
        </Tooltip>
        <BugReportDialog
            searchResult={searchResult}
            open={isOpen}
            onSubmit={closeDialog}
            onClose={closeDialog}
        />
    </React.Fragment>
}

export default BugReportButton;

