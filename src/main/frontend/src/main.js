import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as Icons from '@element-plus/icons-vue'


const app = createApp(App)

// 注册所有图标
Object.keys(Icons).forEach(key => {
    app.component(key, Icons[key])
  })
app.use(router)
app.use(ElementPlus)
app.mount('#app') 