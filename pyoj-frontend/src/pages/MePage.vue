<template>
  <div class="user-page">
    <h1>User Information</h1>
    <div v-if="user">
      <p><strong>Username:</strong> {{ user.username }}</p>
      <p><strong>Role:</strong> {{ user.role }}</p>
      <p><strong>Tags:</strong> {{ user.tags?.join(', ') || 'No tags' }}</p>
    </div>
    <div v-else>
      <p>No user information available. Please log in.</p>
    </div>

    <h2>Update Tags</h2>
    <form @submit.prevent="updateTags">
      <label for="tags">Tags (comma-separated):</label>
      <input v-model="tagsInput" id="tags" type="text" placeholder="Enter new tags" />
      <button type="submit">Update</button>
    </form>

    <button @click="logout" class="logout-button">Logout</button>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useUserStore } from '../stores/user'
import { useRouter } from 'vue-router'
import request from '../axios'
import type { AppResponse } from '../types/global'
import type { UserUpdateRequest } from '../types/user'

const userStore = useUserStore()
const router = useRouter()
const user = userStore.user
const tagsInput = ref(user?.tags?.join(', ') || '')

// Update user tags
const updateTags = async () => {
  if (!user) return
  try {
    const payload: UserUpdateRequest = { tags: tagsInput.value.split(',').map(tag => tag.trim()) }
    const response = await request.post<AppResponse<typeof user>>('/user/update', payload)
    if (response.data.code === 0) {
      alert('Tags updated successfully!')
      await userStore.fetchCurrentUser() // Refresh user data
    } else {
      alert(`Failed to update tags: ${response.data.message}`)
    }
  } catch (error) {
    console.error('Error updating tags:', error)
    alert('An error occurred while updating tags.')
  }
}

// Logout
const logout = async () => {
  try {
    await request.get('/user/logout')
    userStore.clearUser()
    alert('Logged out successfully!')
    router.push({ name: 'Home' }) // Redirect to home page
  } catch (error) {
    console.error('Error logging out:', error)
    alert('An error occurred while logging out.')
  }
}

onMounted(() => {
  userStore.fetchCurrentUser()
})
</script>

<style scoped>
.user-page {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.logout-button {
  margin-top: 20px;
  background-color: #ff4d4f;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

.logout-button:hover {
  background-color: #ff7875;
}
</style>