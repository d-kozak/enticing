export function findInterval(middle: number, left: number, right: number, size: number): [number, number] {
    const extra = Math.floor(size / 2) - (size % 2 == 0 ? 1 : 0);

    const setLeft = (x: number) => Math.max(x, left);
    const setRight = (x: number) => Math.min(x, right);

    let leftBoundary = setLeft(middle - extra);
    let rightBoundary = setRight(middle + extra);

    const currentSize = () => rightBoundary - leftBoundary + 1;

    const toAddLeft = size - currentSize();
    if (toAddLeft > 0)
        leftBoundary = setLeft(leftBoundary - toAddLeft);
    const toAddRight = size - currentSize();
    if (toAddRight > 0)
        rightBoundary = setRight(rightBoundary + toAddRight);


    return [leftBoundary, rightBoundary];
}

export function asList(interval: [number, number]): Array<number> {
    const [start, end] = interval;
    const res: Array<number> = [];

    for (let i = start; i <= end; i++) {
        res.push(i);
    }

    return res;
}