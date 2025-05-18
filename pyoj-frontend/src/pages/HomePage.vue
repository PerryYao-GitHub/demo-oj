<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import request from '../axios'
import QuestionList from '../components/QuestionList.vue'
import { useUserStore } from '../stores/user'
import type { QuestionBriefVO, QuestionPageQuery } from '../types/question'
import type { AppResponse, PageVO } from '../types/global'
import router from '../router'

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
    const response = await request.get<AppResponse<QuestionBriefVO[]>>('/question/recommend')
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
    const response = await request.post<AppResponse<PageVO<QuestionBriefVO>>>('/question/list', payload)
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

const handleViewSolutions = (id: number) => {
  router.push({ name: 'SubmitsCheckPage', query: { questionId: id } })
}

const handleMySolutions = (id: number) => {
  router.push({ name: 'SubmitsCheckPage', query: { questionId: id, userId: userStore.user?.id } })
}

const handleUpdateQuestion = (id: number) => {
  router.push({ name: 'AdminUpdateQuestion', query: { id } })
}

const handleDeleteQuestion = async (id: number) => {
  if (!userStore.isAdmin()) { return }
  const response = await request.delete<AppResponse<null>>(`/admin/delete/question/${id}`)
  if (response.data.code === 0) {
    alert('Question deleted successfully!')
    fetchQuestions() // Refresh the question list
  } else {
    alert(`Failed to delete question: ${response.data.message}`)
  }
}

const handleAdminSyncQuestion = async () => {
  const response = await request.get<AppResponse<null>>('/admin/sync/question')
  if (response.data.code === 0) {
    alert('Question data updated successfully!')
    fetchQuestions() // Refresh the question list
  } else {
    alert(`Failed to sync question data: ${response.data.message}`)
  }
}

// 初始化数据
onMounted(() => {
  fetchQuestions()
})
</script>

<template>
  <div>
    <h1>{{ userStore.user?.username || 'Guest' }}, welcome to PYOJ!</h1>

    <!-- 推荐题目 -->
    <div v-if="userStore.isLogin() && recommendedQuestions.length > 0" class="recommended-section">
      <h2>Recommended Questions</h2>
      <QuestionList :questions="recommendedQuestions" @view-solutions="handleViewSolutions"
        @my-solutions="handleMySolutions" />
    </div>

    <!-- 题目列表 -->
    <div class="question-list-section">
      <h2>All Questions</h2>
      <QuestionList :questions="questions" @view-solutions="handleViewSolutions" @my-solutions="handleMySolutions"
        @delete-question="handleDeleteQuestion" @update-question="handleUpdateQuestion" />
      <div class="pagination">
        <button :disabled="pageNum === 1" @click="changePage(pageNum - 1)">Previous</button>
        <span>Page {{ pageNum }} of {{ totalPages }}</span>
        <button :disabled="pageNum === totalPages" @click="changePage(pageNum + 1)">Next</button>
      </div>
    </div>

    <button @click="handleAdminSyncQuestion" v-if="userStore.isAdmin()">Sync Questions</button>
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