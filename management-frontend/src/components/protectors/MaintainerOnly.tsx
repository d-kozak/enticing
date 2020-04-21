import React, {FunctionComponent} from 'react';
import {connect} from "react-redux";
import {ApplicationState} from "../../ApplicationState";
import {isMaintainer} from "../../reducers/userDetailsReducer";


type MaintainerOnlyProps = ReturnType<typeof mapStateToProps> & typeof mapDispatchToProps & {}

const MaintainerOnly: FunctionComponent<MaintainerOnlyProps> = (props) => {
    const {isMaintainer, children} = props;
    return <React.Fragment>
        {isMaintainer && children}
    </React.Fragment>
}

const mapStateToProps = (state: ApplicationState) => ({
    isMaintainer: isMaintainer(state)
});
const mapDispatchToProps = {};


export default connect(mapStateToProps, mapDispatchToProps)(MaintainerOnly);