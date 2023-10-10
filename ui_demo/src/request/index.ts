import axios from 'axios';
import {ElMessage} from 'element-plus';

// 创建 axios 实例
const service = axios.create({
    baseURL: "http://localhost:8888",
    timeout: 8000
});

// 根据不同的消息类型，调用不同的消息组件进行信息回显
const showInfo = (message: string, type: string) => {
    if (type === "error") {
        ElMessage.error(message);
    } else if (type === "success" || type === "warning") {
        ElMessage.success({
            type,
            message
        });
    } else {
        return;
    }
};

//请求拦截器
service.interceptors.request.use((config) => {
    config.headers = config.headers || {};
    let userToken = window.sessionStorage.getItem("userToken");
    if (userToken) {
        // 如果存在该 token 令牌，则每次请求后端时，都要携带该令牌
        config.headers['Authorization'] = userToken;
    }
    return config
}, error => {
    console.log(error)
});

//响应拦截器
service.interceptors.response.use((success) => {
    //后端返回的数据形式为：
    // success : { ...., data: {code: -xxxx, message: "xxxxx", data: xxxx}, ....}
    //成功调到了后端的接口
    if (success.status && success.status === 200) {
        //后端发生了业务逻辑错误(即后端抛出了异常,后端响应的 data.code 为负数)
        if (success.data.code < 0) {
            showInfo(success.data.message, "error");
            return;
        }
        //后端未出错，返回了正常的信息
        if (success.data.message) {
            showInfo(success.data.message, "success");
        }
    }
    return success.data;
}, (error => {//没有调到后端接口(或者后端直接挂掉了)
    const resCode = error.response.code;
    if (resCode === 504) {
        showInfo("服务器内部出错，请联系管理员", "error");
    } else if (resCode === 403) {
        showInfo("权限不足，请联系管理员", "warning");
    } else if (resCode === 404) {
        showInfo("页面走丢了~", "error")
    } else {
        const unknownInfo = error.response.data.message;
        if (unknownInfo) {
            showInfo(unknownInfo, "error");
        } else {
            showInfo("未知错误", "error");
        }
    }
}));

export default service;