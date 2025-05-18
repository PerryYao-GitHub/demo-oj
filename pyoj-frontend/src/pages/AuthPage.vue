<template>
  <div class="auth-page">
    <h2 style="text-align: center;">{{ isLoginMode ? 'Login' : 'Register' }}</h2>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="username">Username</label>
        <input v-model="username" id="username" type="text" required />
      </div>
      
      <div>
        <label for="password">Password</label>
        <input v-model="password" id="password" type="password" required />
      </div>
      
      <div v-if="!isLoginMode">
        <label for="confirmPassword">Confirm password</label>
        <input v-model="confirmPassword" id="confirmPassword" type="password" required />
      </div>

      <div style="text-align: center;">
        <button type="submit">{{ isLoginMode ? 'Login' : 'Register' }}</button>
      </div>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
    <p class="toggle-mode">
      {{ isLoginMode ? 'Don\'t have an account?' : 'Have an account?' }}
      <a href="#" @click.prevent="toggleMode">{{ isLoginMode ? 'Register' : 'Login' }}</a>
    </p>
  </div>
</template>

<script lang="ts">
import { ref } from 'vue'
import { useUserStore } from '../stores/user'
import { useRouter } from 'vue-router'
import request from '../axios'
import type { AppResponse } from '../types/global'
import type { UserVO } from '../types/user'
import type { UserAuthRequest } from '../types/user'

export default {
  setup() {
    const isLoginMode = ref(true) // 控制Login/Register模式
    const username = ref('')
    const password = ref('')
    const confirmPassword = ref('')
    const errorMessage = ref('')
    const userStore = useUserStore()
    const router = useRouter()

    const toggleMode = () => {
      isLoginMode.value = !isLoginMode.value
      errorMessage.value = '' // 切换模式时清空错误信息
    }

    const handleSubmit = async () => {
      errorMessage.value = ''
      if (!isLoginMode.value && password.value !== confirmPassword.value) {
        errorMessage.value = 'Passwords do not match!'
        return
      }

      try {
        const payload: UserAuthRequest = { username: username.value, password: password.value }
        if (isLoginMode.value) {
          // Login逻辑
          const response = await request.post<AppResponse<UserVO>>('/user/login', payload)
          if (response.data.code === 0) {
            userStore.setUser(response.data.data)
            router.push({ name: 'Home' }) // 登录成功后跳转到首页
          } else {
            errorMessage.value = response.data.message
          }
        } else {
          // Register逻辑
          const response = await request.post<AppResponse<UserVO>>('/user/register', payload)
          if (response.data.code === 0) {
            alert('Register successfully, please login!')
            toggleMode() // 切换回Login模式
          } else {
            errorMessage.value = response.data.message
          }
        }
      } catch (error) {
        errorMessage.value = isLoginMode.value
          ? 'Login failed, please check username and password!'
          : 'Register failed, please try again!'
      }
    }

    return {
      isLoginMode,
      username,
      password,
      confirmPassword,
      errorMessage,
      toggleMode,
      handleSubmit
    }
  }
}
</script>

<style scoped>
.auth-page {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.auth-page form > div {
  margin-bottom: 15px; /* 增加输入框之间的间距 */
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 确保左端对齐 */
}

.auth-page label {
  margin-bottom: 5px; /* 标签与输入框之间的间距 */
  font-weight: bold; /* 标签加粗 */
}

.auth-page input {
  width: 100%; /* 输入框宽度占满父容器 */
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box; /* 包括内边距和边框 */
}

.error {
  color: red;
  margin-top: 10px;
}

.toggle-mode {
  margin-top: 10px;
}

.toggle-mode a {
  color: #007bff;
  text-decoration: none;
  cursor: pointer;
}

.toggle-mode a:hover {
  text-decoration: underline;
}
</style>