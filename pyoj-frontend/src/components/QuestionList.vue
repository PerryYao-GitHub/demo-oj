<template>
  <div class="question-list">
    <div
      v-for="question in questions"
      :key="question.id"
      class="question-item"
      @click="navigateToQuestion(question.id)"
    >
    <div class="content">
      <span class="title">{{ question.title }}</span>
      <span class="tags">{{ question.tags.join(', ') }}</span>
      <span class="ac-rate">{{ question.acRate }}%</span>
    </div>
    <div class="more">
      <div class="dropdown">
        <span class="dots" @click.stop="toggleDropdown(question.id)">⋮</span>
        <div v-if="dropdownVisible === question.id" class="dropdown-menu">
          <button @click.stop="$emit('view-solutions', question.id)">View Solutions</button>
          <button @click.stop="$emit('my-solutions', question.id)">My Solutions</button>
        </div>
      </div>
    </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import type { QuestionBriefVO } from '../types/question'

export default defineComponent({
  name: 'QuestionList',
  props: {
    questions: {
      type: Array as () => QuestionBriefVO[],
      required: true
    }
  },
  setup() {
    const router = useRouter()
    const userStore = useUserStore()
    const dropdownVisible = ref<number | null>(null)

    const navigateToQuestion = (id: number) => {
      if (!userStore.isLogin()) {
        alert('Please log in to view the question.')
        return
      }
      router.push(`/question/${id}`)
    }

    const toggleDropdown = (id: number) => {
      if (!userStore.isLogin()) {
        alert('Please log in to view the question.')
        return
      }
      dropdownVisible.value = dropdownVisible.value === id ? null : id
    }

    return {
      navigateToQuestion,
      toggleDropdown,
      dropdownVisible
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
  cursor: pointer;
  position: relative;
}

.question-item:hover {
  background-color: #f5f5f5;
}

.more {
  display: flex;
  align-items: center;
}

.dots {
  font-size: 1.5em;
  cursor: pointer;
}

.dropdown {
  position: relative;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 0.5em;
  display: flex;
  flex-direction: column;
  gap: 0.5em;
  z-index: 10;
}

.dropdown-menu button {
  background: none;
  border: none;
  cursor: pointer;
  text-align: left;
  padding: 0.5em;
}

.dropdown-menu button:hover {
  background-color: #f5f5f5;
}

.content {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  flex: 2;
  font-weight: bold;
}

.dots {
  font-size: 1.5em;
  cursor: pointer;
  user-select: none;
}

.tags {
  flex: 3;
  color: #666;
}

.ac-rate {
  flex: 1;
  text-align: center;
}
</style>