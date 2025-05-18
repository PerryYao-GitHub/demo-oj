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
          <button 
            @click.stop="$emit('view-solutions', question.id)" 
            class="btn btn-view-solutions"
          >
            View Solutions
          </button>
          <button 
            @click.stop="$emit('my-solutions', question.id)" 
            class="btn btn-my-solutions"
          >
            My Solutions
          </button>
          
          <button 
            v-if="userStore.isAdmin()" 
            @click.stop="$emit('update-question', question.id)" 
            class="btn btn-edit"
          >
            Update
          </button>
          <button 
            v-if="userStore.isAdmin()" 
            @click.stop="$emit('delete-question', question.id)" 
            class="btn btn-delete"
          >
            Delete
          </button>
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
      dropdownVisible,
      userStore
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
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #fff;
  cursor: pointer;
  position: relative;
  transition: background-color 0.2s ease;
}

.question-item:hover {
  background-color: #f9f9f9;
}

.more {
  position: relative;
  display: inline-block;
}

.dots {
  cursor: pointer;
  font-size: 1.5em;
  padding: 5px 10px;
  user-select: none;
  color: #333;
  transition: color 0.2s ease;
}

.dots:hover {
  color: #007bff;
}

.dropdown-menu {
  position: absolute;
  right: 0;
  top: 100%;
  margin-top: 6px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 6px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 10;
  min-width: 160px;
  padding: 8px 0;
}

.dropdown-menu .btn {
  width: 100%;
  background: none;
  border: none;
  text-align: left;
  padding: 10px 16px;
  font-size: 14px;
  color: #333;
  cursor: pointer;
  transition: background-color 0.2s ease, color 0.2s ease;
}

.dropdown-menu .btn:hover {
  background-color: #f0f0f0;
}

.btn-view-solutions {
  color: #007bff;
}

.btn-view-solutions:hover {
  color: #0056b3;
}

.btn-my-solutions {
  color: #28a745;
}

.btn-my-solutions:hover {
  color: #1e7e34;
}

.btn-delete {
  color: #dc3545;
  font-weight: bold;
}

.btn-delete:hover {
  color: #a71d2a;
}

.btn-edit {
  color: #ffc107;
}

.btn-edit:hover {
  color: #e0a800;
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
  color: #333;
}

.tags {
  flex: 3;
  color: #666;
}

.ac-rate {
  flex: 1;
  text-align: center;
  color: #333;
}
</style>