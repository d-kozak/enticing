import {makeStyles} from "@material-ui/core/styles";
import {ApplicationState} from "../../../ApplicationState";
import {connect} from "react-redux";
import React from "react";
import PaginatedTable from "../../pagination/PaginatedTable";
import {PaginatedTableColumn, StringColumn} from "../../pagination/PaginatedTableColumn"
import {addLogsToComponent} from "../../../reducers/componentsReducer";
import {getRequest} from "../../../network/requests";
import {PaginatedResult} from "../../../entities/pagination";
import {useHistory} from "react-router";
import {LogDto} from "../../../entities/LogDto";

const useStyles = makeStyles({});

type ComponentLogsTableSimpleProps = {
    componentId: string
};

type ComponentLogsTableProps =
    ReturnType<typeof mapStateToProps>
    & typeof mapDispatchToProps
    & ComponentLogsTableSimpleProps

const ComponentLogsTable = (props: ComponentLogsTableProps) => {
    const classes = useStyles();
    const {component, addLogsToComponent} = props;

    const history = useHistory();

    if (!component) {
        return <div>no data</div>;
    }

    const requestPage = (page: number, size: number) => {
        getRequest<PaginatedResult<LogDto>>(`/log/${component.id}`, [["page", page], ["size", size]])
            .then(res => {
                addLogsToComponent({...res, componentId: component.id});
            })
            .catch(err => {
                console.error(err);
            })
    }

    const columns: Array<PaginatedTableColumn<any, any>> = [
        StringColumn("logType", "Log Type"),
        StringColumn("className", "Classname"),
        StringColumn("message", "Message"),
        StringColumn("timestamp", "Timestamp")
    ];

    return <PaginatedTable
        data={component.logs}
        columns={columns}
        requestPage={requestPage}
    />
};


const mapStateToProps = (state: ApplicationState, props: ComponentLogsTableSimpleProps) => ({
    component: state.components.elements[props.componentId],
});
const mapDispatchToProps = {
    addLogsToComponent
};

export default connect(mapStateToProps, mapDispatchToProps)(ComponentLogsTable);


