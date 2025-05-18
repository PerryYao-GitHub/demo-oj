<script setup lang="ts">
import NavBar from './components/NavBar.vue';
import { onMounted } from 'vue'
import { useUserStore } from './stores/user'

const userStore = useUserStore()

onMounted(async () => {
  await userStore.fetchCurrentUser()
})
</script>

<template>
  <div>
    <NavBar />
    <router-view />
  </div>
</template>

<style scoped>
</style>
