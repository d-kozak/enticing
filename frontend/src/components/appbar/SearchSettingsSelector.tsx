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


const styles = (theme: Theme) => createStyles({});

type SearchSettingsSelectorProps =
    WithStyles<typeof styles>
    & ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & {}

const options = [
    'Show some love to Material-UI',
    'Show all notification content',
    'Hide sensitive notification content',
    'Hide all notification content',
];

const SearchSettingsSelector = (props: SearchSettingsSelectorProps) => {
    const {} = props;

    const [anchorElem, setAnchorElem] = useState<HTMLElement | null>(null)
    const [selectedIndex, setSelectedIndex] = useState(1)


    return <div>
        <List component="nav">
            <ListItem
                button
                aria-haspopup="true"
                aria-controls="lock-menu"
                aria-label="When device is locked"
                onClick={(event) => setAnchorElem(event.currentTarget)}
            >
                <ListItemText
                    primary="When device is locked"
                    secondary={options[selectedIndex]}
                />
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