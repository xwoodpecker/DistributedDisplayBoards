import axios from "axios";
import { ENV } from '@/environment';
import { sprintf }   from 'sprintf-js';
import {store} from '@/main';


export default {
    getBoards,
    getBoard,
    getUserGroups,
    addBoard,
    deleteBoard
}

export async function getBoards(){
    return axios.get(ENV.baseUrl + ENV.endpoints.boards ).then( response => {
        if (response) {
            return response.data
        }
    })
}

export async function getBoard(boardId){
    return axios.get(baseUrl+"/boards/"+boardId).then( response => {
        if (response) {
            return response.data
        }
    })
}

export async function getUserGroups(userId){
    return axios.get(ENV.baseUrl + sprintf(ENV.endpoints.usergroups, userId), {headers: store.getters.authHeader}).then( response => {
        if (response) {
            return response.data
        }
    })
}


export async function addBoard(board){
    return (await axios.post("/boards"),board).then(response => {
        if (response) {
            return response.data
        }
    })
}

export async function deleteBoard(boardId){
    return (await axios.delete("/boards/"+boardId)).then(response => {
        if (response) {
            return response.data
        }
    })
}
