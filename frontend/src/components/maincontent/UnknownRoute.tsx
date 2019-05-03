import createStyles from "@material-ui/core/es/styles/createStyles";
import {WithStyles} from "@material-ui/core";
import withStyles from "@material-ui/core/es/styles/withStyles";

import React from 'react';
import Typography from "@material-ui/core/Typography";

const styles = createStyles({
    root: {
        position: 'fixed',
        top: '40%',
        left: '50%',
        transform: 'translate(-50%,-50%)',
        textAlign: 'center'
    },
    title: {
        marginBottom: '20px'
    },
    text: {
        marginBottom: '20px'
    }
});


export interface UnknownRouteProps extends WithStyles<typeof styles> {
    path?: string
}

const UnknownRoute = (props: UnknownRouteProps) => {
    const {path = '', classes} = props;
    return <div className={classes.root}>
        <Typography variant="h3" className={classes.title}>Oups, you entered an unknown page...</Typography>
        <Typography variant="body1" className={classes.text}>The path <strong>/{path}</strong> is unknown, please make
            sure you typed the path you wanted
            correctly. </Typography>
        <iframe src="https://giphy.com/embed/KKOMG9EB7VqBq" width="480" height="270" frameBorder="0"
                className="giphy-embed" allowFullScreen/>
        <p><a href="https://giphy.com/gifs/mrw-holiday-confusedtravolta-KKOMG9EB7VqBq">via GIPHY</a></p>
    </div>
};

export default withStyles(styles, {
    withTheme: true
})(UnknownRoute)