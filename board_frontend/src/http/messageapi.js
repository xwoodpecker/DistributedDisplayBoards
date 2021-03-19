import axios from "axios";
import { ENV } from '@/environment';
import { sprintf }   from 'sprintf-js';
import {store} from '@/main';
const auth = store.getters.authHeader.Authorization;


export default {
    getMessagesOfBoard, getMessage
}

export function getMessagesOfBoard(boardId){
    return axios.get(ENV.baseUrl + sprintf(ENV.endpoints.boardmessages, boardId), {headers: store.getters.authHeader}).then( response => {
        if (response) {
            return response.data
        }
    })
}

export function getMessage(messageId){
    return axios.get(ENV.baseUrl + sprintf(ENV.endpoints.message, messageId)).then( response => {
        if (response) {
            return response.data
        }
    })
}
