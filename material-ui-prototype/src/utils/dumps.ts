export const dumpMap = (annotations: Map<any, any>) => {
    return JSON.stringify([...Array.from(annotations.entries())]);
};