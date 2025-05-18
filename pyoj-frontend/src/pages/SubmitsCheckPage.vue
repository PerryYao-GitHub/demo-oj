<template>
  <div class="submits-check-page">
    <h1>Submissions</h1>
    <SubmitList :submits="submits" />

    <!-- 分页 -->
    <div class="pagination">
      <button :disabled="pageNum === 1" @click="changePage(pageNum - 1)">Previous</button>
      <span>Page {{ pageNum }} of {{ totalPages }}</span>
      <button :disabled="pageNum === totalPages" @click="changePage(pageNum + 1)">Next</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import request from '../axios'
import SubmitList from '../components/SubmitList.vue'
import type { SubmitPageQuery, SubmitVO } from '../types/submit'
import type { AppResponse, PageVO } from '../types/global'

// 路由参数
const route = useRoute()
const userId = Number(route.query.userId) || undefined
const questionId = Number(route.query.questionId) || undefined

// 分页状态
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0) // 总记录数
const totalPages = computed(() => Math.ceil(total.value / pageSize.value)) // 总页数

// 提交列表
const submits = ref<SubmitVO[]>([])

// 获取提交列表
const fetchSubmits = async () => {
  try {
    const payload: SubmitPageQuery = {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      userId,
      questionId,
      orderBy: 'createTime',
      desc: false
    }
    const response = await request.post<AppResponse<PageVO<SubmitVO>>>('/submit', payload)
    if (response.data.code === 0) {
      submits.value = response.data.data.content // 更新提交列表
      total.value = response.data.data.total // 更新总记录数
    } else {
      console.error('Failed to fetch submits:', response.data.message)
    }
  } catch (error) {
    console.error('Error fetching submits:', error)
  }
}

// 分页切换
const changePage = (newPageNum: number) => {
  pageNum.value = newPageNum
  fetchSubmits()
}

// 初始化数据
onMounted(() => {
  fetchSubmits()
})
</script>

<style scoped>
.submits-check-page {
  padding: 2rem;
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