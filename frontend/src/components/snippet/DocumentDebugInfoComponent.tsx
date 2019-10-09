import createStyles from "@material-ui/core/es/styles/createStyles";
import {Divider, ListItem, Theme, WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import React from 'react';
import Typography from "@material-ui/core/es/Typography";
import {DocumentDebugInfo} from "../../entities/FullDocument";


const styles = (theme: Theme) => createStyles({
    sectionTitle: {
        margin: '10px 0px'
    },
    divider: {
        margin: '10px 0px'
    }
});

type DocumentDebugInfoProps = WithStyles<typeof styles> & {
    document: DocumentDebugInfo
}

const DocumentDebugInfoComponent = (props: DocumentDebugInfoProps) => {
    const {document, classes} = props;
    return <div>
        <Typography variant="h6">Document Info</Typography>
        <ListItem>
            <Typography variant="body1">Server - {document.host}</Typography>
        </ListItem>
        <ListItem>
            <Typography variant="body1">Collection - {document.collection}</Typography>
        </ListItem>
        <ListItem>
            <Typography variant="body1">Id - {document.documentId}</Typography>
        </ListItem>
        <Divider className={classes.divider}/>
        <Typography className={classes.sectionTitle} variant="h6">Content</Typography>
    </div>
};


export default withStyles(styles, {withTheme: true})(DocumentDebugInfoComponent);