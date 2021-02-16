import axios from "axios";

const baseUrl="localhost:80";

export default {
    getUsers,
    getUser,
    addUser,
    deleteUser,
    updateUser
}

export async function getUsers(){
    return axios.get(baseUrl+"/users").then( response => {
        if (response) {
            return response.data
        }
    })
}

export async function getUser(userId){
    return axios.get(baseUrl+"/users/"+userId).then( response => {
        if (response) {
            return response.data
        }
    })
}


export async function addUser(user){
    return (await axios.post("/users"),user).then(response => {
        if (response) {
            return response.data
        }
    })
}

export async function deleteUser(userId){
    return (await axios.delete("/users/"+userId)).then(response => {
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
