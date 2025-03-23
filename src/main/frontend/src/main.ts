import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './style.css'
import App from './App.vue'
import 'vue-emoji-picker/dist/vue-emoji-picker.css'
import '@fortawesome/fontawesome-free/css/all.min.css'

const app = createApp(App)
app.use(createPinia())
app.mount('#app')
