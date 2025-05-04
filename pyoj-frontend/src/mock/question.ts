import type { MockMethod } from 'vite-plugin-mock'
import type { QuestionPageQuery, QuestionBriefVO, QuestionVO } from '../types/question'
import type { AppResponse } from '../types/global'

export default [
  {
    url: '/api/question/list',
    method: 'post',
    response: (req: { body: QuestionPageQuery }): AppResponse<QuestionBriefVO[]> => {
      return {
        code: 0,
        message: '查询成功',
        data: [
          {
            id: 101,
            title: '两数之和',
            tags: ['数组', '哈希表'],
            acRate: 85.5
          }
        ]
      }
    }
  },
  {
    url: '/api/question/101',
    method: 'get',
    response: (): AppResponse<QuestionVO> => {
      return {
        code: 0,
        message: '查询成功',
        data: {
          id: 101,
          title: '两数之和',
          description: '给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的两个整数。',
          tags: ['数组', '哈希表'],
          submitCnt: 1000,
          acceptedCnt: 850,
          judgeConfig: {
            timeLimit: 1000,
            memoryLimit: 128
          }
        }
      }
    }
  }
] as MockMethod[]