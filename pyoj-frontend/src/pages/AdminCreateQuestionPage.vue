<template>
  <div class="admin-create-question-page">
    <h1>Create a New Question</h1>
    <form @submit.prevent="handleSubmit">
      <!-- 题目标题 -->
      <div class="form-group">
        <label for="title">Title</label>
        <input v-model="title" id="title" type="text" placeholder="Enter question title" required />
      </div>

      <!-- 题目描述 -->
      <div class="form-group">
        <label for="description">Description</label>
        <textarea
          v-model="description"
          id="description"
          rows="5"
          placeholder="Enter question description"
          required
        ></textarea>
      </div>

      <!-- 标签选择 -->
      <div class="form-group">
        <label for="tags">Tags</label>
        <select id="tags" v-model="selectedTags" multiple>
          <option v-for="tag in availableTags" :key="tag" :value="tag">{{ tag }}</option>
        </select>
      </div>

      <!-- 判题配置 -->
      <div class="form-group">
        <label for="timeLimit">Time Limit (ms)</label>
        <input v-model.number="timeLimit" id="timeLimit" type="number" placeholder="Enter time limit" required />
      </div>
      <div class="form-group">
        <label for="memoryLimit">Memory Limit (MB)</label>
        <input v-model.number="memoryLimit" id="memoryLimit" type="number" placeholder="Enter memory limit" required />
      </div>

      <!-- 判题用例 -->
      <div class="form-group">
        <label>Judge Cases</label>
        <div v-for="(pair, index) in pairs" :key="index" class="judge-case">
          <div class="case-input">
            <label>Input:</label>
            <textarea v-model="pair.input" rows="3" placeholder="Enter input"></textarea>
          </div>
          <div class="case-output">
            <label>Output:</label>
            <textarea v-model="pair.output" rows="3" placeholder="Enter output"></textarea>
          </div>
          <button type="button" @click="removePair(index)" class="remove-case">Remove</button>
        </div>
        <button type="button" @click="addPair" class="add-case">+ Add Judge Case</button>
      </div>

      <!-- 提交按钮 -->
      <div class="form-group">
        <button type="submit">Create Question</button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import request from '../axios'
import type { QuestionRequest, JudgeCase } from '../types/question'
import type { AppResponse } from '../types/global'

// 表单数据
const title = ref('')
const description = ref('')
const selectedTags = ref<string[]>([])
const timeLimit = ref<number | null>(null)
const memoryLimit = ref<number | null>(null)

// 页面中维护的 pairs 数组
const pairs = ref<{ input: string; output: string }[]>([])

// 可选标签
const availableTags = [
  'grammar',
  'array',
  'string',
  'hash',
  'stack',
  'queue',
  'linked list',
  'binary tree',
  'backtracking',
  'greedy',
  'dynamic programming',
  'depth first search',
  'breadth first search',
  'sliding window'
]

// 添加一个 pair
const addPair = () => {
  pairs.value.push({ input: '', output: '' })
}

// 移除一个 pair
const removePair = (index: number) => {
  pairs.value.splice(index, 1)
}

// 提交表单
const handleSubmit = async () => {
  if (!title.value || !description.value || !timeLimit.value || !memoryLimit.value) {
    alert('Please fill in all required fields.')
    return
  }

  // 将 pairs 转换为符合后端要求的 JudgeCase
  const judgeCase: JudgeCase = {
    inputs: pairs.value.map(pair => pair.input),
    outputs: pairs.value.map(pair => pair.output)
  }

  const payload: QuestionRequest = {
    title: title.value,
    description: description.value,
    tags: selectedTags.value,
    judgeConfig: {
      timeLimit: timeLimit.value,
      memoryLimit: memoryLimit.value
    },
    judgeCase
  }

  try {
    const response = await request.post<AppResponse<null>>('/admin/create/question', payload)
    if (response.data.code === 0) {
      alert('Question created successfully!')
      // 重置表单
      title.value = ''
      description.value = ''
      selectedTags.value = []
      timeLimit.value = null
      memoryLimit.value = null
      pairs.value = []
    } else {
      alert(`Failed to create question: ${response.data.message}`)
    }
  } catch (error) {
    console.error('Error creating question:', error)
    alert('An error occurred while creating the question. Please try again.')
  }
}
</script>

<style scoped>
.admin-create-question-page {
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}

h1 {
  text-align: center;
  margin-bottom: 1.5rem;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

input,
textarea,
select {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

textarea {
  resize: vertical;
}

select[multiple] {
  height: 100px;
}

button {
  width: 100%;
  padding: 0.75rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.judge-case {
  border: 1px solid #ddd;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 4px;
  background-color: #f9f9f9;
}

.case-input,
.case-output {
  margin-bottom: 0.5rem;
}

.remove-case {
  background-color: #dc3545;
  color: white;
  border: none;
  padding: 0.5rem;
  border-radius: 4px;
  cursor: pointer;
}

.remove-case:hover {
  background-color: #a71d2a;
}

.add-case {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 0.5rem;
  border-radius: 4px;
  cursor: pointer;
}

.add-case:hover {
  background-color: #218838;
}
</style>