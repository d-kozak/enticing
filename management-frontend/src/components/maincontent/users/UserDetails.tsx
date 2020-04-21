import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {useParams} from "react-router";


export type ComponentDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const ComponentDetails = (props: ComponentDetailsProps) => {
    const {} = props;
    const {userId} = useParams();
    return <div>
        User with login {userId}
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default (connect(mapStateToProps, mapDispatchToProps)(ComponentDetails));