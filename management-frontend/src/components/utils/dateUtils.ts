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
    const minutes = date.getMinutes();
    return `${date.getHours()}:${minutes < 10 ? '0' + minutes : minutes}:${date.getSeconds()}`;
}

export function dateToString(date: Date | string | null): string {
    date = parseDate(date);
    if (!date) return "";
    return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
}