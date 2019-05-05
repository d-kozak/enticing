import withStyles, {WithStyles} from "@material-ui/core/es/styles/withStyles";
import createStyles from "@material-ui/core/es/styles/createStyles";
import {Route, Switch} from "react-router";
import React from "react";
import CommonAppBarButtons from "./CommonAppBarButtons";
import LinkTo from "../utils/linkTo";
import Button from "@material-ui/core/es/Button";

const styles = createStyles({});

export interface AppBarButtonsProps extends WithStyles<typeof styles> {
    handleLogout: () => void,
    isLoggedIn: boolean,
    isAdmin: boolean,
}

const AppBarButtonsRouter = (props: AppBarButtonsProps) => {
    const {isLoggedIn, isAdmin, handleLogout} = props;

    const LoginLink = LinkTo("/login");
    return <Switch>
        <Route path={["/", "/search", "/settings", "/users"]} exact
               render={() => isLoggedIn ? <CommonAppBarButtons isAdmin={isAdmin} handleLogout={handleLogout}/> :
                   <Button component={LoginLink} color="inherit">Login</Button>}/>}/>
    </Switch>
};


export default withStyles(styles, {withTheme: true})(AppBarButtonsRouter)