import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";

import {connect} from "react-redux";
import React from 'react';
import {ApplicationState} from "../../ApplicationState";
import ComplexSnippetView from "./ComplexSnippetView";
import MinimizedSnippetView from "./MinimizedSnippetView";
import {isDebugMode} from "../../reducers/selectors";
import * as H from "history";


const styles = (theme: Theme) => createStyles({});

type SnippetViewSwitchEnhancedProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {
    history: H.History,
    snippetId: string,
    openDocumentRequest: () => void
}

const SnippetViewSwitch = (props: SnippetViewSwitchEnhancedProps) => {
    const {debugMode, openDocumentRequest, history, snippetId} = props;
    if (debugMode) {
        return <ComplexSnippetView snippetId={snippetId} history={history} openDocumentRequest={openDocumentRequest}/>;
    } else {
        return <MinimizedSnippetView snippetId={snippetId} history={history}
                                     openDocumentRequest={openDocumentRequest}/>;
    }
};


const mapStateToProps = (state: ApplicationState) => ({
    debugMode: isDebugMode(state)
});
const mapDispatchToProps = {};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(SnippetViewSwitch));