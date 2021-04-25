import axios from 'axios';
import {
    MessageBox,
    Loading
} from 'element-ui'
/**加载样式 */
let loading
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    loading = Loading.service({
        lock: true,
        text: '加载中……',
        background: 'rgba(0, 0, 0, 0.7)'
    })
    // 在发送请求之前做些什么
    config.headers.common['token'] = sessionStorage.getItem('token')
    return config;
}, function (error) {
    return Promise.reject(error);
});
// 会话超时时路由跳转到登陆页
let dialog = true;
// axios接口未通抛错时
let dialogs = true
// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    loading.close()
    // 对响应数据做点什么
    if (response.data.jsonError) {
        response.errmessage = response.data.jsonError[0]._exceptionMessage
        if (response.errmessage == '会话已超时') {
            if (dialog) {
                MessageBox.alert(response.errmessage, {
                    confirmButtonText: '确定',
                    showClose: false,
                    center: true,
                    callback: action => {
                        sessionStorage.clear()
                        window.vm.$router.push("login")
                    }
                });
                dialog = false;
            }
        } else {
            MessageBox.alert(response.errmessage, {
                showClose: false,
                center: true,
            })
        }
    }
    return Promise.resolve(response);
}, function (error) {
    loading.close()
    // 对响应错误做点什么
    if (error && error.response) {
        switch (error.response.status) {
            case 400:
                error.message = '请求错误'
                break
            case 401:
                error.message = '未授权，请登录'
                break
            case 403:
                error.message = '拒绝访问'
                break
            case 404:
                error.message = `请求地址出错: ${error.response.config.url}`
                break
            case 408:
                error.message = '请求超时'
                break
            case 500:
                error.message = '服务器内部错误'
                break
            case 501:
                error.message = '服务未实现'
                break
            case 502:
                error.message = '网关错误'
                break
            case 503:
                error.message = '服务不可用'
                break
            case 504:
                error.message = '网关超时'
                break
            case 505:
                error.message = 'HTTP版本不受支持'
                break
            default:
                break
        }
    }
    if (dialogs) {
        MessageBox.alert(error.message, {
            showClose: false,
            center: true
        })
        dialogs = false
    }
    return Promise.reject(error);
});

// 根据环境不同引入不同baseApi地址

let baseURL = '/activiti';

export const GET = (url, params) => {
    return axios.get(`${baseURL}${url}`, {
        params: params
    }).then((data) => {
        return data;
    })
}
export const POST = (url, params) => {
    // 文件下载，加上blob响应头设置
    if (url.indexOf('DownloadInvestPlan') != -1) {
        return axios.post(`${baseURL}${url}`, params, {
            responseType: 'blob',
        }).then((data) => {
            return data
        });
    }
    else {
        return axios.post(`${baseURL}${url}`, params).then((data) => {
            return data
        });
    }
}
export const PUT = (url, data) => {
    return axios.put(`${baseURL}${url}`, data).then(data => data)
}
export const DELETE = (url, params) => {
    return axios.delete(`${baseURL}${url}`, params).then(data => data)
}

export const baseurl = baseURL