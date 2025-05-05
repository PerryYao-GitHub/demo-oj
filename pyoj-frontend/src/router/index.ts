import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../pages/HomePage.vue'
import AuthPage from '../pages/AuthPage.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomePage
  },

  {
    path: '/auth',
    name: 'Auth',
    component: AuthPage
  },

  {
    path: '/me',
    name: 'Me',
    component: () => import('../pages/MePage.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router