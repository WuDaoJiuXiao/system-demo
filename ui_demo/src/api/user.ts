import service from "@/request";
import {UserLoginInt} from "@/type/interface/userInt";

// POST 请求时使用 data，GET 请求时使用 params

/* 用户登录 */
export function login(data: UserLoginInt) {
    return service({
        url: 'login',
        method: 'post',
        data: data
    })
}

/* 获取登录用户信息 */
export function info() {
    return service({
        url: 'info',
        method: 'get'
    })
}