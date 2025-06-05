import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('@/views/HomeView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/RegisterView.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    component: () => import('@/views/ProfileView.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/articles',
    name: 'articles',
    component: () => import('@/views/ArticlesView.vue')
  },
  {
    path: '/article/:id',
    name: 'article',
    component: () => import('@/views/ArticleContentView.vue')
  },
  {
    path: '/article/create',
    name: 'articleCreate',
    component: () => import('@/views/ArticleCreateView.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/article/edit/:id',
    name: 'articleEdit',
    component: () => import('@/views/ArticleEditView.vue'),
    meta: {
      requiresAuth: true
    }
  }
]

const router = createRouter({
  history: createWebHistory(''),
  routes
})

export default router
