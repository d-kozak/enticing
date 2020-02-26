import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles, {CSSProperties} from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import {Theme} from "@material-ui/core/es";

const paginationItemCommon = (theme: Theme): CSSProperties => ({
    padding: '0px 5px',
    fontFamily: theme.typography.fontFamily,
    fontSize: theme.typography.h6.fontSize,
    width: '32px',
    height: '32px',
    borderRadius: '3px',
    display: 'flex'
});

const styles = (theme: Theme) => createStyles({
    paginationItem: {
        ...paginationItemCommon(theme),
        '&:hover': {
            background: theme.palette.action.hover
        }
    },
    activeItem: {
        ...paginationItemCommon(theme),
        background: theme.palette.primary.main,
        color: '#FFF'
    }
});


export interface PaginationItemProps extends WithStyles<typeof styles> {
    onClick: () => void,
    text: string,
    isActive?: boolean
}

const PaginationItem = (props: PaginationItemProps) => {
    const {onClick, text, classes, isActive = false} = props;
    return <div className={isActive ? classes.activeItem : classes.paginationItem} onClick={onClick}>
        <div style={{margin: 'auto'}}>
            {text}
        </div>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(PaginationItem)