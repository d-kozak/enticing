import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';

const styles = createStyles({
    root: {
        display: 'flex'
    }
});


export interface SearchListPaginationProps extends WithStyles<typeof styles> {
    currentPage: number,
    setCurrentPage: (pageNumber: number) => void,
    pageCount: number
}

const SearchListPagination = (props: SearchListPaginationProps) => {
    const {classes, pageCount, setCurrentPage, currentPage} = props;
    const indexes = Array.from({length: pageCount}, (x, i) => i);
    console.log(indexes);
    return <div className={classes.root}>
        {indexes.map(index =>
            <b key={index} onClick={() => setCurrentPage(index)}>{index + 1}{index == currentPage ? 'c' : ''} </b>
        )}
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(SearchListPagination)