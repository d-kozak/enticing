import {SearchSettings} from "../entities/SearchSettings";
import {String2StringObjectMap} from "../entities/CorpusFormat";
import axios from "axios";
import {API_BASE_PATH} from "../globals";

export const checkIndexServerStatus = async (searchSettings: SearchSettings): Promise<String2StringObjectMap> => {
    const response = await axios.get<String2StringObjectMap>(`${API_BASE_PATH}/search-settings/status/${searchSettings.id}`, {withCredentials: true});
    return response.data;
};
