import request from "@/request/index";


export function authStatus() {
    return request({
        url: "/user/status",
        method: "get"
    });
}


export function authLogin(param: any) {
    return request({
        url: "/auth/login",
        method: "post",
        data: param
    });
}