<!-- src/pages/HomePage.vue -->
<template>
  <div>
    <h1>Welcome to YUOJ</h1>
    <p>当前用户：{{ userStore.username || '未登录' }}</p>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '../stores/user.ts'

const userStore = useUserStore()
</script>