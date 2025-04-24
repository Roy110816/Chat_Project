import axios from 'axios';
import { useRouter } from 'vue-router';

const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, // 在 .env 中配置
  timeout: 10000,
  headers: { 'Content-Type': 'application/json' }
})

// 请求拦截器
service.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// 响应拦截器
service.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response?.status === 401) {
        router.push('/') 
    }
    return Promise.reject(error)
  }
)

export default service