import withStyles, {WithStyles} from "@material-ui/core/es/styles/withStyles";
import createStyles from "@material-ui/core/es/styles/createStyles";
import {Route, Switch} from "react-router";
import React from "react";
import MainPageAppBarButtons from "./AppBarButtons";

const styles = createStyles({});

export interface AppBarButtonsProps extends WithStyles<typeof styles> {
    handleLogout: () => void,
    isLoggedIn: boolean,
    isAdmin: boolean,
}

const AppBarButtonsRouter = (props: AppBarButtonsProps) => {
    const {isLoggedIn, isAdmin, handleLogout} = props;

    return <Switch>
        <Route path="/" exact
               render={() => <MainPageAppBarButtons isAdmin={isAdmin} isLoggedIn={isLoggedIn}
                                                    handleLogout={handleLogout}/>}/>
        <Route path="/search"
               render={() => <MainPageAppBarButtons isAdmin={isAdmin} isLoggedIn={isLoggedIn}
                                                    handleLogout={handleLogout}/>}/>
        <Route path="/settings" exact
               render={() => <MainPageAppBarButtons isAdmin={isAdmin} isLoggedIn={isLoggedIn}
                                                    handleLogout={handleLogout}/>}/>
        <Route path="/users" exact
               render={() => <MainPageAppBarButtons isAdmin={isAdmin} isLoggedIn={isLoggedIn}
                                                    handleLogout={handleLogout}/>}/>
    </Switch>
};


export default withStyles(styles, {withTheme: true})(AppBarButtonsRouter)