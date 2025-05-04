/** 用户认证请求体 */
export interface UserAuthRequest {
  username: string
  password: string
}

/** 用户视图对象 */
export interface UserVO {
  id: number
  username: string
  role: string
}