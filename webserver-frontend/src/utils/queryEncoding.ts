export function encodeQuery(query: string): string {
    return encodeURI(query).replace(/&/g, "%26")
        .replace(/\+/g, "%2B")
}

export function decodeQuery(query: string): string {
    return new URLSearchParams(query).get('query')!
}