import * as React from 'react';
import {Link} from "react-router-dom";

const LinkTo = (to: string) => (props: any) => <Link to={to} {...props}/>;

export default LinkTo;