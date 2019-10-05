import createStyles from "@material-ui/core/es/styles/createStyles";
import {FormControl, InputLabel, MenuItem, Select, Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import React, {useState} from 'react';
import {TwitterPicker} from 'react-color';

export type EntityColorConfig = { [entity: string]: string };

const styles = (theme: Theme) => createStyles({
    entitySelect: {
        marginTop: '5px',
        marginBottom: '15px'
    }
});

type EntityColorPickerProps = WithStyles<typeof styles> & {
    selectedColors: EntityColorConfig
    setSelectedColors: (config: EntityColorConfig) => void
}

const EntityColorPicker = (props: EntityColorPickerProps) => {
    const {selectedColors, setSelectedColors, classes} = props;
    if (Object.keys(selectedColors).length == 0) return <span>No Entity to set color for</span>;

    const [selectedEntity, setSelectedEntity] = useState(Object.keys(selectedColors)[0]);

    return <div>
        <FormControl className={classes.entitySelect}>
            <InputLabel htmlFor="selected-entity"/>
            <Select
                value={selectedEntity}
                onChange={(event) => setSelectedEntity(event.target.value)}
                inputProps={{
                    name: 'selected-entity',
                    id: 'selected-entity',
                }}
            >
                {Object.keys(selectedColors).map(entity => <MenuItem key={entity}
                                                                     value={entity}>{entity}</MenuItem>)}
            </Select>
        </FormControl>
        <TwitterPicker color={selectedColors[selectedEntity]} onChange={(color) => setSelectedColors(
            {...selectedColors, [selectedEntity]: color.hex}
        )}/>
    </div>
};


export default withStyles(styles, {withTheme: true})(EntityColorPicker);