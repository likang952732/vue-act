import * as API from '../index'

// 投资计划首页
export const getBatch = (params) => {
  return API.POST(`/bpmn/queryBatch`, params)
}