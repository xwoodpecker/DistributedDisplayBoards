import axios from "axios";
import {ENV} from "@/environment"
import {store} from '@/main';
import { sprintf } from "sprintf-js";

export default {
    addUser,
    removeUser,
    getGroupUsers
}


export function getGroupUsers(groupId){
    return axios.get(ENV.baseUrl + sprintf(ENV.endpoints.groupUsers, groupId)).then( response => {
        if(response) {
            return response.data
        }
    })
}

export function addUser(userId, groupId){
    const auth = store.getters.authHeader.Authorization;
    
    return axios.post(ENV.baseUrl + sprintf(ENV.endpoints.groupUser, groupId), userId,
    {
        headers: {
            'Content-type' : 'application/json',
            'Authorization': auth
        }
    }).then(response => {
        if (response) {
            return response
        }
    })
}

export function removeUser(userId, groupId){
    const auth = store.getters.authHeader.Authorization;
    return axios.delete(ENV.baseUrl + sprintf(ENV.endpoints.groupUser, groupId),
    {
        headers: {
            'Authorization': auth
        },
        params: {
            userId: userId
        }
    }).then(response => {
        if (response) {
            return response
        }
    })
}