import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import axios from 'axios'

// 配置axios默认值
axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'
axios.defaults.headers.common['Content-Type'] = 'application/json'

// 添加请求拦截器，在请求头中添加token
axios.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.config.globalProperties.$axios = axios
app.mount('#app')