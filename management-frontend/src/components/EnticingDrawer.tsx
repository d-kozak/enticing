import Toolbar from "@material-ui/core/Toolbar";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ComputerIcon from "@material-ui/icons/Computer";
import PersonIcon from "@material-ui/icons/Person";
import DashboardIcon from "@material-ui/icons/Dashboard";
import ScheduleIcon from "@material-ui/icons/Schedule";
import MessageIcon from "@material-ui/icons/Message";
import SpeedIcon from "@material-ui/icons/Speed";
import BuildIcon from "@material-ui/icons/Build";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
import React from "react";
import {createStyles, makeStyles, Theme} from "@material-ui/core/styles";
import {useHistory} from "react-router";


const drawerWidth = 240;

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        appBar: {
            zIndex: theme.zIndex.drawer + 1,
        },
        drawer: {
            width: drawerWidth,
            flexShrink: 0,
        },
        drawerPaper: {
            width: drawerWidth,
        },
        drawerContainer: {
            overflow: 'auto',
        },
        content: {
            flexGrow: 1,
            padding: theme.spacing(3),
        },
    }),
);

export default function EnticingDrawer() {
    const classes = useStyles();

    const history = useHistory();

    return <Drawer
        className={classes.drawer}
        variant="permanent"
        classes={{
            paper: classes.drawerPaper,
        }}
    >
        <Toolbar/>
        <div className={classes.drawerContainer}>
            <List>
                <ListItem button onClick={() => history.push("/")}>
                    <ListItemIcon><DashboardIcon/></ListItemIcon>
                    <ListItemText primary="Dashboard"/>
                </ListItem>
            </List>
            <Divider/>
            <List>
                <ListItem button onClick={() => history.push("/user-management")}>
                    <ListItemIcon><PersonIcon/></ListItemIcon>
                    <ListItemText primary="User management"/>
                </ListItem>
            </List>
            <Divider/>
            <List>
                <ListItem button onClick={() => history.push("/server")}>
                    <ListItemIcon><ComputerIcon/></ListItemIcon>
                    <ListItemText primary="Servers"/>
                </ListItem>
                <ListItem button onClick={() => history.push("/component")}>
                    <ListItemIcon><ComputerIcon/></ListItemIcon>
                    <ListItemText primary="Components"/>
                </ListItem>
            </List>
            <Divider/>
            <List>
                <ListItem button onClick={() => history.push("/log")}>
                    <ListItemIcon><MessageIcon/></ListItemIcon>
                    <ListItemText primary="Logs"/>
                </ListItem>
                <ListItem button onClick={() => history.push("/performance")}>
                    <ListItemIcon><SpeedIcon/></ListItemIcon>
                    <ListItemText primary="Performance"/>
                </ListItem>
            </List>
            <Divider/>
            <List>
                <ListItem button onClick={() => history.push("/command")}>
                    <ListItemIcon><ScheduleIcon/></ListItemIcon>
                    <ListItemText primary="Commands"/>
                </ListItem>
                <ListItem button onClick={() => history.push("/build")}>
                    <ListItemIcon><BuildIcon/></ListItemIcon>
                    <ListItemText primary="Builds"/>
                </ListItem>
            </List>
        </div>
    </Drawer>
}