import {
    ExtraColumnParams,
    ExtraColumnParamsDefaults,
    PaginatedTableColumn
} from "../../pagination/PaginatedTableColumn";
import {ComponentInfo} from "../../../entities/ComponentInfo";
import LastheartbeatInfo from "./LastheartbeatInfo";
import React from "react";


export function LastHeartbeatColumn(id: string, label: string, extra?: Partial<ExtraColumnParams>): PaginatedTableColumn<ComponentInfo, Date> {
    const renderContent = (input: Date, component: ComponentInfo) => <LastheartbeatInfo component={component}/>;
    return {
        type: "custom",
        id,
        label,
        renderContent,
        ...ExtraColumnParamsDefaults,
        ...extra
    }
}