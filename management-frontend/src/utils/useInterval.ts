import {useEffect} from "react";

export function useInterval(block: () => void, refreshRateMs: number = 500) {
    useEffect(() => {
        const interval = setInterval(block, refreshRateMs)
        return () => clearInterval(interval);
    }, [block, refreshRateMs]);
}