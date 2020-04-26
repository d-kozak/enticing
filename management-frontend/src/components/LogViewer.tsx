import React, {useEffect, useState} from 'react';
// @ts-ignore
import {LazyLog, ScrollFollow} from "react-lazylog";
import {getRequest} from "../network/requests";


export interface BuildsProps {
    url: string
}

const LogViewer = (props: BuildsProps) => {
    const {url} = props;
    const [text, setText] = useState("Loading the logs...");
    const [startLine, setStartLine] = useState(0);

    useEffect(() => {
        const refresh = () => {
            getRequest<string>(url, [["startLine", startLine]])
                .then(newText => {
                    if (newText.length > 0) {
                        let newLines = newText.split("\n").length + 1;
                        setStartLine(startLine + newLines);
                        setText(text + newText);
                    }
                })
                .catch(err => console.error(err))
        }
        const interval = setInterval(refresh, 1_000)
        return () => clearInterval(interval);
    }, [url, text, setText, startLine, setStartLine])

    return <ScrollFollow
        startFollowing={true}
        render={({follow, onScroll}: any) => (
            <LazyLog text={text}
                     enableSearch
                // stream
                     follow={follow}
                     onScroll={onScroll}/>
        )}
    />
};

export default LogViewer;