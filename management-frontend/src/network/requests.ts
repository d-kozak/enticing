import {API_BASE_PATH} from "../globals";
import axios from "axios";

function encodeParams(params?: Array<[string, string]>): string {
    let suffix = "";
    if (params) {
        suffix += "?"
        for (let i = 0; i < params.length; i++) {
            suffix += `${params[i][0]}=${params[i][1]}`
            if (i != params.length - 1) suffix += "&"
        }
    }
    return suffix;
}

export async function getRequest<T>(endpoint: string, params?: Array<[string, string]>): Promise<T> {
    const encoded = encodeParams(params);
    return (await axios.get<T>(API_BASE_PATH + endpoint + encoded, {withCredentials: true})).data;
}

export async function postRequest<T>(endpoint: string, content: any, params?: Array<[string, string]>) {
    const encoded = encodeParams(params);
    return (await axios.post<T>(API_BASE_PATH + endpoint + encoded, content, {withCredentials: true})).data;
}

export async function putRequest<T>(endpoint: string, content: any, params?: Array<[string, string]>) {
    const encoded = encodeParams(params);
    return (await axios.put<T>(API_BASE_PATH + endpoint + encoded, content, {withCredentials: true})).data
}

export async function deleteRequest(endpoint: string, content: any, params?: Array<[string, string]>) {
    const encoded = encodeParams(params);
    await axios.delete(API_BASE_PATH + endpoint + encoded, {withCredentials: true})
}


