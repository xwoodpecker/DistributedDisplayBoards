import axios from "axios";

const baseUrl="localhost:80";

export default {
    getBoards,
    getBoard,
    getUserBoards,
    addBoard,
    deleteBoard
}

export async function getBoards(){
    return axios.get(baseUrl+"/boards").then( response => {
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

export async function getUserBoards(userId){
    return axios.get(baseUrl+"/users/"+userId+"/boards").then( response => {
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
