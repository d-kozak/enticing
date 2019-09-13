export function findInterval(midddle: number, left: number, right: number, size: number): [number, number] {
    let len = Math.floor(size / 2);

    let leftBoundary = Math.max(midddle - len, left);
    let rightBoundary = Math.min(midddle + len, right);

    if (rightBoundary - leftBoundary + 1 > size) rightBoundary--;

    const remFromLeft = len - (midddle - leftBoundary);
    if (remFromLeft > 0) {
        rightBoundary = Math.min(right, rightBoundary + remFromLeft)
    }

    return [leftBoundary, rightBoundary];
}