import request from "@/request/index";


export function userTasks() {
    return request({
        url: "/task/user",
        method: "get"
    });
}


export function taskDetails(type: any) {
    return request({
        url: "/task/details",
        method: "get",
        params: {
            "type": type,
        }
    });
}

export function taskRun(id: any) {
    return request({
        url: "/task/start",
        method: "get",
        params: {
            "id": id,
        }
    });
}

export function taskLog(id: any) {
    return request({
        url: "/task/log",
        method: "get",
        params: {
            "id": id,
        }
    });
}