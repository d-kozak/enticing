import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {useParams, useRouteMatch} from "react-router";


export type ServerDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const ServerDetails = (props: ServerDetailsProps) => {
    const {} = props;
    const {serverId} = useParams();
    const match = useRouteMatch();
    return <div>
        Server with id {serverId}
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default (connect(mapStateToProps, mapDispatchToProps)(ServerDetails));