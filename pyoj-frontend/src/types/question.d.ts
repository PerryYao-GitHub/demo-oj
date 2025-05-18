import type { PageQuery } from "./global"

/** 问题分页查询请求体 */
export interface QuestionPageQuery extends PageQuery{
  title?: string
  tags?: string[]
}

/** 问题简要视图对象 */
export interface QuestionBriefVO {
  id: number
  title: string
  tags: string[]
  acRate: number
}

/** 问题视图对象 */
export interface QuestionVO {
  id: number
  title: string
  description: string
  tags: string[]
  submitCnt: number
  acceptedCnt: number
  judgeConfig: JudgeConfig
  judgeCase?: JudgeCase
}

/** 问题请求体 */
export interface QuestionRequest {
  id?: number
  title: string
  description: string
  tags: string[]
  judgeCase?: JudgeCase
  judgeConfig?: JudgeConfig
}

/** 判题配置 */
export interface JudgeConfig {
  timeLimit: number
  memoryLimit: number
}

/** 判题测试用例 */
export interface JudgeCase {
  inputs: string[]
  outputs: string[]
}