/** 用户认证请求体 */
export interface UserAuthRequest {
  username: string
  password: string
  oldPassword?: string
}

/** 用户视图对象 */
export interface UserVO {
  id: number
  username: string
  role: string
  tags?: string[] // 可选字段，表示用户的标签
}

/** 用户更新视图对象 */
export interface UserUpdateRequest {
  tags?: string[]
}