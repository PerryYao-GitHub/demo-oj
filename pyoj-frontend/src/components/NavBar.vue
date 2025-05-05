<template>
  <nav class="navbar">
    <ul class="left">
      <li><router-link to="/">Home</router-link></li>
    </ul>

    <ul class="right">
      <li v-if="!userStore.isLogin()">
        <router-link to="/auth">Login/Register</router-link>
      </li>
      <li v-else>
        <router-link to="/me">{{ userStore.user?.username }}</router-link>
      </li>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import { useUserStore } from '../stores/user'

const userStore = useUserStore()
</script>

<style scoped>
.navbar {
  background-color: #333;
  padding: 1em;
  display: flex;
  justify-content: space-between; /* 左右分布 */
  align-items: center;
}

.navbar ul {
  list-style: none;
  display: flex;
  gap: 1em;
  margin: 0;
  padding: 0;
}

.navbar ul.left {
  flex: 1; /* 左侧占据剩余空间 */
}

.navbar ul.right {
  justify-content: flex-end; /* 右侧对齐 */
}

.navbar li {
  color: white;
}

.navbar a {
  color: white;
  text-decoration: none;
}

.navbar a:hover {
  text-decoration: underline;
}
</style>