/** 全局通用 API 响应类型 */
export interface AppResponse<T> {
  code: number
  message: string
  data: T
}

export interface PageQuery {
  pageNum: number
  pageSize: number
  orderBy: string
  desc: boolean
}

export interface PageVO<T> {
  pageNum: number
  pageSize: number
  total: number
  content: T[]
}

