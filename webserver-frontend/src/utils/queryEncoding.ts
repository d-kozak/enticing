export function encodeQuery(query: string): string {
    return encodeURI(query.replace(/&/g, "::"))
}

export function decodeQuery(query: string): string {
    return decodeURI(query).replace(/::/g, "&")
}