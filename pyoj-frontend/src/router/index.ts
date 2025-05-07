import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../pages/HomePage.vue')
  },

  {
    path: '/auth',
    name: 'Auth',
    component: () => import('../pages/AuthPage.vue')
  },

  {
    path: '/me',
    name: 'Me',
    component: () => import('../pages/MePage.vue')
  },

  {
    path: '/question/:id',
    name: 'Question',
    component: () => import('../pages/QuestionPage.vue'),
  },

  {
    path: '/submits',
    name: 'SubmitsCheckPage',
    component: () => import('../pages/SubmitsCheckPage.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router