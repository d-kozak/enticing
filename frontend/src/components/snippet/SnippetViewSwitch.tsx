import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";

import {connect} from "react-redux";
import React from 'react';
import {ApplicationState} from "../../ApplicationState";
import ComplexSnippetView from "./ComplexSnippetView";
import MinimizedSnippetView from "./MimimizedSnippetView";


const styles = (theme: Theme) => createStyles({});

type SnippetViewSwitchEnhancedProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {
    snippetId: string,
    openDocumentRequest: () => void
}

const SnippetViewSwitch = (props: SnippetViewSwitchEnhancedProps) => {
    const {viewFormat, openDocumentRequest, snippetId} = props;
    switch (viewFormat) {
        case "COMPLEX":
            return <ComplexSnippetView snippetId={snippetId} openDocumentRequest={openDocumentRequest}/>;
        case "MINIMIZED":
            return <MinimizedSnippetView snippetId={snippetId} openDocumentRequest={openDocumentRequest}/>;
    }
    return <div>
        ERR - switch not exhaustive
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    viewFormat: state.searchResult.snippetConfig.viewFormat
});
const mapDispatchToProps = {};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(SnippetViewSwitch));