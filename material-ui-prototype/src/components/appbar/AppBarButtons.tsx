import withStyles, {WithStyles} from "@material-ui/core/es/styles/withStyles";
import createStyles from "@material-ui/core/es/styles/createStyles";
import {Route, Switch} from "react-router";
import React from "react";
import MainPageAppBarButtons from "./MainPageAppBarButtons";

const styles = createStyles({});

export interface AppBarButtonsProps extends WithStyles<typeof styles> {
    handleLogout: () => void,
    isLoggedIn: boolean
}

const AppBarButtons = (props: AppBarButtonsProps) => {
    const {isLoggedIn, handleLogout} = props;

    return <Switch>
        <Route path="/" exact
               render={() => <MainPageAppBarButtons isLoggedIn={isLoggedIn} handleLogout={handleLogout}/>}/>
        <Route path="/search"
               render={() => <MainPageAppBarButtons isLoggedIn={isLoggedIn} handleLogout={handleLogout}/>}/>
        <Route path="/settings" exact
               render={() => <MainPageAppBarButtons isLoggedIn={isLoggedIn} handleLogout={handleLogout}/>}/>
    </Switch>
};


export default withStyles(styles, {withTheme: true})(AppBarButtons)