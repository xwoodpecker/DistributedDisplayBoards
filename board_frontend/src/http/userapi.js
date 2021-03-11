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

export async function getUser(userId){
    return axios.get(ENV.baseUrl+"users/"+userId).then( response => {
        if (response) {
            return response.data
        }
    })
}


export async function addUser(user){
    return await axios.post(ENV.baseUrl + "users/", null, {
        headers : store.getters.authHeader.headers,
        params: {
            'userName' : user.userName,
            'password' : user.password,
            'isSupervisor' : user.isSupervisor,
            'email' : user.email,
        }
    }).then(response => {
        if (response) {
            return response.data
        }
    })
}

export async function deleteUser(userId){
    return await axios.delete(ENV.baseUrl + "users/"+userId, store.getters.authHeader).then(response => {
        if (response) {
            return response.data
        }
    })
}

export async function updateUser(user){
    return (await axios.put("/users/"+user.id),user).then(response => {
        if (response) {
            return response.data
        }
    })
}
