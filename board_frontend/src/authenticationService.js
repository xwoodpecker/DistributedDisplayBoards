import axios from 'axios'

var getUrl = window.location;
var baseUrl = getUrl.protocol + "//" + getUrl.host;

const backendEndpoint = 'endpoint';

export default {
    login,
}

export function login(user) {
    return axios.get(baseUrl + backendEndpoint, {
        params: {
            user: user
        }
    }).then((response) => {
        if (response.data && response.status === 200) {
            return response.data;
        }
    })
}
