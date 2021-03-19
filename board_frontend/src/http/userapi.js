import axios from "axios";
import {ENV} from "@/environment"
import {store} from '@/main';


export default {
    getUsers,
    getUser,
    addUser,
    deleteUser,
    updateUser
}

export function getUsers(){
    return axios.get(ENV.baseUrl + ENV.endpoints.users).then( response => {
        if (response) {
            return response.data
        }
    })
}

export function getUser(userId){
    return axios.get(ENV.baseUrl+"users/"+userId).then( response => {
        if (response) {
            return response.data
        }
    })
}


export function addUser(user){
    const auth = store.getters.authHeader.Authorization;
    return axios.post(ENV.baseUrl + "users/",{
            'userName' : user.userName,
            'password' : user.password,
            'isSupervisor' : user.isSupervisor,
            'email' : user.email,
    }, {
        headers : {
            'Content-type' : 'application/json',
            'Authorization': auth
        }
    }).then(response => {
        if (response) {
            return response.data
        }
    })
}

export function deleteUser(userId){
    const auth = store.getters.authHeader.Authorization;

    return axios.delete(ENV.baseUrl + "users/"+userId,{
        headers : {
            'Authorization': auth
        }
    }).then(response => {
        if (response) {
            return response;
        }
    })
}

export function updateUser(user, id){
    const auth = store.getters.authHeader.Authorization;
    return axios.post(ENV.baseUrl + "users/" + id,user, {
        headers : {
            'Content-type' : 'application/json',
            'Authorization': auth
        }
    }).then(response => {
        if (response) {
            return response.data
        }
    })
}
