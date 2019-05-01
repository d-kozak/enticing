import React from "react";
import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core/es";
import withStyles from "@material-ui/core/es/styles/withStyles";
import NotLoggedInButtons from "./NotLoggedInButtons";
import LoggedInButtons from "./LoggedInButtons";


const styles = createStyles({});

export interface MainPageAppBarButtonsProps extends WithStyles<typeof styles> {
    handleLogout: () => void,
    isLoggedIn: boolean,
    isAdmin: boolean
}

const AppBarButtons = (props: MainPageAppBarButtonsProps) => {
    const {isLoggedIn, isAdmin, handleLogout} = props;

    return <React.Fragment>
        {isLoggedIn ?
            <LoggedInButtons isAdmin={isAdmin} handleLogout={handleLogout}/>
            : <NotLoggedInButtons/>
        }
    </React.Fragment>

}

export default withStyles(styles, {withTheme: true})(AppBarButtons)