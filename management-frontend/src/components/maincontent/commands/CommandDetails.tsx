import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from 'react';
import {useParams} from "react-router";
import {BackButton} from "../../button/BackButton";


export type CommandDetailsProps = typeof mapDispatchToProps
    & ReturnType<typeof mapStateToProps>

const CommandDetails = (props: CommandDetailsProps) => {
    const {} = props;
    const {commandId} = useParams();
    return <div>
        <BackButton/>
        Command with id {commandId}
    </div>
};


const mapStateToProps = (state: ApplicationState) => ({});

const mapDispatchToProps = {};

export default (connect(mapStateToProps, mapDispatchToProps)(CommandDetails));