/** 提交分页查询请求体 */
export interface SubmitPageQuery {
  pageNum?: number
  pageSize?: number
  userId?: number
  questionId?: number
}

/** 提交视图对象 */
export interface SubmitVO {
  id: number
  userId: number
  username: string
  questionId: number
  questionTitle: string
  status: string
  lang: string
  code: string
  judgeResult: JudgeResult
  createTime: string
}

/** 判题结果 */
export interface JudgeResult {
  message: string
  status: string
  memory: number
  time: number
}

/** 提交请求体 */
export interface SubmitRequest {
  questionId: number
  lang: string
  code: string
}