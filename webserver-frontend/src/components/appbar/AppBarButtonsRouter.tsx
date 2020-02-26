import withStyles, {WithStyles} from "@material-ui/core/es/styles/withStyles";
import createStyles from "@material-ui/core/es/styles/createStyles";
import {Route, Switch} from "react-router";
import React from "react";
import CommonAppBarButtons from "./CommonAppBarButtons";
import LinkTo from "../utils/linkTo";
import Button from "@material-ui/core/es/Button";
import {ApplicationState} from "../../ApplicationState";
import {getUser, isLoggedIn, logoutRequest} from "../../reducers/UserReducer";
import {connect} from "react-redux";


const styles = createStyles({});

export type AppBarButtonsProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const AppBarButtonsRouter = (props: AppBarButtonsProps) => {
    const {user, isUserLoggedId, handleLogout} = props;

    const LoginLink = LinkTo("/login");
    return <Switch>
        <Route path={["/", "/search", "/settings", "/users", "/search-settings"]} exact
               render={() => isUserLoggedId ? <CommonAppBarButtons user={user} handleLogout={handleLogout}/> :
                   <Button component={LoginLink} color="inherit">Login</Button>}/>}/>
    </Switch>
};


const mapStateToProps = (state: ApplicationState) => ({
    user: getUser(state),
    isUserLoggedId: isLoggedIn(state)
});

const mapDispatchToProps = {
    handleLogout: logoutRequest as () => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(AppBarButtonsRouter))