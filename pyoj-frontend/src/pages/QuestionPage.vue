<template>
  <div class="question-page">
    <!-- 左侧题目信息 -->
    <div class="question-info">
      <h1>{{ question?.title }}</h1>
      <p><strong>Description:</strong></p>
      <p>{{ question?.description }}</p>
      <p><strong>Tags:</strong> {{ question?.tags.join(', ') }}</p>
      <p><strong>Submissions:</strong> {{ question?.submitCnt }}</p>
      <p><strong>Accepted:</strong> {{ question?.acceptedCnt }}</p>
      <p><strong>Time Limit:</strong> {{ question?.judgeConfig.timeLimit }} ms</p>
      <p><strong>Memory Limit:</strong> {{ question?.judgeConfig.memoryLimit }} MB</p>
    </div>

    <!-- 右侧代码编辑器 -->
    <div class="code-editor">
      <select v-model="selectedLanguage" class="language-selector">
        <option value="java">Java</option>
      </select>
      <MonacoEditor v-model="code" language="java" theme="vs-dark" class="editor" @change="handleCodeChange" />
      <button @click="submitCode" class="submit-button">Submit</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useRouter } from 'vue-router'
import axios from 'axios'
import MonacoEditor from 'monaco-editor-vue3'
import type { QuestionVO } from '../types/question'
import type { AppResponse } from '../types/global'
import type { SubmitRequest, SubmitVO } from '../types/submit'

const route = useRoute()
const questionId = route.params.id as string

const router = useRouter()

const question = ref<QuestionVO | null>(null)
const code = ref('// Write your solution here\n')
const selectedLanguage = ref('java')
const handleCodeChange = (newCode: string) => {
  code.value = newCode
}


// Fetch question details
const fetchQuestion = async () => {
  try {
    const response = await axios.get<AppResponse<QuestionVO>>(`/api/question/${questionId}`)
    if (response.data.code === 0) {
      question.value = response.data.data
    } else {
      console.error('Failed to fetch question:', response.data.message)
    }
  } catch (error) {
    console.error('Error fetching question:', error)
  }
}

// Submit code
const submitCode = async () => {
  if (!question.value) {
    alert('Question data is not loaded yet.')
    return
  }

  try {
    // 构造 SubmitRequest 对象
    const payload: SubmitRequest = {
      questionId: question.value.id,
      lang: selectedLanguage.value,
      code: code.value
    }

    // 发送 POST 请求到 /api/submit/do
    const response = await axios.post<AppResponse<SubmitVO>>('/api/submit/do', payload)

    if (response.data.code === 0) {
      alert('Submission successful! Redirecting to SubmitsPage...')
      // 重定向到 SubmitsCheckPage
      router.push({
        name: 'SubmitsCheckPage',
        query: {
          questionId: question.value.id,
          userId: response.data.data.userId
        }
      })
    } else {
      alert(`Submission failed: ${response.data.message}`)
    }
  } catch (error) {
    console.error('Error submitting code:', error)
    alert('An error occurred while submitting your code. Please try again.')
  }
}

onMounted(fetchQuestion)
</script>



<style scoped>
.question-page {
  display: flex;
  gap: 2rem;
  padding: 2rem;
  height: 100vh;
  /* 设置页面高度为视口高度 */
  box-sizing: border-box;
}

.question-info {
  flex: 1;
  background-color: #f9f9f9;
  padding: 1rem;
  border-radius: 8px;
  border: 1px solid #ddd;
  overflow-y: auto;
  /* 如果内容过多，允许滚动 */
}

.code-editor {
  flex: 2;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  height: 100%;
  /* 占满父容器高度 */
}

.language-selector {
  align-self: flex-start;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.editor {
  flex: 1;
  /* 让编辑器占满剩余空间 */
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
}

.submit-button {
  align-self: flex-end;
  padding: 0.5rem 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #0056b3;
}
</style>
