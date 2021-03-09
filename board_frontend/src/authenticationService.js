import axios from 'axios'
import { ENV } from './environment'


//var baseUrl = getUrl.protocol + "//" + getUrl.host;

const url = "http://localhost:8000/users/login"

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
    sessionStorage.clear();
}
