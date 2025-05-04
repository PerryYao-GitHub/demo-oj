import type { MockMethod } from 'vite-plugin-mock'
import type { SubmitPageQuery, SubmitVO, SubmitRequest } from '../types/submit'
import type { AppResponse } from '../types/global'

export default [
  {
    url: '/api/submit',
    method: 'get',
    response: (req: { query: { id: number } }): AppResponse<SubmitVO> => {
      return {
        code: 0,
        message: '查询成功',
        data: {
          id: req.query.id,
          userId: 1,
          username: 'admin',
          questionId: 101,
          questionTitle: '两数之和',
          status: 'AC',
          lang: 'JavaScript',
          code: 'console.log("Hello World")',
          judgeResult: {
            message: 'Accepted',
            status: 'AC',
            memory: 128,
            time: 50
          },
          createTime: new Date().toISOString()
        }
      }
    }
  },
  {
    url: '/api/submit',
    method: 'post',
    response: (req: { body: SubmitPageQuery }): AppResponse<SubmitVO[]> => {
      return {
        code: 0,
        message: '查询成功',
        data: [
          {
            id: 1,
            userId: 1,
            username: 'admin',
            questionId: 101,
            questionTitle: '两数之和',
            status: 'AC',
            lang: 'JavaScript',
            code: 'console.log("Hello World")',
            judgeResult: {
              message: 'Accepted',
              status: 'AC',
              memory: 128,
              time: 50
            },
            createTime: new Date().toISOString()
          }
        ]
      }
    }
  },
  {
    url: '/api/submit/do',
    method: 'post',
    response: (req: { body: SubmitRequest }): AppResponse<null> => {
      return {
        code: 0,
        message: '提交成功',
        data: null
      }
    }
  }
] as MockMethod[]