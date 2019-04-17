import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import CenteredSearchBar from "../searchbar/CenteredSearchBar";

import * as H from 'history';

const styles = createStyles({});


export interface MainPageProps extends WithStyles<typeof styles> {
    history: H.History
}

const MainPage = (props: MainPageProps) => {
    const {history} = props;
    return <CenteredSearchBar history={history}/>
};

export default withStyles(styles, {
    withTheme: true
})(MainPage)