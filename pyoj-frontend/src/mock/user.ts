import type { MockMethod } from 'vite-plugin-mock'
import type { UserAuthRequest, UserVO } from '../types/user'
import type { AppResponse } from '../types/global'

export default [
  {
    url: '/api/user/login',
    method: 'post',
    response: (req: { body: UserAuthRequest }): AppResponse<UserVO | null> => {
      const { username, password } = req.body
      if (username === 'admin' && password === '123456') {
        return {
          code: 0,
          message: '登录成功',
          data: {
            id: 1,
            username: 'admin',
            role: 'admin'
          }
        }
      }
      return {
        code: 401,
        message: '用户名或密码错误',
        data: null
      }
    }
  },
  {
    url: '/api/user/register',
    method: 'post',
    response: (req: { body: UserAuthRequest }): AppResponse<UserVO> => {
      return {
        code: 0,
        message: '注册成功',
        data: {
          id: Math.floor(Math.random() * 1000),
          username: req.body.username,
          isAdmin: false
        }
      }
    }
  }
] as MockMethod[]