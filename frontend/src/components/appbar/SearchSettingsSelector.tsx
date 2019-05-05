import createStyles from "@material-ui/core/es/styles/createStyles";
import {Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {AppState} from "../../reducers/RootReducer";
import {connect} from "react-redux";
import React, {useState} from 'react';
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import Typography from "@material-ui/core/Typography";
import {SearchSettings} from "../../entities/SearchSettings";
import {searchSettingsSelectedRequestAction} from "../../actions/UserActions";
import LinkTo from "../utils/linkTo";
import {selectedSearchSettingsIndexSelector} from "../../reducers/selectors";


const styles = (theme: Theme) => createStyles({
    listItem: {
        color: "white"
    }
});

type SearchSettingsSelectorProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {}

const SearchSettingsSelector = (props: SearchSettingsSelectorProps) => {
    const {classes, isAdmin, searchSettings, selectSearchSettings, selectedSearchSettingsIndex} = props;

    const [anchorElem, setAnchorElem] = useState<HTMLElement | null>(null)

    return <div>
        <List component="nav">
            <ListItem
                button
                aria-haspopup="true"
                aria-controls="lock-menu"
                aria-label="Search Settings"
                onClick={(event) => setAnchorElem(event.currentTarget)}
            >
                <Typography variant="button" color="inherit">Using: </Typography>
                <ListItemText className={classes.listItem}>
                    {selectedSearchSettingsIndex < searchSettings.length && <Typography variant="button"
                                                                                        color="inherit">{searchSettings[selectedSearchSettingsIndex].name}</Typography>}
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
            {searchSettings.map((settings, index) => (
                <MenuItem
                    key={index}
                    selected={index === selectedSearchSettingsIndex}
                    onClick={() => {
                        selectSearchSettings(settings)
                        setAnchorElem(null);
                    }}
                >
                    {settings.name}
                </MenuItem>
            ))}
            <MenuItem onClick={() => setAnchorElem(null)} component={LinkTo("/search-settings")}>
                {isAdmin ? 'Edit settings' : 'Show details'}
            </MenuItem>
        </Menu>
    </div>
};


const mapStateToProps = (state: AppState) => ({
    isAdmin: state.user.isAdmin,
    searchSettings: state.searchSettings.settings,
    selectedSearchSettingsIndex: selectedSearchSettingsIndexSelector(state)
});
const mapDispatchToProps = {
    selectSearchSettings: searchSettingsSelectedRequestAction as (settings: SearchSettings) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(SearchSettingsSelector));