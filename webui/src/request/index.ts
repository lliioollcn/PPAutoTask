import axios from 'axios'

const service = axios.create({
    baseURL: 'https://atapi.lliiooll.cn',
    timeout: 2000,
    headers: {
        'Content-Type': "application/json",
        'Token': localStorage.getItem("at_token")
    },
    withCredentials: true
});

export default service;