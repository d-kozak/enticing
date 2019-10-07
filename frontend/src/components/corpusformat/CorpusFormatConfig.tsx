import {CircularProgress, createStyles, WithStyles, withStyles} from "@material-ui/core";
import {ApplicationState} from "../../ApplicationState";
import {connect} from "react-redux";
import * as React from "react";
import {useEffect} from "react";
import {SearchSettings} from "../../entities/SearchSettings";
import {Node} from 'react-checkbox-tree';

import Button from '@material-ui/core/Button';

import 'react-checkbox-tree/lib/react-checkbox-tree.css';
import Typography from "@material-ui/core/es/Typography";
import {loadCorpusFormatRequest} from "../../reducers/SearchSettingsReducer";
import {getUser, isLoggedIn, loadSelectedMetadataRequest} from "../../reducers/UserReducer";
import CorpusFormatSelector from "./CorpusFormatSelector";

const styles = createStyles({});


export type CorpusFormatSelectorProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>
    & {
    searchSettings: SearchSettings
}


const CorpusFormatConfig = (props: CorpusFormatSelectorProps) => {
    const {searchSettings, user, loadCorpusFormat, loadSelectedMetadata} = props;

    useEffect(() => {
        if (!searchSettings.corpusFormat) {
            loadCorpusFormat(searchSettings);
        }
    }, [searchSettings]);


    useEffect(() => {
        if (!user.selectedMetadata[searchSettings.id]) {
            loadSelectedMetadata(searchSettings.id);
        }
    }, [user]);

    if (!searchSettings.corpusFormat) {
        return <div>
            <Typography variant="body1">Loading corpus format...</Typography>
            <CircularProgress/>
            <Button onClick={() => loadCorpusFormat(searchSettings)}>Try again</Button>
        </div>
    } else if (!user.selectedMetadata[searchSettings.id]) {
        return <div>
            <Typography variant="body1">Loading selected metadata...</Typography>
            <CircularProgress/>
            <Button onClick={() => loadSelectedMetadata(searchSettings.id)}>Try again</Button>
        </div>
    } else {
        return <CorpusFormatSelector searchSettings={searchSettings} corpusFormat={searchSettings.corpusFormat}
                                     selectedMetadata={user.selectedMetadata[searchSettings.id]}/>
    }
};


const mapStateToProps = (state: ApplicationState) => ({
    isLoggedIn: isLoggedIn(state),
    user: getUser(state)
});

const mapDispatchToProps = {
    loadCorpusFormat: loadCorpusFormatRequest as (settings: SearchSettings) => void,
    loadSelectedMetadata: loadSelectedMetadataRequest as (settingsId: string) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(CorpusFormatConfig))