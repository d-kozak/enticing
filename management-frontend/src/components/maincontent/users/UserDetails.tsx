import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {Redirect, useParams} from "react-router";
import {BackButton} from "../../button/BackButton";
import {getCurrentUserDetails, isAdmin} from "../../../reducers/userDetailsReducer";
import {
    Checkbox,
    CircularProgress,
    Divider,
    FormControlLabel,
    List,
    ListItem,
    ListItemText,
    Paper,
    Typography
} from "@material-ui/core";
import {addUser, requestUserInfo} from "../../../reducers/usersReducer";
import {openSnackbarAction} from "../../../reducers/snackbarReducer";
import {Centered} from "../../Centered";
import {User} from "../../../entities/user";
import {putRequest} from "../../../network/requests";
import ChangePasswordDialog from "./ChangePasswordDialog";


export type UserDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const UserDetails = (props: UserDetailsProps) => {
    const {allUsers, addUser, openSnackbarAction, currentUser, isAdmin, requestUserInfo} = props;
    const {userId} = useParams();
    if (!userId) {
        return <div>no user id</div>
    }
    if (!currentUser) {
        return <Redirect to="/login"/>
    }
    const viewedUser = allUsers.elements[userId];
    if (!viewedUser) {
        requestUserInfo(userId);
        return <Centered>
            <CircularProgress color="inherit"/>
        </Centered>
    }

    const updateUser = (user: User) => {
        putRequest<User>("/user", user)
            .then(newUser => {
                addUser(newUser)
                openSnackbarAction("User details updated");
            })
            .catch(err => {
                console.error(err);
                openSnackbarAction("Failed to update user details");
            })
    }

    const viewingMyself = viewedUser.id === currentUser.id;

    return <Paper>
        <BackButton/>
        <Typography variant="h3">User details</Typography>
        <Divider/>
        <List component="nav">
            <ListItem>
                <ListItemText primary={`Login: ${viewedUser.login}`}/>
            </ListItem>
            {!isAdmin && <ListItem>
                <ListItemText primary={`Roles: ${viewedUser.roles.join(", ")}`}/>
            </ListItem>
            }
            {isAdmin && <React.Fragment>
                <ListItem>
                    <FormControlLabel
                        control={<Checkbox checked={viewedUser.roles.includes("PLATFORM_MAINTAINER")}
                                           onChange={((event, checked) => {
                                               const user: User = {...viewedUser, roles: []}
                                               if (viewedUser.roles.includes("ADMIN")) user.roles.push("ADMIN");
                                               if (checked) user.roles.push("PLATFORM_MAINTAINER")
                                               updateUser(user);
                                           })} name="isPlatformMaintainer"/>}
                        label="Platform maintainer"
                    />
                </ListItem>
                <ListItem>
                    <FormControlLabel
                        control={<Checkbox checked={viewedUser.roles.includes("ADMIN")} onChange={((event, checked) => {
                            const user: User = {...viewedUser, roles: []}
                            if (viewedUser.roles.includes("PLATFORM_MAINTAINER")) user.roles.push("PLATFORM_MAINTAINER");
                            if (checked) user.roles.push("ADMIN")
                            updateUser(user);
                        })} name="isAdmin"/>}
                        label="Admin"
                    />
                </ListItem>
            </React.Fragment>}
            {(isAdmin || viewingMyself) && <ChangePasswordDialog editedUser={viewedUser}/>}
        </List>
    </Paper>
};


const mapStateToProps = (state: ApplicationState) => ({
    currentUser: getCurrentUserDetails(state),
    isAdmin: isAdmin(state),
    allUsers: state.users
});

const mapDispatchToProps = {
    requestUserInfo: requestUserInfo as (id: string) => void,
    addUser,
    openSnackbarAction
};

export default (connect(mapStateToProps, mapDispatchToProps)(UserDetails));