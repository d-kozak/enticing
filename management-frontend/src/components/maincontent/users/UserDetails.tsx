import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {useParams} from "react-router";
import {BackButton} from "../../button/BackButton";
import {getCurrentUserDetails, isAdmin} from "../../../reducers/userDetailsReducer";
import {CircularProgress, Divider, List, ListItem, ListItemText, Typography} from "@material-ui/core";
import {requestUserInfo} from "../../../reducers/usersReducer";
import {Centered} from "../../Centered";


export type UserDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const UserDetails = (props: UserDetailsProps) => {
    const {currentUser, isAdmin, allUsers, requestUserInfo} = props;
    const {userId} = useParams();
    // if (!currentUser) {
    //     return <div>not logged in</div>
    // }
    if (!userId) {
        return <div>no user id</div>
    }
    const viewedUser = allUsers.elements[userId];
    if (!viewedUser) {
        requestUserInfo(userId);
        return <Centered>
            <CircularProgress color="inherit"/>
        </Centered>
    }

    // const viewingMyself = currentUser.id === userId;

    return <div>
        <BackButton/>
        <Typography variant="h3">User details</Typography>
        <Divider/>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Login: ${viewedUser.login}`}/>
            </ListItem>
            <ListItem>
                <ListItemText primary={`Roles: ${viewedUser.roles.join(", ")}`}/>
            </ListItem>
        </List>
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({
    currentUser: getCurrentUserDetails(state),
    isAdmin: isAdmin(state),
    allUsers: state.users
});

const mapDispatchToProps = {
    requestUserInfo: requestUserInfo as (id: string) => void
};

export default (connect(mapStateToProps, mapDispatchToProps)(UserDetails));