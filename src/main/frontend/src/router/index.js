import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name:'Login_Register',
    component: () => import('../components/Login_Register.vue')
  },
  {
    path: '/chatWindow',
    name: 'ChatWindow',
    component: () => import('../components/ChatWindow.vue')
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem('isLogin') === 'true'
    if (to.meta.requiresAuth && !isAuthenticated) {
      next('/') // 跳转登录页
    } else {
      next()
    }
  })
export default router 