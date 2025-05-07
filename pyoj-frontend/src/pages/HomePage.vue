<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import QuestionList from '../components/QuestionList.vue'
import { useUserStore } from '../stores/user'
import type { QuestionBriefVO, QuestionPageQuery } from '../types/question'
import type { AppResponse, PageVO } from '../types/global'

const userStore = useUserStore()

// 推荐题目
const recommendedQuestions = ref<QuestionBriefVO[]>([])
// 题目列表
const questions = ref<QuestionBriefVO[]>([])
// 分页状态
const pageNum = ref(1)
const pageSize = ref(5)
const total = ref(0) // 总记录数
const totalPages = computed(() => Math.ceil(total.value / pageSize.value)) // 总页数

// 获取推荐题目
const fetchRecommendedQuestions = async () => {
  try {
    const response = await axios.get<AppResponse<QuestionBriefVO[]>>('/api/question/recommend')
    if (response.data.code === 0) {
      recommendedQuestions.value = response.data.data
    } else {
      console.error('Failed to fetch recommended questions:', response.data.message)
    }
  } catch (error) {
    console.error('Error fetching recommended questions:', error)
  }
}

// 获取题目列表
const fetchQuestions = async () => {
  try {
    const payload: QuestionPageQuery = {
      pageNum: pageNum.value, 
      pageSize: pageSize.value,
      orderBy: 'createTime', // TODO: 增加按照 ac_rate 排序
      desc: false
    }
    const response = await axios.post<AppResponse<PageVO<QuestionBriefVO>>>('/api/question/list', payload)
    if (response.data.code === 0) {
      questions.value = response.data.data.content // 更新题目列表
      total.value = response.data.data.total // 更新总记录数
    } else {
      console.error('Failed to fetch questions:', response.data.message)
    }
  } catch (error) {
    console.error('Error fetching questions:', error)
  }
}

// 分页切换
const changePage = (newPageNum: number) => {
  pageNum.value = newPageNum
  fetchQuestions()
}

// 处理 QuestionList 的事件
const handleSolve = (id: number) => {
  console.log(`Solve question with ID: ${id}`)
  // Navigate to the solve page or perform other actions
}

const handleViewSolutions = (id: number) => {
  console.log(`View solutions for question with ID: ${id}`)
  // Navigate to the solutions page or perform other actions
}

const handleMySolutions = (id: number) => {
  console.log(`View my solutions for question with ID: ${id}`)
  // Navigate to the user's solutions page or perform other actions
}

// 初始化数据
onMounted(() => {
  fetchQuestions()
  if (userStore.isLogin()) {
    fetchRecommendedQuestions()
  }
})
</script>

<template>
  <div>
    <h1>{{ userStore.user?.username || 'Guest' }}, welcome to PYOJ!</h1>

    <!-- 推荐题目 -->
    <div v-if="userStore.isLogin() && recommendedQuestions.length > 0" class="recommended-section">
      <h2>Recommended Questions</h2>
      <QuestionList
        :questions="recommendedQuestions"
        @solve="handleSolve"
        @view-solutions="handleViewSolutions"
        @my-solutions="handleMySolutions"
      />
    </div>

    <!-- 题目列表 -->
    <div class="question-list-section">
      <h2>All Questions</h2>
      <QuestionList
        :questions="questions"
        @solve="handleSolve"
        @view-solutions="handleViewSolutions"
        @my-solutions="handleMySolutions"
      />
      <div class="pagination">
        <button :disabled="pageNum === 1" @click="changePage(pageNum - 1)">Previous</button>
        <span>Page {{ pageNum }} of {{ totalPages }}</span>
        <button :disabled="pageNum === totalPages" @click="changePage(pageNum + 1)">Next</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.recommended-section {
  margin-bottom: 2em;
}

.question-list-section {
  margin-top: 2em;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1em;
  margin-top: 1em;
}

.pagination button {
  padding: 0.5em 1em;
  border: 1px solid #ccc;
  background-color: #f9f9f9;
  cursor: pointer;
}

.pagination button:disabled {
  background-color: #eee;
  cursor: not-allowed;
}
</style>