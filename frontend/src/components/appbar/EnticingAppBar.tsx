import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core/es";
import {Link} from "react-router-dom";
import SearchSettingsSelector from "./SearchSettingsSelector";
import WarningIcon from "@material-ui/icons/Warning";
import {logoutRequest} from "../../reducers/UserReducer";
import withStyles from "@material-ui/core/es/styles/withStyles";
import {connect} from "react-redux";
import {isUserAdmin} from "../../reducers/selectors";
import {ApplicationState} from "../../ApplicationState";
import AppBarButtonsRouter from "./AppBarButtonsRouter";
import {IconButton} from "@material-ui/core";


const styles = createStyles({
    root: {
        flexGrow: 1,
    },
    grow: {
        flexGrow: 1,
    }
});

export type EnticingAppBarProps =
    WithStyles<typeof styles>
    & typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const EnticingAppBar = (props: EnticingAppBarProps) => {
    const {classes, user, isAdmin, handleLogout, searchSettings} = props;

    const clearDecorationsRef = (node: HTMLAnchorElement | null) => {
        if (node) {
            node.style.textDecoration = 'none';
            node.style.color = 'white';
        }
    }

    return <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <div className={classes.grow}>
                    <Link to="/" innerRef={clearDecorationsRef}>
                        <Typography variant="h6" color="inherit">
                            Enticing
                        </Typography>
                    </Link>
                </div>
                {Object.values(searchSettings).length > 0 && <SearchSettingsSelector/>}
                {Object.values(searchSettings).length == 0 &&
                <Link innerRef={clearDecorationsRef} to="/search-settings">
                    <IconButton color="inherit">
                        <WarningIcon/>
                        <Typography style={{margin: '5px'}} color="inherit" variant="body1">No search settings
                            available</Typography>
                    </IconButton>
                </Link>}
                <AppBarButtonsRouter user={user} handleLogout={handleLogout}/>
            </Toolbar>
        </AppBar>
    </div>
};

const mapStateToProps = (state: ApplicationState) => ({
    user: state.userState.user,
    isAdmin: isUserAdmin(state),
    searchSettings: state.searchSettings.settings
});

const mapDispatchToProps = {
    handleLogout: logoutRequest as () => void
};

export default withStyles(styles, {withTheme: true})((connect(mapStateToProps, mapDispatchToProps))(EnticingAppBar));
