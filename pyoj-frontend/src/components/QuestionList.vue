<template>
  <div class="question-list">
    <div v-for="question in questions" :key="question.id" class="question-item">
      <span class="title">{{ question.title }}</span>
      <span class="tags">{{ question.tags.join(', ') }}</span>
      <span class="ac-rate">{{ question.acRate }}%</span>
      <div class="actions">
        <button
          :disabled="!isUserLoggedIn"
          @click="$emit('solve', question.id)"
        >
          Solve
        </button>
        <button
          :disabled="!isUserLoggedIn"
          @click="$emit('view-solutions', question.id)"
        >
          View Solutions
        </button>
        <button
          :disabled="!isUserLoggedIn"
          @click="$emit('my-solutions', question.id)"
        >
          My Solutions
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, type PropType } from 'vue'
import { useUserStore } from '../stores/user'
import type { QuestionBriefVO } from '../types/question'

export default defineComponent({
  name: 'QuestionList',
  props: {
    questions: {
      type: Array as PropType<QuestionBriefVO[]>,
      required: true
    }
  },
  setup() {
    const userStore = useUserStore()
    const isUserLoggedIn = userStore.isLogin()

    return {
      isUserLoggedIn
    }
  }
})
</script>

<style scoped>
.question-list {
  display: flex;
  flex-direction: column;
  gap: 0.5em;
}

.question-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.5em 1em;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #fff;
}

.question-item .title {
  flex: 2; /* 标题占较多空间 */
  font-weight: bold;
}

.question-item .tags {
  flex: 3; /* 标签占中等空间 */
  color: #666;
}

.question-item .ac-rate {
  flex: 1; /* AC Rate 占较少空间 */
  text-align: center;
}

.question-item .actions {
  flex: 2; /* 按钮区域 */
  display: flex;
  gap: 0.5em;
  justify-content: flex-end;
}

.actions button {
  padding: 0.3em 0.6em;
  font-size: 0.9em;
}

.actions button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>