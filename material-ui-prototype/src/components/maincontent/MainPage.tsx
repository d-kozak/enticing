import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import CenteredSearchBar from "../searchbar/CenteredSearchBar";

const styles = createStyles({});


export interface MainPageProps extends WithStyles<typeof styles> {
    startSearching: (query: string) => void;
}

const MainPage = (props: MainPageProps) => {
    const {startSearching} = props;
    return <CenteredSearchBar startSearching={startSearching}/>
};

export default withStyles(styles, {
    withTheme: true
})(MainPage)