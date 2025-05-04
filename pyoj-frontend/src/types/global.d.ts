/** 全局通用 API 响应类型 */
export interface AppResponse<T> {
    code: number
    message: string
    data: T
  }