import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import PaginationItem from "./PaginationItem";

const styles = createStyles({
    root: {
        display: 'flex',
        listStyle: 'none',
        justifyContent: 'center',
        padding: '0px',
        margin: '0px'
    }
});


export interface PaginationProps extends WithStyles<typeof styles> {
    currentPage: number,
    setCurrentPage: (pageNumber: number) => void,
    pageCount: number
}

const Pagination = (props: PaginationProps) => {
    const {classes, pageCount, setCurrentPage, currentPage} = props;
    const indexes = Array.from({length: pageCount}, (x, i) => i);
    return <ul className={classes.root}>
        {currentPage != 0 && <li>
            <PaginationItem onClick={() => setCurrentPage(currentPage - 1)} text={'<'}/>
        </li>}
        {indexes.map(index =>
            <li key={index} onClick={() => setCurrentPage(index)}>
                <PaginationItem isActive={index == currentPage} onClick={() => setCurrentPage(index)}
                                text={`${index + 1}`}/>
            </li>
        )}
        {currentPage != pageCount - 1 && <li>
            <PaginationItem onClick={() => setCurrentPage(currentPage + 1)} text={'>'}/>
        </li>}
    </ul>
};

export default withStyles(styles, {
    withTheme: true
})(Pagination)