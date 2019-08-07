/* tslint:disable */
import React from 'react';
import CheckboxTree, {Node} from 'react-checkbox-tree';
import {
    CheckBox,
    CheckBoxOutlineBlank,
    IndeterminateCheckBox,
    KeyboardArrowDown,
    KeyboardArrowUp
} from '@material-ui/icons';


import 'react-checkbox-tree/lib/react-checkbox-tree.css';


// @ts-ignore
const nodes: Array<Node> = [
    {
        value: '/app',
        label: <span>'app'</span>,
        children: [
            {
                value: '/app/Http',
                label: <span>'Http'</span>,
                children: [
                    {
                        value: '/app/Http/Controllers',
                        label: <span>'Controllers'</span>,
                        children: [{
                            value: '/app/Http/Controllers/WelcomeController.js',
                            label: <span>'WelcomeController.js'</span>,
                        }],
                    },
                    {
                        value: '/app/Http/routes.js',
                        label: <span>'routes.js'</span>,
                    },
                ],
            },
            {
                value: '/app/Providers',
                label: <span>'Providers'</span>,
                children: [{
                    value: '/app/Http/Providers/EventServiceProvider.js',
                    label: <span>'EventServiceProvider.js'</span>,
                }],
            },
        ],
    },
    {
        value: '/config',
        label: <span>'config'</span>,
        children: [
            {
                value: '/config/app.js',
                label: <span>'app.js'</span>,
            },
            {
                value: '/config/database.js',
                label: <span>'database.js'</span>,
            },
        ],
    },
    {
        value: '/public',
        label: <span>'public'</span>,
        children: [
            {
                value: '/public/assets/',
                label: <span>'assets'</span>,
                children: [{
                    value: '/public/assets/style.css',
                    label: <span>'style.css'</span>,
                }],
            },
            {
                value: '/public/index.html',
                label: <span>'index.html'</span>,
            },
        ],
    },
    {
        value: '/.env',
        label: <span>'.env'</span>,
    },
    {
        value: '/.gitignore',
        label: <span>'.gitignore'</span>,
    },
    {
        value: '/README.md',
        label: <span>'README.md'</span>,
    },
];

class BasicExample extends React.Component<any> {
    state = {
        checked: [
            '/app/Http/Controllers/WelcomeController.js',
            '/app/Http/routes.js',
            '/public/assets/style.css',
            '/public/index.html',
            '/.gitignore',
        ],
        expanded: [
            '/app',
        ],
    };

    constructor(props: any) {
        super(props);

        this.onCheck = this.onCheck.bind(this);
        this.onExpand = this.onExpand.bind(this);
    }

    onCheck(checked: any) {
        this.setState({checked});
    }

    onExpand(expanded: any) {
        this.setState({expanded});
    }

    render() {
        const {checked, expanded} = this.state;

        return (
            <CheckboxTree
                checked={checked}
                expanded={expanded}
                nodes={nodes}
                onCheck={this.onCheck}
                onExpand={this.onExpand}
                icons={{
                    check: <CheckBox color="primary"/>,
                    uncheck: <CheckBoxOutlineBlank color="primary"/>,
                    halfCheck: <IndeterminateCheckBox color="primary"/>,
                    expandClose: <KeyboardArrowDown/>,
                    expandOpen: <KeyboardArrowUp/>,
                    expandAll: <span/>,
                    collapseAll: <span/>,
                    parentClose: <span/>,
                    parentOpen: <span/>,
                    leaf: <span/>
                }}
            />
        );
    }
}

export default BasicExample;