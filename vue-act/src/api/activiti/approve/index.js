import * as API from '../../index'

// 投资计划首页
export const processTask = (params) => {
    return API.POST(`/bpmn/processTask`, params)
}