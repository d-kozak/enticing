import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import {ComponentInfo, isComponentProbablyDead} from "../../../entities/ComponentInfo";
import {dateTimeToString} from "../../utils/dateUtils";
import PingComponentButton from "./PingComponentButton";


export type LastHeartbeatInfoProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {
    component: ComponentInfo
};


const LastHeartbeat = (props: LastHeartbeatInfoProps) => {
    const {component} = props;
    const {lastHeartbeat} = component;
    const isProbablyDead = isComponentProbablyDead(component);
    return <div>
        {isProbablyDead && <PingComponentButton component={component}/>}
        {dateTimeToString(lastHeartbeat)}
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};


export default (connect(mapStateToProps, mapDispatchToProps)(LastHeartbeat));