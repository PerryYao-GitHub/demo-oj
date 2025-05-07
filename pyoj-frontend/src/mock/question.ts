import type { MockMethod } from 'vite-plugin-mock'
import type { QuestionPageQuery, QuestionBriefVO, QuestionVO } from '../types/question'
import type { AppResponse, PageVO } from '../types/global'

const fakeQuestionBriefVOList = [
  {
    id: 1,
    title: '两数之和',
    tags: ['数组', '哈希表'],
    acRate: 85.5
  },
  {
    id: 2,
    title: '爬楼梯',
    tags: ['动态规划'],
    acRate: 85.5
  },
  {
    id: 3,
    title: '三数之和',
    tags: ['数组', '双指针'],
    acRate: 85.5
  },
  {
    id: 4,
    title: '翻转二叉树',
    tags: ['树', '递归'],
    acRate: 85.5
  },
  {
    id: 5,
    title: '二叉树中最长ZigZag路径',
    tags: ['树', 'DFS'],
    acRate: 85.5
  },
  {
    id: 6,
    title: '二叉树的最大深度',
    tags: ['树', 'DFS'],
    acRate: 85.5
  },
  {
    id: 7,
    title: '接雨水',
    tags: ['数组', '双指针'],
    acRate: 85.5
  },
  {
    id: 8,
    title: '合并区间',
    tags: ['数组', '排序'],
    acRate: 85.5
  },
  {
    id: 9,
    title: '最小路径和',
    tags: ['动态规划', '数组'],
    acRate: 85.5
  },
  {
    id: 10,
    title: '组合总和',
    tags: ['回溯', '数组'],
    acRate: 85.5
  },
  {
    id: 11,
    title: '有效的括号',
    tags: ['栈', '字符串'],
    acRate: 85.5
  },
  {
    id: 12,
    title: '序列化二叉树',
    tags: ['树', 'DFS'],
    acRate: 85.5
  },
  {
    id: 13,
    title: '二叉树的层序遍历',
    tags: ['树', 'DFS'],
    acRate: 85.5
  },
  {
    id: 14,
    title: '二叉树的中序遍历',
    tags: ['树', 'DFS'],
    acRate: 85.5
  },
  {
    id: 15,
    title: '二叉树的前序遍历',
    tags: ['树', 'DFS'],
    acRate: 85.5
  },
  {
    id: 16,
    title: '二叉树的后序遍历',
    tags: ['树', 'DFS'],
    acRate: 85.5
  }
]

export default [
  {
    url: '/api/question/list',
    method: 'post',
    response: (req: { body: QuestionPageQuery }): AppResponse<PageVO<QuestionBriefVO>> => {
      let pageNum = req.body.pageNum
      let pageSize = req.body.pageSize
      if (pageNum == null || pageNum < 1) pageNum = 1
      if (pageSize == null || pageSize < 1) pageSize = 5
      const showList = fakeQuestionBriefVOList.slice((pageNum - 1) * pageSize, pageNum * pageSize)
      const pageVo : PageVO<QuestionBriefVO> = {
        pageNum: pageNum,
        pageSize: pageSize,
        total: fakeQuestionBriefVOList.length,
        content: showList
      }
      return {
        code: 0,
        message: '查询成功',
        data: pageVo
      }
    }
  },

  {
    url: '/api/question/1',
    method: 'get',
    response: (): AppResponse<QuestionVO> => {
      return {
        code: 0,
        message: '查询成功',
        data: {
          id: 1,
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
  },
  
  {
    url: '/api/question/recommend',
    method: 'get',
    response: (): AppResponse<QuestionBriefVO[]> => {
      return {
        code: 0,
        message: '查询成功',
        data: [fakeQuestionBriefVOList[0], fakeQuestionBriefVOList[1], fakeQuestionBriefVOList[2]]
      }
    }
  }
] as MockMethod[]