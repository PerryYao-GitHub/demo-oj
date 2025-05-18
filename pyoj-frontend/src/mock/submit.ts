import type { MockMethod } from 'vite-plugin-mock'
import type { SubmitPageQuery, SubmitVO, SubmitRequest } from '../types/submit'
import type { AppResponse, PageVO } from '../types/global'

const fakeSubmitVOList: SubmitVO[] = [
  {
    id: 33,
    userId: 2,
    username: 'user',
    questionId: 1,
    questionTitle: '两数之和',
    status: 'AC',
    lang: 'js',
    code: 'console.log("Hello World")',
    judgeResult: {
      message: 'Accepted',
      status: 'AC',
      memory: 128,
      time: 50
    },
    createTime: new Date().toISOString()
  },
  {
    id: 34,
    userId: 2,
    username: 'user',
    questionId: 1,
    questionTitle: '两数之和',
    status: 'AC',
    lang: 'java',
    code: 'System.out.println("Hello World")',
    judgeResult: {
      message: 'Accepted',
      status: 'AC',
      memory: 128,
      time: 50
    },
    createTime: new Date().toISOString()
  },
  {
    id: 35,
    userId: 2,
    username: 'user',
    questionId: 1,
    questionTitle: '两数之和',
    status: 'AC',
    lang: 'cpp',
    code: 'cout << "Hello World" << endl;',
    judgeResult: {
      message: 'Accepted',
      status: 'AC',
      memory: 128,
      time: 50
    },
    createTime: new Date().toISOString()
  },
  {
    id: 36,
    userId: 2,
    username: 'user',
    questionId: 1,
    questionTitle: '两数之和',
    status: 'AC',
    lang: 'go',
    code: 'fmt.Println("Hello World")',
    judgeResult: {
      message: 'Accepted',
      status: 'AC',
      memory: 128,
      time: 50
    },
    createTime: new Date().toISOString()
  },
  {
    id: 37,
    userId: 2,
    username: 'user',
    questionId: 1,
    questionTitle: '两数之和',
    status: 'AC',
    lang: 'rust',
    code: 'println!("Hello World")',
    judgeResult: {
      message: 'Accepted',
      status: 'AC',
      memory: 128,
      time: 50
    },
    createTime: new Date().toISOString()
  }
]

export default [
  {
    url: '/api/submit',
    method: 'get',
    response: (req: { query: { id: number } }): AppResponse<SubmitVO> => {
      return {
        code: 0,
        message: '查询成功',
        data: fakeSubmitVOList[0]
      }
    }
  },

  {
    url: '/api/submit',
    method: 'post',
    response: (req: { body: SubmitPageQuery }): AppResponse<PageVO<SubmitVO>> => {
      const pageVO : PageVO<SubmitVO> = {
        pageNum: req.body.pageNum,
        pageSize: req.body.pageSize,
        total: fakeSubmitVOList.length,
        content: fakeSubmitVOList
      }
      return {
        code: 0,
        message: '查询成功',
        data: pageVO
      }
    }
  },
  
  {
    url: '/api/submit/do',
    method: 'post',
    response: (req: { body: SubmitRequest }): AppResponse<SubmitVO> => {
      return {
        code: 0,
        message: '提交成功',
        data: fakeSubmitVOList[0]
      }
    }
  }
] as MockMethod[]