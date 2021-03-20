import axios from "axios";
import { ENV } from '@/environment';
import { sprintf }   from 'sprintf-js';
import {store} from '@/main';
const auth = store.getters.authHeader.Authorization;


export default {
    getBoards,
    getBoard,
    getUserGroups,
    addBoard,
    deleteBoard,
    updateBoard
}

export function getBoards(){
    return axios.get(ENV.baseUrl + ENV.endpoints.boards ).then( response => {
        if (response) {
            return response.data
        }
    })
}

export function getBoard(boardId){
    return axios.get(ENV.baseUrl+"boards/"+boardId).then( response => {
        if (response) {
            return response.data
        }
    })
}

export function getUserGroups(userId){
    return axios.get(ENV.baseUrl + sprintf(ENV.endpoints.usergroups, userId), {headers: store.getters.authHeader}).then( response => {
        if (response) {
            return response.data
        }
    })
}


export function addBoard(board){
    console.log(board)
    return axios.post(ENV.baseUrl + ENV.endpoints.boards, board, {
        headers : {
            'Content-type' : 'application/json',
            'Authorization': auth
        }
    }).then(response => {
        if (response) {
            return response
        }
    });
}

export function updateBoard(board, id){
    console.log(board)
    return axios.post(ENV.baseUrl + ENV.endpoints.boards + id , board, {
        headers : {
            'Content-type' : 'application/json',
            'Authorization': auth
        }
    }).then(response => {
        if (response) {
            return response
        }
    });
}


export function deleteBoard(boardId){
        return axios.delete(ENV.baseUrl + ENV.endpoints.boards +boardId,{
        headers : {
            'Authorization': auth
        }
    }).then(response => {
        if (response) {
            return response;
        }
    })
}
