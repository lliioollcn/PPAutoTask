import axios from 'axios'

const service = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 2000,
    headers: {
        'Content-Type': "application/json",
        'Token': localStorage.getItem("at_token")
    },
    withCredentials: true
});

export default service;