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

const options = [
    'Select Search Settings',
    'Test index',
    'Wikipedia 2018',
    'CC-2017',
];

const SearchSettingsSelector = (props: SearchSettingsSelectorProps) => {
    const {classes} = props;

    const [anchorElem, setAnchorElem] = useState<HTMLElement | null>(null)
    const [selectedIndex, setSelectedIndex] = useState(1)


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
                    <Typography variant="button" color="inherit">{options[selectedIndex]}</Typography>
                </ListItemText>
            </ListItem>
        </List>
        <Menu
            id="lock-menu"
            anchorEl={anchorElem}
            open={anchorElem !== null}
            onClose={() => setAnchorElem(null)}
        >
            {options.map((option, index) => (
                <MenuItem
                    key={option}
                    disabled={index === 0}
                    selected={index === selectedIndex}
                    onClick={() => {
                        setSelectedIndex(index);
                        setAnchorElem(null);
                    }}
                >
                    {option}
                </MenuItem>
            ))}
        </Menu>
    </div>
};


const mapStateToProps = (state: AppState) => ({});
const mapDispatchToProps = {};

export default withStyles(styles, {withTheme: true})(connect(mapStateToProps, mapDispatchToProps)(SearchSettingsSelector));