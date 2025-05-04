import type { MockMethod } from 'vite-plugin-mock'
import type { QuestionRequest } from '../types/question'
import type { AppResponse } from '../types/global'

export default [
  {
    url: '/api/admin/create/question',
    method: 'post',
    response: (req: { body: QuestionRequest }): AppResponse<null> => {
      return {
        code: 0,
        message: '问题创建成功',
        data: null
      }
    }
  },
  {
    url: '/api/admin/update/question',
    method: 'post',
    response: (req: { body: QuestionRequest }): AppResponse<null> => {
      return {
        code: 0,
        message: '问题更新成功',
        data: null
      }
    }
  },
  {
    url: '/api/admin/delete/question/101',
    method: 'delete',
    response: (): AppResponse<null> => {
      return {
        code: 0,
        message: '问题删除成功',
        data: null
      }
    }
  }
] as MockMethod[]