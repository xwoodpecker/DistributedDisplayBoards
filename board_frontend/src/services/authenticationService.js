import axios from 'axios'
import { store } from "@/main";

var getUrl = window.location;
var baseUrl = getUrl.protocol + "//" + getUrl.host;

const backendEndpoint = 'endpoint';

export default {
    login, logout
}

export async function login(credentials) {
    return axios.get(ENV.baseUrl + ENV.endpoints.login, {
        headers: {
            'Authorization': 'Basic ' + window.btoa(credentials.username + ':' + credentials.password)
        },
    }).then((response) => {
        if (response.data && response.status === 200) {
            return response.data;
        }
    })
}

export function logout() {
    store.commit('logout');
    sessionStorage.clear();
}
