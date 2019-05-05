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

const calculateSelectedIndex = (searchSettings: Array<SearchSettings>, selectedSettings: number | null): number => {
    if (selectedSettings == null) {
        for (let i in searchSettings) {
            if (searchSettings[i].isDefault) {
                return Number(i)
            }
        }
        return 0
    }
    for (let i in searchSettings) {
        if (searchSettings[i].id == selectedSettings) {
            return Number(i)
        }
    }
    return 0
}

const SearchSettingsSelector = (props: SearchSettingsSelectorProps) => {
    const {classes, searchSettings, selectedSettings, selectSearchSettings} = props;

    const [anchorElem, setAnchorElem] = useState<HTMLElement | null>(null)

    const selectedIndex = calculateSelectedIndex(searchSettings, selectedSettings);

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
                    {selectedIndex < searchSettings.length && <Typography variant="button"
                                                                          color="inherit">{searchSettings[selectedIndex].name}</Typography>}
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
                    selected={index === selectedIndex}
                    onClick={() => {
                        selectSearchSettings(settings)
                        setAnchorElem(null);
                    }}
                >
                    {settings.name}
                </MenuItem>
            ))}
        </Menu>
    </div>
};


const mapStateToProps = (state: AppState) => ({
    searchSettings: state.searchSettings.settings,
    selectedSettings: state.user.selectedSettings
});
const mapDispatchToProps = {
    selectSearchSettings: searchSettingsSelectedRequestAction as (settings: SearchSettings) => void
};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(SearchSettingsSelector));