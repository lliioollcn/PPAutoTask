import request from "@/request/index";


export function userTasks() {
    return request({
        url: "/task/user",
        method: "get"
    });
}


export function taskDetails(type:any) {
    return request({
        url: "/task/details",
        method: "get",
        params:{
            "type":type,
        }
    });
}