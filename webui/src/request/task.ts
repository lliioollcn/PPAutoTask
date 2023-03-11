import request from "@/request/index";


export function userTasks() {
    return request({
        url: "/task/user",
        method: "get"
    });
}

export function sysTasks() {
    return request({
        url: "/task/sys",
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

export function taskDelete(id: any) {
    return request({
        url: "/task/delete",
        method: "get",
        params: {
            "id": id,
        }
    });
}

export function taskEdit(param: any) {
    return request({
        url: "/task/edit",
        method: "post",
        data: param
    });
}

export function taskAdd(param: any) {
    return request({
        url: "/task/add",
        method: "post",
        data: param
    });
}


export function taskView(page: any) {
    return request({
        url: "/open/view",
        method: "get",
        params: {
            "page": page
        }
    });
}

export function taskTotal() {
    return request({
        url: "/open/total",
        method: "get",
    });
}