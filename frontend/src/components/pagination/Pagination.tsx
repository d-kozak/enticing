import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import PaginationItem from "./PaginationItem";
import {asList, findInterval} from "../utils/findInterval";

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
    pageCount: number,
    hasMore: boolean
}

const Pagination = (props: PaginationProps) => {
    const {classes, pageCount, setCurrentPage, currentPage, hasMore} = props;
    const indexes = asList(findInterval(currentPage, 1, pageCount, 10));
    return <ul className={classes.root}>
        {currentPage != 0 && <li>
            <PaginationItem onClick={() => setCurrentPage(currentPage - 1)} text={'<'}/>
        </li>}
        {indexes.map(index =>
            <li key={index} onClick={() => setCurrentPage(index - 1)}>
                <PaginationItem isActive={index == currentPage} onClick={() => setCurrentPage(index - 1)}
                                text={`${index}`}/>
            </li>
        )}
        {currentPage != pageCount - 1 && <li>
            <PaginationItem onClick={() => setCurrentPage(currentPage + 1)} text={'>'}/>
        </li>}
        {currentPage == pageCount - 1 && hasMore && <li>
            <PaginationItem onClick={() => setCurrentPage(currentPage + 1)} text={'>'}/>
        </li>}
    </ul>
};

export default withStyles(styles, {
    withTheme: true
})(Pagination)