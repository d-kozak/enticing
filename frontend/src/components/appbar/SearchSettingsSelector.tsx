import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {ApplicationState} from "../../reducers/ApplicationState";
import {connect} from "react-redux";
import React, {useState} from 'react';
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import Typography from "@material-ui/core/Typography";
import {SearchSettings} from "../../entities/SearchSettings";
import {selectSearchSettingsRequest} from "../../reducers/UserReducer";
import LinkTo from "../utils/linkTo";
import {getSelectedSearchSettings, isLoggedIn, isUserAdmin} from "../../reducers/selectors";
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import EditIcon from '@material-ui/icons/Edit';
import InfoIcon from '@material-ui/icons/Info';
import Divider from "@material-ui/core/Divider";
import {openSnackbar} from "../../reducers/SnackBarReducer";


const styles = (theme: Theme) => createStyles({
    listItem: {
        color: "white"
    },
    iconSmall: {
        margin: '0px 5px 0px 0px',
        fontSize: 20,
    }
});

type SearchSettingsSelectorProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {}

const SearchSettingsSelector = (props: SearchSettingsSelectorProps) => {
    const {classes, isAdmin, isLoggedIn, searchSettings, selectSearchSettings, selectedSearchSettings, openSnackbar} = props;

    const [anchorElem, setAnchorElem] = useState<HTMLElement | null>(null)


    const selectedSearchSettingsIndex = selectedSearchSettings !== null ? selectedSearchSettings.id : "-1"
    if (!selectedSearchSettings) {
        openSnackbar('No search settings is selected!');
    }

    return <div>
        <List component="nav">
            <ListItem
                button
                aria-haspopup="true"
                aria-controls="lock-menu"
                aria-label="Search Settings"
                onClick={(event) => setAnchorElem(event.currentTarget)}
            >
                {selectedSearchSettings && selectedSearchSettings.private &&
                <VisibilityOffIcon className={classes.iconSmall}/>}
                <Typography variant="button" color="inherit">Using: </Typography>
                <ListItemText className={classes.listItem}>
                    {selectedSearchSettings && <Typography variant="button"
                                                           color="inherit">{selectedSearchSettings.name}</Typography>}
                </ListItemText>
            </ListItem>
        </List>
        <Menu
            id="lock-menu"
            anchorEl={anchorElem}
            open={anchorElem !== null}
            onClose={() => setAnchorElem(null)}
        >
            <MenuItem disabled={true}>
                Select configuration
            </MenuItem>
            {Object.values(searchSettings).map((settings) => (
                <MenuItem
                    key={settings.id}
                    selected={settings.id === selectedSearchSettingsIndex}
                    onClick={() => {
                        selectSearchSettings(settings, selectedSearchSettingsIndex, isLoggedIn)
                        setAnchorElem(null);
                    }}
                >
                    {settings.private && <VisibilityOffIcon className={classes.iconSmall}/>}
                    {settings.name}
                </MenuItem>
            ))}
            <Divider/>
            <MenuItem onClick={() => setAnchorElem(null)} component={LinkTo("/search-settings")}>
                {isAdmin ?
                    <React.Fragment>
                        <EditIcon className={classes.iconSmall}/> Edit
                    </React.Fragment> : <React.Fragment>
                        <InfoIcon className={classes.iconSmall}/> Show details
                    </React.Fragment>}
            </MenuItem>
        </Menu>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    isAdmin: isUserAdmin(state),
    isLoggedIn: isLoggedIn(state),
    searchSettings: state.searchSettings.settings,
    selectedSearchSettings: getSelectedSearchSettings(state),
    openSnackbar
});
const mapDispatchToProps = {
    selectSearchSettings: selectSearchSettingsRequest as (settings: SearchSettings, previousSelectedIndex: string, isLoggedIn: boolean) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(SearchSettingsSelector));