function withLeadingZero(x: number): string {
    return x < 10 ? '0' + x : x.toString();
}

function parseDate(date: Date | string | null): Date | null {
    if (!date) return null;
    if (typeof date === "string")
        return new Date(date);
    return date;
}

export function dateTimeToString(date: Date | string | null): string {
    date = parseDate(date);
    if (!date) return "";
    return `${timeToString(date)} ${dateToString(date)}`;
}

export function timeToString(date: Date | string | null): string {
    date = parseDate(date);
    if (!date) return "";
    return `${date.getHours()}:${withLeadingZero(date.getMinutes())}:${withLeadingZero(date.getSeconds())}`;
}

export function dateToString(date: Date | string | null): string {
    date = parseDate(date);
    if (!date) return "";
    return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
}